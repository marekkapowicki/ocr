package pl.marekk.ocr.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.Executor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import pl.marekk.ocr.exceptions.OcrExceptions;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.function.Function;
import java.util.function.Supplier;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class PythonExecutor {

  private static final String scriptsDirectory = "src/main/resources/scripts/filters";
  private static final String tmpDirectory = "/home/marekk/workspace/tmp";
  private final String outputPath;
  private final CommandLine pythonCommand;
  private final Executor executor;

  public static PythonExecutor script(String script) {
    return script(script, scriptsDirectory);
  }
  // TODO verify that directoryForOutput exists
  static PythonExecutor script(String script, String scriptDirectory) {
    Supplier<String> scriptNameProvider =
        () -> new File(scriptDirectory + File.separator + script).getAbsolutePath();
    Supplier<String> outputPathNameProvider = () -> defaultFileNameProvider().apply(tmpDirectory);
    return script(scriptNameProvider, outputPathNameProvider);
  }

  private static PythonExecutor script(
      Supplier<String> scriptNameProvider, final Supplier<String> outputFileNameProvider) {
    String script = scriptNameProvider.get();
    final CommandLine cmdLine = new CommandLine(script);
    Executor executor = new DefaultExecutor();
    return new PythonExecutor(outputFileNameProvider.get(), cmdLine, executor);
  }

  private static Function<String, String> defaultFileNameProvider() {
    long epochMilli = timestamp();
    return outDirectory -> outDirectory + File.separator + epochMilli + ".png";
  }

  @SneakyThrows
  private static File temporaryFileFromContent(byte[] content) {
    File tempFile = File.createTempFile("input_" + timestamp(), ".png");
    try (FileOutputStream outputStream = new FileOutputStream(tempFile)) {
      outputStream.write(content);
    }
    tempFile.deleteOnExit();
    return tempFile;
  }

  @SneakyThrows
  private static byte[] parseFile(String outputPath) {
    File outputFile = new File(outputPath);
    try (final InputStream targetStream = new DataInputStream(new FileInputStream(outputFile))) {
      return IOUtils.toByteArray(targetStream);
    } finally {
      LOG.debug("removing file {}", outputPath);
      FileUtils.forceDelete(outputFile);
    }
  }

  private static long timestamp() {
    return LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli();
  }

  @SneakyThrows
  public byte[] execute(byte[] fileContent) {
    File inputFile = temporaryFileFromContent(fileContent);
    try {
      return tryExecute(inputFile.getAbsolutePath());
    } catch (RuntimeException e) {
      throw OcrExceptions.illegalState("problem during invoking the python script");
    } finally {
      FileUtils.forceDelete(inputFile);
    }
  }

  @SneakyThrows
  private byte[] tryExecute(String filePath) {
    pythonCommand.addArgument("-i" + filePath, false);
    pythonCommand.addArgument("-o" + outputPath, false);

    LOG.info("executing {}", pythonCommand);
    int exitCode = executor.execute(pythonCommand);
    if (exitCode == 0) {
      return parseFile(outputPath);
    }
    throw OcrExceptions.illegalState(
        "execution of python script" + pythonCommand.getExecutable() + " failed");
  }
}

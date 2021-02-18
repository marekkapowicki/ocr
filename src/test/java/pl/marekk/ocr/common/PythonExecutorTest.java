package pl.marekk.ocr.common;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PythonExecutorTest {
  private static final String scriptsTestDirectory = "src/test/resources/scripts";

  @Test
  @SneakyThrows
  void happyPath() {
    // given
    PythonExecutor samplePythonScript =
        PythonExecutor.script("sample_script.py", scriptsTestDirectory);
    String pathToFile =
        "/home/marekk/workspace/projects/ocr/src/test/resources/texcleaner_samples/sample_4.jpg";
    File inputFile = new File(pathToFile);
    byte[] fileContent = java.nio.file.Files.readAllBytes(inputFile.toPath());
    // when

    byte[] result = samplePythonScript.execute(fileContent);

    // then
    assertThat(result).isNotEmpty();
  }
}

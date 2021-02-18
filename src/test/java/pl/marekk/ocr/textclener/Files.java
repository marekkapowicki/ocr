package pl.marekk.ocr.textclener;

import lombok.SneakyThrows;

import java.io.File;
import java.io.InputStream;

public class Files {
  @SneakyThrows
  static byte[] loadResource(String resourceName) {
    File file = new File(resourceName);
    return java.nio.file.Files.readAllBytes(file.toPath());
  }

  @SneakyThrows
  static InputStream loadResourceAsStream(String resourceName) {
    final ClassLoader classLoader = Files.class.getClassLoader();
    return classLoader.getResourceAsStream(resourceName);
  }

  @SneakyThrows
  static String loadResourcePath(String resourceName) {
    final ClassLoader classLoader = Files.class.getClassLoader();
    return new File(classLoader.getResource(resourceName).getFile()).getAbsolutePath();
  }

  public static String outputFileName(
      String filterName, String inputFilePath, String outputDirectory) {
    String nameWithoutExtension = com.google.common.io.Files.getNameWithoutExtension(inputFilePath);
    String outputFileName = String.join("_", nameWithoutExtension, filterName, "out");
    return outputDirectory + File.separator + outputFileName;
  }
}

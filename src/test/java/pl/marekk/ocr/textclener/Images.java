package pl.marekk.ocr.textclener;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.Arrays;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class Images {
  private static final String tmpDirectory = "/home/marekk/workspace/tmp";

  @SneakyThrows
  public static Mat toImage(@NonNull File file) {
    byte[] bytes = java.nio.file.Files.readAllBytes(file.toPath());
    return Imgcodecs.imdecode(new MatOfByte(bytes), CvType.CV_8UC1);
  }

  public static boolean storeFile(BufferedImage bufferedImage, String filePrefix) {
    try {
      String filePath = tmpDirectory + File.separator + filePrefix;
      storeFile(bufferedImage, filePath, 300);
      return true;
    } catch (RuntimeException e) {
      LOG.error("issue with storing a file ", e);
      return false;
    }
  }

  @SneakyThrows
  private static void storeFile(BufferedImage bufferedImage, String filePrefix, int dpi) {
    LOG.debug("storing file {}", filePrefix);
    createDirectoriesOnPath(filePrefix);
    try (OutputStream out = new FileOutputStream(filePrefix + ".png")) {
      ImageIOUtil.writeImage(bufferedImage, "png", out, dpi);
    }
  }

  private static void createDirectoriesOnPath(String filePrefix) throws IOException {
    String directoryPath = directoryPath(filePrefix);
    java.nio.file.Files.createDirectories(Path.of(directoryPath));
  }

  private static String directoryPath(String filePrefix) {
    String[] split = filePrefix.split(File.separator);
    return String.join(File.separator, Arrays.copyOfRange(split, 0, split.length - 1));
  }
}

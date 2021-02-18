package pl.marekk.ocr.textclener;

import lombok.NonNull;
import lombok.SneakyThrows;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ImagesLoader {
  private static final String testImagesDirectory = "src/test/resources/texcleaner_samples";

  public static Mat loadAsMat(@NonNull String fileName) {
    return loadPathAsMat(imagePath(fileName));
  }

  public static BufferedImage loadAsBufferedImage(@NonNull String fileName) {
    return loadPathAsBufferedImage(imagePath(fileName));
  }

  public static byte[] loadAsBytes(@NonNull String fileName) {
    return Files.loadResource(imagePath(fileName));
  }

  public static Set<File> listSampleImages() {
    return listFiles(testImagesDirectory);
  }

  @SneakyThrows
  private static Set<File> listFiles(String dir) {
    try (Stream<Path> stream = java.nio.file.Files.walk(Paths.get(dir), 1)) {
      return stream
          .filter(file -> !java.nio.file.Files.isDirectory(file))
          .map(Path::toAbsolutePath)
          .map(Path::toFile)
          .collect(Collectors.toSet());
    }
  }

  private static String imagePath(@NonNull String fileName) {
    return testImagesDirectory + File.separator + fileName;
  }

  @SneakyThrows
  private static BufferedImage loadPathAsBufferedImage(String path) {
    byte[] bytes = Files.loadResource(path);
    return ImageIO.read(new ByteArrayInputStream(bytes));
  }

  @SneakyThrows
  private static Mat loadPathAsMat(String path) {
    byte[] bytes = Files.loadResource(path);
    return Imgcodecs.imdecode(new MatOfByte(bytes), CvType.CV_8UC1);
  }
}

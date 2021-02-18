package pl.marekk.ocr.textclener.filter;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.opencv.core.Mat;
import pl.marekk.ocr.textclener.Files;
import pl.marekk.ocr.textclener.Images;

import java.io.File;
import java.util.function.Function;

import static pl.marekk.ocr.textclener.Images.storeFile;
import static pl.marekk.ocr.textclener.ImagesLoader.listSampleImages;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class FilesGenerator {

  static void processSampleImagesUsingMatFunction(
      String filterName, Function<Mat, Mat> processingFunction, String outputDirectory) {
    listSampleImages()
        .forEach(
            path ->
                processFileUsingMatFunction(filterName, processingFunction, path, outputDirectory));
  }

  static void processSampleImagesUsingByteFunction(
      String filterName, Function<byte[], byte[]> processingFunction, String outputDirectory) {
    listSampleImages()
        .forEach(
            path ->
                processFileUsingByteFunction(
                    filterName, processingFunction, path, outputDirectory));
  }

  @SneakyThrows
  private static void processFileUsingByteFunction(
      String filterName,
      Function<byte[], byte[]> processingFunction,
      File file,
      String outputDirectory) {
    byte[] src = FileUtils.readFileToByteArray(file);
    processingFunction
        .andThen(ImageConverter.bytesToBufferedImage)
        .andThen(
            image ->
                storeFile(
                    image,
                    Files.outputFileName(filterName, file.getAbsolutePath(), outputDirectory)))
        .apply(src);
  }

  private static void processFileUsingMatFunction(
      String filterName, Function<Mat, Mat> processingFunction, File file, String outputDirectory) {
    Mat src = Images.toImage(file);
    processingFunction
        .andThen(ImageConverter.matToBufferedImage)
        .andThen(
            image ->
                storeFile(
                    image,
                    Files.outputFileName(filterName, file.getAbsolutePath(), outputDirectory)))
        .apply(src);
  }
}

package pl.marekk.ocr.textclener.filter;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.function.Function;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class ImageConverter {
  static final Function<byte[], BufferedImage> bytesToBufferedImage =
      ImageConverter::toBufferedImage;
  static Function<Mat, BufferedImage> matToBufferedImage = ImageConverter::toBufferedImage;
  static Function<BufferedImage, Mat> bufferedImageToMat = ImageConverter::toMat;
  static Function<BufferedImage, byte[]> bufferedImageToBytes = ImageConverter::toBytes;

  // copied from
  // https://github.com/Transkribus/TranskribusInterfaces/blob/master/src/main/java/eu/transkribus/interfaces/types/util/ImageUtils.java
  private static BufferedImage toBufferedImage(@NonNull Mat mat) {
    int type = BufferedImage.TYPE_BYTE_GRAY;
    if (mat.channels() > 1) {
      type = BufferedImage.TYPE_3BYTE_BGR;
    }
    int bufferSize = mat.channels() * mat.cols() * mat.rows();
    byte[] b = new byte[bufferSize];
    mat.get(0, 0, b); // get all the pixels
    BufferedImage image = new BufferedImage(mat.cols(), mat.rows(), type);
    final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
    System.arraycopy(b, 0, targetPixels, 0, b.length);
    return image;
  }

  private static Mat toMat(@NonNull BufferedImage src) {
    Mat mat = new Mat(src.getHeight(), src.getWidth(), CvType.CV_8UC1);
    byte[] data = ((DataBufferByte) src.getRaster().getDataBuffer()).getData();
    mat.put(0, 0, data);
    return mat;
  }

  @SneakyThrows
  private static BufferedImage toBufferedImage(@NonNull byte[] src) {
    try (InputStream is = new ByteArrayInputStream(src)) {
      return ImageIO.read(is);
    }
  }

  @SneakyThrows
  private static byte[] toBytes(@NonNull BufferedImage src) {
    try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
      ImageIO.write(src, "png", outputStream);
      return outputStream.toByteArray();
    }
  }
}

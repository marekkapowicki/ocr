package pl.marekk.ocr.textclener;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import pl.marekk.ocr.exceptions.OcrExceptions;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.Function;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(of = "name")
@Slf4j
public class Image {
  private final byte[] content;
  private final String name;

  public static Image of(byte[] bytes, String imageName) {
    return new Image(bytes, imageName);
  }

  static Image load(String path) {
    try {
      return tryLoad(path);
    } catch (RuntimeException e) {
      throw OcrExceptions.illegalArgument("problem with loading file " + path, e);
    }
  }

  @SneakyThrows
  private static Image tryLoad(String path) {

    try (final InputStream targetStream = new DataInputStream(new FileInputStream(path))) {
      return of(IOUtils.toByteArray(targetStream), path);
    }
  }

  private Image fromBuffered(@NonNull BufferedImage bufferedImage) {
    final byte[] targetPixels = ((DataBufferByte) bufferedImage.getRaster().getDataBuffer()).getData();
    return of(targetPixels, this.name);
  }

  public BufferedImage toBufferedImage() {
    try (ByteArrayInputStream inputStream = new ByteArrayInputStream(content)) {
      return ImageIO.read(inputStream);
    } catch (IOException e) {
      throw OcrExceptions.illegalArgument("problem with conversion to bufferImage " + this, e);
    }
  }

  public Image withContent(byte[] content) {
    return Image.of(content, name);
  }

  public Image applyFilter(@NonNull Function<Image, Image> filter) {
    LOG.info("applying filter on image {}", this);
    return filter.apply(this);
  }

  public String name() {
    return name;
  }
}

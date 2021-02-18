package pl.marekk.ocr.textclener.filter;

import com.recognition.software.jdeskew.ImageDeskew;
import com.recognition.software.jdeskew.ImageUtil;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.awt.image.BufferedImage;
import java.util.Optional;
import java.util.function.Function;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
class Deskew implements Function<BufferedImage, BufferedImage> {
  static Function<BufferedImage, BufferedImage> deskew = Deskew.deskewing();
  private final double skewTreshold;

  private static Function<BufferedImage, BufferedImage> deskewing() {
    return new Deskew(0.05);
  }

  @Override
  public BufferedImage apply(@NonNull BufferedImage src) {
    LOG.info("applying image rotation");
    final Optional<Double> skewAngle = calculateAngle(src);
    return skewAngle
        .map(angle -> ImageUtil.rotate(src, -angle, src.getWidth(), src.getHeight()))
        .orElse(src);
  }

  private Optional<Double> calculateAngle(BufferedImage src) {
    final ImageDeskew imageDeskew = new ImageDeskew(src);
    double skewAngle = imageDeskew.getSkewAngle();
    if (skewAngle > skewTreshold) {
      LOG.info("rotation ange {}", skewAngle);
      return Optional.of(skewAngle);
    }
    return Optional.empty();
  }
}

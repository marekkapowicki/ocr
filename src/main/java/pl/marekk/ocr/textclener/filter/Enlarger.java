package pl.marekk.ocr.textclener.filter;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.function.Function;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
class Enlarger {

  static Function<Mat, Mat> enrarger = Enlarger::enlarging;

  private static Mat enlarging(@NonNull Mat src) {
    Mat dst = src.clone();
    LOG.info("enlarging image x 2");
    int resizeFactor = calculateFactor(src.width(), src.height());
    Imgproc.resize(
        src,
        dst,
        new Size(resizeFactor * src.width(), resizeFactor * src.height()),
        Imgproc.INTER_CUBIC);
    return dst;
  }

  private static int calculateFactor(int width, int height) {
    LOG.info("initial width {} height {}", width, height);
    return 2;
  }
}

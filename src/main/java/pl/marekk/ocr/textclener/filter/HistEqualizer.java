package pl.marekk.ocr.textclener.filter;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.util.function.Function;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
class HistEqualizer {

  static Function<Mat, Mat> histEqualizer = HistEqualizer::filter;

  private static Mat filter(@NonNull Mat src) {
    Mat dst = src.clone();
    LOG.info("equalize histogram");
    Imgproc.equalizeHist(src, dst);
    return dst;
  }
}

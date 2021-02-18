package pl.marekk.ocr.textclener.filter;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.function.Function;

@Slf4j
@AllArgsConstructor(access = AccessLevel.PRIVATE)
class Morphologies {
  static Function<Mat, Mat> open = Morphologies::oppening;
  static Function<Mat, Mat> close = Morphologies::closing;
  private final int morphologyType;

  private static Mat oppening(@NonNull Mat src) {
    LOG.info("applying opening");
    return morphology(src, Imgproc.MORPH_OPEN);
  }

  private static Mat closing(@NonNull Mat src) {
    LOG.info("applying closing");
    return morphology(src, Imgproc.MORPH_CLOSE);
  }

  private static Mat morphology(Mat src, int morphologyType) {
    Mat dst = src.clone();
    Imgproc.morphologyEx(src, dst, morphologyType, kerner());
    return dst;
  }

  private static Mat kerner() {
    return Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3, 3));
  }
}

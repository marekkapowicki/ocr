package pl.marekk.ocr.textclener.filter;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import pl.marekk.ocr.common.PythonExecutor;

import java.io.File;
import java.util.function.Function;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
class Restoration {
  static Function<Mat, Mat> bilateralOpenCV = Restoration::bilateralOpenCV;
  static Function<byte[], byte[]> bilateralSkImage = new BilateralScikit();
  static Function<byte[], byte[]> binaryOpening = new BinaryHolesFiller();

  private static Mat bilateralOpenCV(@NonNull Mat src) {
    Mat dst = src.clone();
    LOG.info("applying the bilateralFilter");
    Imgproc.bilateralFilter(src, dst, 9, 75.0, 75.0);
    return dst;
  }

  private static class BilateralScikit implements Function<byte[], byte[]> {
    public static final String SCRIPT = "restoration" + File.separator + "bilateral.py";
    private final PythonExecutor scriptExecutor = PythonExecutor.script(SCRIPT);

    @Override
    public byte[] apply(@NonNull byte[] fileContent) {
      LOG.info("applying the bilateralFilter Scikit");
      return scriptExecutor.execute(fileContent);
    }
  }

  private static class BinaryHolesFiller implements Function<byte[], byte[]> {
    public static final String SCRIPT = "restoration" + File.separator + "binary_opening.py";
    private final PythonExecutor scriptExecutor = PythonExecutor.script(SCRIPT);

    @Override
    public byte[] apply(@NonNull byte[] fileContent) {
      LOG.info("filling the holes in image");
      return scriptExecutor.execute(fileContent);
    }
  }
}

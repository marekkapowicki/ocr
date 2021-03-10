package pl.marekk.ocr.textclener.filter;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.util.ImageHelper;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import pl.marekk.ocr.common.PythonExecutor;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.function.Function;

import static org.opencv.imgproc.Imgproc.THRESH_BINARY;

@Slf4j
@AllArgsConstructor(access = AccessLevel.PRIVATE)
class Thresholders {

  static Function<Mat, Mat> otsu = new Otsu();
  static Function<Mat, Mat> adaptiveThresholding = new Adaptive();
  static Function<byte[], byte[]> yen = new Yen();
  static Function<byte[], byte[]> LocalSauvolaThreshold = new LocalSauvola();
  static Function<byte[], byte[]> localThreshold = new Local();
  static Function<BufferedImage, BufferedImage> simpleBinary = new SimpleBinary();

  private static class Otsu implements Function<Mat, Mat> {
    @Override
    public Mat apply(@NonNull Mat src) {
      LOG.info("applying the otsu thresholding ");
      Mat dst = src.clone();
      Imgproc.threshold(src, dst, 0, 255, Imgproc.THRESH_BINARY + Imgproc.THRESH_OTSU);
      return dst;
    }
  }

  private static class Adaptive implements Function<Mat, Mat> {
    @Override
    public Mat apply(@NonNull Mat src) {
      LOG.info("applying the adaptive thresholding");
      Mat dst = src.clone();
      Imgproc.adaptiveThreshold(
          src, dst, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, THRESH_BINARY, 45, 2);
      return dst;
    }
  }

  private static class Yen implements Function<byte[], byte[]> {
    public static final String YEN_SCRIPT = "threshold" + File.separator + "yen.py";
    private final PythonExecutor scriptExecutor = PythonExecutor.script(YEN_SCRIPT);

    @Override
    public byte[] apply(@NonNull byte[] fileContent) {
      LOG.info("applying the yen thresholding");
      return scriptExecutor.execute(fileContent);
    }
  }

  private static class Local implements Function<byte[], byte[]> {
    public static final String SCRIPT = "threshold" + File.separator + "skimage_local.py";
    private final PythonExecutor scriptExecutor = PythonExecutor.script(SCRIPT);

    @Override
    public byte[] apply(@NonNull byte[] fileContent) {
      LOG.info("applying the local thresholding");
      return scriptExecutor.execute(fileContent);
    }
  }

  private static class LocalSauvola implements Function<byte[], byte[]> {
    public static final String SCRIPT = "threshold" + File.separator + "sauvola_local.py";
    private final PythonExecutor scriptExecutor = PythonExecutor.script(SCRIPT);

    @Override
    public byte[] apply(@NonNull byte[] fileContent) {
      LOG.info("applying the local Sauvola thresholding");
      return scriptExecutor.execute(fileContent);
    }
  }
  private static class SimpleBinary implements Function<BufferedImage, BufferedImage> {

    @Override
    public BufferedImage apply(BufferedImage bufferedImage) {
      LOG.info("converting image to binary");
      return ImageHelper.convertImageToBinary(bufferedImage);
    }
  }

}

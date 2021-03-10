package pl.marekk.ocr.textclener.filter;

import static org.opencv.imgproc.Imgproc.COLOR_GRAY2RGB;
import static org.opencv.imgproc.Imgproc.COLOR_RGB2GRAY;

import java.io.File;
import java.util.function.Function;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.opencv.photo.Photo;
import pl.marekk.ocr.common.PythonExecutor;

@Slf4j
@AllArgsConstructor(access = AccessLevel.PRIVATE)
class Enhancer {

  static Function<byte[], byte[]> edgeSharper = new EdgeSharper();
  static Function<byte[], byte[]> contrastEnhancer = new ContrastEnhancer();
  static Function<Mat, Mat> detailEnhancer =
      ColorConverters.grayToColor.andThen(Enhancer::enhance).andThen(ColorConverters.colorToGray);

  private static Mat enhance(@NonNull Mat src) {
    LOG.info("enhancing details");
    Mat dst = src.clone();
    Photo.detailEnhance(src, dst);
    return dst;
  }

  @AllArgsConstructor
  private static class ColorConverters {

    static Function<Mat, Mat> grayToColor = src -> changeColor(src, COLOR_GRAY2RGB);

    static Function<Mat, Mat> colorToGray = src -> changeColor(src, COLOR_RGB2GRAY);

    private static Mat changeColor(@NonNull Mat src, int type) {
      Mat dst = src.clone();
      Imgproc.cvtColor(src, dst, type);
      return dst;
    }
  }

  private static class EdgeSharper implements Function<byte[], byte[]> {
    public static final String SCRIPT = "enhancer" + File.separator + "sharp_edges.py";
    private final PythonExecutor scriptExecutor = PythonExecutor.script(SCRIPT);

    @Override
    public byte[] apply(@NonNull byte[] fileContent) {
      LOG.info("Enhance the edges in an image");
      return scriptExecutor.execute(fileContent);
    }
  }

  private static class ContrastEnhancer implements Function<byte[], byte[]> {
    public static final String SCRIPT = "enhancer" + File.separator + "contrast_enhancer.py";
    private final PythonExecutor scriptExecutor = PythonExecutor.script(SCRIPT);

    @Override
    public byte[] apply(@NonNull byte[] fileContent) {
      LOG.info("Enhance the contrast");
      return scriptExecutor.execute(fileContent);
    }
  }
}

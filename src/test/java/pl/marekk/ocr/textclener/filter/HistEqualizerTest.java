package pl.marekk.ocr.textclener.filter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.opencv.core.Mat;
import pl.marekk.ocr.textclener.ImagesLoader;

import static org.assertj.core.api.Assertions.assertThat;

class HistEqualizerTest {
  @BeforeAll
  static void setUp() {
    nu.pattern.OpenCV.loadLocally();
  }

  @Test
  void happyPath() {
    // given
    Mat src = ImagesLoader.loadAsMat("sample_1.jpg");

    // then
    Mat result = HistEqualizer.histEqualizer.apply(src);
    // then
    assertThat(result).isNotNull();
  }

  @Test
  @SlowTest
  void produceAndStoreFiles() {
    // expect
    FilesGenerator.processSampleImagesUsingMatFunction(
        "equalizer", HistEqualizer.histEqualizer, "histogram");
  }
}

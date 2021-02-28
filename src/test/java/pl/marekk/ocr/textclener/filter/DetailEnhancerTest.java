package pl.marekk.ocr.textclener.filter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.opencv.core.Mat;
import pl.marekk.ocr.textclener.ImagesLoader;

class DetailEnhancerTest {
  @BeforeAll
  static void setUp() {
    nu.pattern.OpenCV.loadLocally();
  }

  @Test
  void happyPathGray() {
    // given
    Mat src = ImagesLoader.loadAsMat("sample_1.jpg");

    // then
    Mat result = Enhancer.detailEnhancer.apply(src);
    // then
    assertThat(result).isNotNull();
  }

  @Test
  @SlowTest
  void produceAndStoreGrayFiles() {
    // expect
    FilesGenerator.processSampleImagesUsingMatFunction(
        "detail", Enhancer.detailEnhancer, "enhancer/details/gray");
  }

  @Test
  void happyPathColor() {
    // given
    Mat src = ImagesLoader.loadAsMat("sample_1.jpg");

    // then
    Mat result = Enhancer.detailEnhancer.apply(src);
    // then
    assertThat(result).isNotNull();
  }

  @Test
  @SlowTest
  void produceAndStoreColorFiles() {
    // expect
    FilesGenerator.processSampleImagesUsingMatFunction(
        "detail", Enhancer.detailEnhancer, "enhancer/details/color");
  }
}

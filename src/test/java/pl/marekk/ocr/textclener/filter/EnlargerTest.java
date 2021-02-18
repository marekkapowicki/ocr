package pl.marekk.ocr.textclener.filter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.opencv.core.Mat;
import pl.marekk.ocr.textclener.Images;
import pl.marekk.ocr.textclener.ImagesLoader;

import static org.assertj.core.api.Assertions.assertThat;

class EnlargerTest {

  @BeforeAll
  static void setUp() {
    nu.pattern.OpenCV.loadLocally();
  }

  @Test
  void happyPath() {
    // given
    Mat src = ImagesLoader.loadAsMat("sample_1.jpg");

    // when
    Mat result = Enlarger.enrarger.apply(src);

    // then
    assertThat(result).isNotNull();
    ImageConverter.matToBufferedImage
        .andThen(image -> Images.storeFile(image, "enlarged"))
        .apply(result);
  }
}

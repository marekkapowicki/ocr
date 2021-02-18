package pl.marekk.ocr.textclener.filter;

import org.junit.jupiter.api.Test;
import pl.marekk.ocr.textclener.ImagesLoader;

import static org.assertj.core.api.Assertions.assertThat;

class YenThresholderTest {

  @Test
  void happyPath() {
    // given
    byte[] content = ImagesLoader.loadAsBytes("sample_1.jpg");
    // when
    byte[] result = Thresholders.yen.apply(content);

    // then
    assertThat(result).isNotEmpty();
  }

  @Test
  @SlowTest
  void produceAndStoreFiles() {
    // expect
    FilesGenerator.processSampleImagesUsingByteFunction("yen", Thresholders.yen, "threshold/yen");
  }
}

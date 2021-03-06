package pl.marekk.ocr.textclener.filter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import pl.marekk.ocr.textclener.ImagesLoader;

class LocalThresholderTest {
  @Test
  void happyPath() {
    // given
    byte[] content = ImagesLoader.loadAsBytes("sample_1.jpg");
    // when
    byte[] result = Thresholders.localThreshold.apply(content);

    // then
    assertThat(result).isNotEmpty();
  }

  @Test
  @SlowTest
  void produceAndStoreFiles() {
    // expect
    FilesGenerator.processSampleImagesUsingByteFunction(
        "local", Thresholders.localThreshold, "threshold/local");
  }
}

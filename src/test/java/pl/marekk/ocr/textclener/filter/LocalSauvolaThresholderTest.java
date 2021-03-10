package pl.marekk.ocr.textclener.filter;

import org.junit.jupiter.api.Test;
import pl.marekk.ocr.textclener.ImagesLoader;

import static org.assertj.core.api.Assertions.assertThat;

class LocalSauvolaThresholderTest {
  @Test
  void happyPath() {
    // given
    byte[] content = ImagesLoader.loadAsBytes("sample_1.jpg");
    // when
    byte[] result = Thresholders.LocalSauvolaThreshold.apply(content);

    // then
    assertThat(result).isNotEmpty();
  }

  @Test
//  @SlowTest
  void produceAndStoreFiles() {
    // expect
    FilesGenerator.processSampleImagesUsingByteFunction(
        "local_sauvola", Thresholders.LocalSauvolaThreshold, "threshold/local_sauvola");
  }
}

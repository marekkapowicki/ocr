package pl.marekk.ocr.textclener.filter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import pl.marekk.ocr.textclener.ImagesLoader;

public class WienerSkiImageTest {
  @Test
  void happyPath() {
    // given
    byte[] content = ImagesLoader.loadAsBytes("sample_1.jpg");
    // when
    byte[] result = Restoration.wienerUnblur.apply(content);

    // then
    assertThat(result).isNotEmpty();
  }

  @Test
//  @SlowTest
  void produceAndStoreFiles() {
    // expect
    FilesGenerator.processSampleImagesUsingByteFunction(
        "wiener", Restoration.wienerUnblur, "morphology/wiener");
  }
}

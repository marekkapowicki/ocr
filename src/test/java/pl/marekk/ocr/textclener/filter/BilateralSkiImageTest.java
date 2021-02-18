package pl.marekk.ocr.textclener.filter;

import org.junit.jupiter.api.Test;
import pl.marekk.ocr.textclener.ImagesLoader;

import static org.assertj.core.api.Assertions.assertThat;

public class BilateralSkiImageTest {
  @Test
  void happyPath() {
    // given
    byte[] content = ImagesLoader.loadAsBytes("sample_1.jpg");
    // when
    byte[] result = Restoration.bilateralSkImage.apply(content);

    // then
    assertThat(result).isNotEmpty();
  }

  @Test
  @SlowTest
  void produceAndStoreFiles() {
    // expect
    FilesGenerator.processSampleImagesUsingByteFunction(
        "bilateral", Restoration.bilateralSkImage, "denoiser/bilateral/skimage");
  }
}

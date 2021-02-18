package pl.marekk.ocr.textclener.filter;

import org.junit.jupiter.api.Test;
import pl.marekk.ocr.textclener.ImagesLoader;

import static org.assertj.core.api.Assertions.assertThat;

public class FillingHolesTest {
  @Test
  void happyPath() {
    // given
    byte[] content = ImagesLoader.loadAsBytes("sample_1.jpg");
    // when
    byte[] result = Restoration.fillingHolesInBinaryImage.apply(content);

    // then
    assertThat(result).isNotEmpty();
  }

  @Test
//  @ManualTest
  void produceAndStoreFiles() {
    // expect
    FilesGenerator.processSampleImagesUsingByteFunction(
        "holes", Restoration.fillingHolesInBinaryImage, "denoiser/holes_filler");
  }
}

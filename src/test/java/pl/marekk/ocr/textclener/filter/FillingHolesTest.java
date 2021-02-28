package pl.marekk.ocr.textclener.filter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import pl.marekk.ocr.textclener.Images;
import pl.marekk.ocr.textclener.ImagesLoader;

public class FillingHolesTest {
  @Test
  void happyPath() {
    // given
    byte[] content = ImagesLoader.loadAsBytes("marek_kapowicki_id_front_1.jpg");
    // when
    byte[] result = Restoration.fillingHolesInBinaryImage.apply(content);
    Images.storeFile(ImageConverter.bytesToBufferedImage.apply(result), "777");

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

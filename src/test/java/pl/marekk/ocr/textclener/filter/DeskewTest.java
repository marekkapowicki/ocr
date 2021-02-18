package pl.marekk.ocr.textclener.filter;

import org.junit.jupiter.api.Test;
import pl.marekk.ocr.textclener.Images;
import pl.marekk.ocr.textclener.ImagesLoader;

import java.awt.image.BufferedImage;

import static org.assertj.core.api.Assertions.assertThat;

class DeskewTest {

  @Test
  void happyPath() {
    // given
    BufferedImage bufferedImage = ImagesLoader.loadAsBufferedImage("sample_rotated_image.png");

    // when
    BufferedImage result = Deskew.deskew.apply(bufferedImage);

    // them
    assertThat(result).isNotNull();
    Images.storeFile(result, "unrotated");
  }
}

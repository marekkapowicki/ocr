package pl.marekk.ocr.textclener.filter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import pl.marekk.ocr.textclener.ImagesLoader;

public class ContrastEnhancerTest {

  @Test
  void happyPath() {
    // given
    byte[] content = ImagesLoader.loadAsBytes("sample_16.jpg");
    // when
    byte[] result = Enhancer.contrastEnhancer.apply(content);

    // then
    assertThat(result).isNotEmpty();
  }

  @Test
//  @ManualTest
  void produceAndStoreFiles() {
    // expect
    FilesGenerator.processSampleImagesUsingByteFunction(
        "holes", Enhancer.contrastEnhancer, "enhancer/contrast");
  }
}

package pl.marekk.ocr.textclener;

import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pl.marekk.ocr.textclener.filter.Filters;
import pl.marekk.ocr.textclener.filter.SlowTest;

public class ImageMediumFilterTest {
  @BeforeAll
  static void setUp() {
    nu.pattern.OpenCV.loadLocally();
  }

  @Test
  @SneakyThrows
  void happyPath() {
    // given
    final String fileName = "sample_1.jpg";
    final byte[] input = ImagesLoader.loadAsBytes(fileName);
    final Image image = ImageFactory.create(input, fileName);
    Image result = image.applyFilter(Filters.medium);

    // then
    Assertions.assertThat(result).isNotNull();
  }
  @Test
  @SlowTest
  void produceAndStoreFiles() {
    // expect
    FilterOutput.processSampleImages("medium", Filters.medium, "filters/medium");
  }
}

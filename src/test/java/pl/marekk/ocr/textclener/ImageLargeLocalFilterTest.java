package pl.marekk.ocr.textclener;

import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pl.marekk.ocr.textclener.filter.Filters;

public class ImageLargeLocalFilterTest {
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
    Image result = image.applyFilter(Filters.large_local);

    // then
    Assertions.assertThat(result).isNotNull();




  }
  @Test
//  @ManualTest
  void produceAndStoreFiles() {
    // expect
    FilterOutput.processSampleImages("large", Filters.large_local, "filters/large_local");
  }
}

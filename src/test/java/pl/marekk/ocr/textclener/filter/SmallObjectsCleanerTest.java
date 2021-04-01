package pl.marekk.ocr.textclener.filter;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.Function;
import org.junit.jupiter.api.Test;
import pl.marekk.ocr.textclener.ImagesLoader;

public class SmallObjectsCleanerTest {

  public static final Function<byte[], byte[]> cleaning_function = Thresholders.localThreshold.andThen(
      Restoration.smallObjectsCleaner);

  @Test
  void happyPath() {
    // given
    byte[] content = ImagesLoader.loadAsBytes("sample_1.jpg");
    // when
    byte[] result = cleaning_function.apply(content);

    // then
    assertThat(result).isNotEmpty();
  }

  @Test
//  @SlowTest
  void produceAndStoreFiles() {
    // expect
    FilesGenerator.processSampleImagesUsingByteFunction(
        "cleaning", cleaning_function, "morphology/objects_cleaning");
  }
}

package pl.marekk.ocr.textclener;

import lombok.NonNull;
import lombok.SneakyThrows;

import java.io.File;
import java.util.function.Function;

class FilterOutput {
    static void processSampleImages(String filterName, Function<Image, Image> processingFunction, String outputDirectory){
       ImagesLoader.listSampleImages().stream()
               .map(FilterOutput::createImage)
               .forEach(image -> processFile(filterName, processingFunction, image, outputDirectory));
    }
    @SneakyThrows
    private static void processFile(
            String filterName,
            Function<Image, Image> processingFunction,
            Image image,
            String outputDirectory) {
        processingFunction
                .andThen(result -> storeFile(result, filterName, outputDirectory))
                .apply(image);
    }

    private static Image createImage(File file){
        final byte[] content = ImagesLoader.loadAsBytes(file.getName());
        return ImageFactory.create(content, file.getName());
    }
    private static boolean storeFile(@NonNull Image image, String filterName, String outputDirectory){
        return Images.storeFile(image.toBufferedImage(), Files.outputFileName(filterName, image.name(), outputDirectory));
    }
}

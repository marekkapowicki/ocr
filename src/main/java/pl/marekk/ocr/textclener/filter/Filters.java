package pl.marekk.ocr.textclener.filter;

import pl.marekk.ocr.textclener.Image;

import java.util.function.Function;

public class Filters {

  public static final Function<Image, Image> medium =
      image ->
          ImageConverter.bufferedImageToMat
              .andThen(Enlarger.enrarger)
              .andThen(ImageConverter.matToBufferedImage)
              .andThen(Deskew.deskew)
              .andThen(Thresholders.simpleBinary)
              .andThen(ImageConverter.bufferedImageToBytes)
              .andThen(image::withContent)
              .apply(image.toBufferedImage());
  public static final Function<Image, Image> large =
      image ->
          Deskew.deskew
              .andThen(ImageConverter.bufferedImageToMat)
              //              .andThen(Enhancer.detailEnhancer)
              .andThen(Enlarger.enrarger)
              .andThen(ImageConverter.matToBufferedImage)
              .andThen(ImageConverter.bufferedImageToBytes)
              .andThen(Thresholders.yen)
              .andThen(ImageConverter.bytesToBufferedImage)
              .andThen(ImageConverter.bufferedImageToMat)
              .andThen(Restoration.bilateralOpenCV)
              .andThen(Morphologies.close)
              .andThen(ImageConverter.matToBufferedImage)
              .andThen(ImageConverter.bufferedImageToBytes)
              .andThen(image::withContent)
              .apply(image.toBufferedImage());
  public static final Function<Image, Image> large_local =
      image ->
          Deskew.deskew
              .andThen(ImageConverter.bufferedImageToMat)
              .andThen(Enlarger.enrarger)
              .andThen(ImageConverter.matToBufferedImage)
              .andThen(ImageConverter.bufferedImageToBytes)
              .andThen(Restoration.fillingHolesInBinaryImage)
              .andThen(Thresholders.localThreshold)

              .andThen(ImageConverter.bytesToBufferedImage)
              .andThen(ImageConverter.bufferedImageToMat)
//                  .andThen(Enhancer.detailEnhancer)
              .andThen(Morphologies.open)
              .andThen(Restoration.bilateralOpenCV)
              .andThen(ImageConverter.matToBufferedImage)
              .andThen(ImageConverter.bufferedImageToBytes)
              .andThen(image::withContent)
              .apply(image.toBufferedImage());
}

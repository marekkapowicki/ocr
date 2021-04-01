package pl.marekk.ocr.textclener.filter;

import java.util.function.UnaryOperator;
import pl.marekk.ocr.textclener.Image;

import java.util.function.Function;

public class Filters {

  public static final UnaryOperator<Image> medium =
      image ->
          ImageConverter.bufferedImageToMat
              .andThen(Enlarger.enrarger)
              .andThen(ImageConverter.matToBufferedImage)
              .andThen(Deskew.deskew)
              .andThen(ImageConverter.bufferedImageToBytes)
              .andThen(Enhancer.contrastEnhancer)
              .andThen(Thresholders.LocalSauvolaThreshold)
              .andThen(image::withContent)
              .apply(image.toBufferedImage());
  public static final UnaryOperator<Image> large =
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
  public static final UnaryOperator<Image> large_local =
      image ->
          Deskew.deskew
              .andThen(ImageConverter.bufferedImageToMat)
              .andThen(Enlarger.enrarger)
              .andThen(ImageConverter.matToBufferedImage)
              .andThen(ImageConverter.bufferedImageToBytes)
              .andThen(Enhancer.contrastEnhancer)
              .andThen(Enhancer.edgeSharper)
              .andThen(Thresholders.LocalSauvolaThreshold)
              .andThen(Restoration.bilateralSkImage)
              .andThen(Restoration.wienerUnblur)
              .andThen(Restoration.smallObjectsCleaner)
              .andThen(image::withContent)
              .apply(image.toBufferedImage());
}

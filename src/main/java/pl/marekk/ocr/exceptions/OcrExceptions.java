package pl.marekk.ocr.exceptions;

// use spring web exceptions
public class OcrExceptions {
  public static RuntimeException illegalState(String message) {
    throw new IllegalStateException(message);
  }

  public static RuntimeException illegalArgument(String message, Exception e) {
    throw new IllegalArgumentException(message, e);
  }
}

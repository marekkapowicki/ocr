package pl.marekk.ocr.textclener.filter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class OpencvChecker {
  static boolean isInstalled() {
    try {
      LOG.info("openv installed");
      return false;
    } catch (RuntimeException e) {
      LOG.warn("no opencv installed");
      return false;
    }
  }
}

package pl.marekk.ocr.textclener;


import org.springframework.test.context.junit.jupiter.EnabledIf;

@EnabledIf(expression = "#{'mkyong'.toUpperCase().equals(3)")
public @interface OpencvInstalled {
}

package pl.marekk.ocr.textclener;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayOutputStream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageFactory {
    public static Image create(byte[] content, String name){
        final BufferedImage bufferedImage = toBufferedImage(loadPathAsMat(content));
        return Image.of(toBytes(bufferedImage), name);
    }

    @SneakyThrows
    private static Mat loadPathAsMat(byte[] content) {
        return Imgcodecs.imdecode(new MatOfByte(content), CvType.CV_8UC1);
    }

//    @SneakyThrows
//    private static byte[] tryLoad(String path) {
//        return java.nio.file.Files.readAllBytes(new File(path).toPath());
//    }

    //duplication
    private static BufferedImage toBufferedImage(@NonNull Mat mat) {
        int type = BufferedImage.TYPE_BYTE_GRAY;
        if (mat.channels() > 1) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        int bufferSize = mat.channels() * mat.cols() * mat.rows();
        byte[] b = new byte[bufferSize];
        mat.get(0, 0, b); // get all the pixels
        BufferedImage image = new BufferedImage(mat.cols(), mat.rows(), type);
        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(b, 0, targetPixels, 0, b.length);
        return image;
    }
    //duplication
    @SneakyThrows
    private static byte[] toBytes(@NonNull BufferedImage src) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ImageIO.write(src, "png", outputStream);
            return outputStream.toByteArray();
        }
    }

}

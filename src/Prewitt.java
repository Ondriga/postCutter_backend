import java.awt.image.BufferedImage;

public class Prewitt extends EdgeDetector {

    @Override
    public BufferedImage highlightEdge(String sourceImage) {
        return mat2BufferedImage(getGrayScale(sourceImage));
    }

}
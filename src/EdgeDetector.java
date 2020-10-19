package src;

import java.awt.image.BufferedImage;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public abstract class EdgeDetector{
    public abstract BufferedImage highlightEdge(String sourceImage);

    public static Mat getGrayScale(String sourceImage){
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat srcImg = Imgcodecs.imread(sourceImage);
        Mat grayImg = new Mat(srcImg.rows(), srcImg.cols(), srcImg.type());

        Imgproc.cvtColor(srcImg, grayImg, Imgproc.COLOR_RGB2GRAY);
        return grayImg;
    }

    public static BufferedImage mat2BufferedImage(Mat mat){
        int type = 0;
        if (mat.channels() == 1) {
            type = BufferedImage.TYPE_BYTE_GRAY;
        } else if (mat.channels() == 3) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        } else {
            return null;
        }
    
        return new BufferedImage(mat.width(), mat.height(), type);
    }
}
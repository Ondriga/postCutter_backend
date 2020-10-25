/*
 * Source code for the backend of Bachelor thesis.
 * EdgeDetector abstract class
 * 
 * (C) Patrik Ondriga (xondri08)
 */

import java.awt.image.BufferedImage;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * Representing interface for all edge detection class.
 */
public abstract class EdgeDetector{

    /// Method name
    private String methodName;

    /**
     * Constructor
     * @param methodName method name
     */
    public EdgeDetector(String methodName){
        this.methodName = methodName;
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    /**
     * Show all edges in pictures.
     * @param sourceImage picture path
     * @return modified picture with edge highlight in BufferImage format
     */
    public abstract BufferedImage highlightEdge(String sourceImage);

    /**
     * Change color picture into grayscale picture.
     * @param sourceImage picture path
     * @return grayscale matrix
     */
    public static Mat getGrayScale(String sourceImage){
        Mat srcImg = Imgcodecs.imread(sourceImage);
        Mat grayImg = new Mat(srcImg.rows(), srcImg.cols(), srcImg.type());

        Imgproc.cvtColor(srcImg, grayImg, Imgproc.COLOR_RGB2GRAY);
        return grayImg;
    }

    /**
     * Convert "Mat" into "BufferedImage"
     * @param mat picture stored like matrix
     * @return picture stored like BufferedImage
     */
    public static BufferedImage mat2BufferedImage(Mat mat){
        int type = 0;
        if (mat.channels() == 1) {
            type = BufferedImage.TYPE_BYTE_GRAY;
        } else if (mat.channels() == 3) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        } else {
            return null;
        }
        int imageDataLenght = mat.channels()*mat.rows()*mat.cols();
        byte [] buffer = new byte[imageDataLenght];
        mat.get(0, 0, buffer);
        BufferedImage grayImage = new BufferedImage(mat.width(), mat.height(), type);
        grayImage.getRaster().setDataElements(0, 0, mat.cols(), mat.rows(), buffer);

        return grayImage;
    }

    /**
     * Getter for "methodName"
     * @return method name
     */
    public String getMethodName(){
        return this.methodName;
    }
}
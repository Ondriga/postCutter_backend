/*
 * Source code for the backend of Bachelor thesis.
 * EdgeDetector abstract class
 * 
 * (C) Patrik Ondriga (xondri08)
 */

package edgeDetection;

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
    public abstract Mat highlightEdge(String sourceImage);

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
     * Getter for "methodName"
     * @return method name
     */
    public String getMethodName(){
        return this.methodName;
    }
}
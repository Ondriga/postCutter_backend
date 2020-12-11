/*
 * Source code for the backend of Bachelor thesis.
 * Canny class
 * 
 * (C) Patrik Ondriga (xondri08)
 */

package edgeDetection;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

/**
 * Representing canny edge detector.
 */
public class Canny extends EdgeDetector {

    /// Constant for low threshold of canny method
    private static final int THRESHOLD1 = 40;
    /// Constant for high threshold of canny method
    private static final int THRESHOLD2 = THRESHOLD1*3;

    /**
     * Constructor
     * @param methodName method name
     */
    public Canny(String methodName) {
        super(methodName);
    }

    @Override
    public Mat highlightEdge(String sourceImage) {
        Mat srcMat = getGrayScale(sourceImage);
        Mat destMat = new Mat(srcMat.rows(), srcMat.cols(), srcMat.type());

        Imgproc.Canny(srcMat, destMat, THRESHOLD1, THRESHOLD2);

        return destMat;
    }
    
}

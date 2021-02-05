/*
 * Source code for the backend of Bachelor thesis.
 * Canny class
 * 
 * (C) Patrik Ondriga (xondri08)
 */

package postCutter.edgeDetection;

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
    public Mat highlightEdge(Mat picture) {
        Mat destMat = new Mat(picture.rows(), picture.cols(), picture.type());

        Imgproc.Canny(picture, destMat, THRESHOLD1, THRESHOLD2);

        return destMat;
    }
    
}

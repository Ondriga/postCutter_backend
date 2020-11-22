/*
 * Source code for the backend of Bachelor thesis.
 * Laplace class
 * 
 * (C) Patrik Ondriga (xondri08)
 */

package edgeDetection;

import org.opencv.core.CvType;
import org.opencv.core.Mat;

/**
 * Implement convolution method with Laplace operator for edge detector.
 */
public class Laplace extends EdgeDetector {
    /// Kernel size constant
    private static final int KERNELSIZE = 3;
    /// Kernel for convolution
    private Mat kernel;

    /**
     * Constructor
     * 
     * @param methodName method name
     */
    public Laplace(String methodName) {
        super(methodName);

        this.kernel = new Mat(KERNELSIZE, KERNELSIZE, CvType.CV_32F) {
            {
                put(0, 0, 1);
                put(0, 1, 1);
                put(0, 2, 1);

                put(1, 0, 1);
                put(1, 1, -8);
                put(1, 2, 1);

                put(2, 0, 1);
                put(2, 1, 1);
                put(2, 2, 1);
            }
        };
    }

    @Override
    public Mat highlightEdge(String sourceImage) {
        Convolution convolution = new Convolution();
        Mat destMat = convolution.doConvolution(getGrayScale(sourceImage), this.kernel);
        return destMat;
    }
}

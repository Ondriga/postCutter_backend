/*
 * Source code for the backend of Bachelor thesis.
 * Sobel class
 * 
 * (C) Patrik Ondriga (xondri08)
 */

package postCutter.edgeDetection;

import org.opencv.core.CvType;
import org.opencv.core.Mat;

/**
 * Implement convolution method with Sobel operator for edge detector.
 */
public class Sobel extends EdgeDetector {
    /// Kernel size constant
    private static final int KERNELSIZE = 3;
    /// Kernel for vertical convolution
    private Mat verticalKernel;
    /// Kernel for horizontal convolution
    private Mat horizontalKernel;

    /**
     * Constructor
     * 
     * @param methodName method name
     */
    public Sobel(String methodName) {
        super(methodName);

        this.verticalKernel = new Mat(KERNELSIZE, KERNELSIZE, CvType.CV_32F) {
            {
                put(0, 0, -1);
                put(0, 1, 0);
                put(0, 2, 1);

                put(1, 0, -2);
                put(1, 1, 0);
                put(1, 2, 2);

                put(2, 0, -1);
                put(2, 1, 0);
                put(2, 2, 1);
            }
        };
        this.horizontalKernel = new Mat(KERNELSIZE, KERNELSIZE, CvType.CV_32F) {
            {
                put(0, 0, -1);
                put(0, 1, -2);
                put(0, 2, -1);

                put(1, 0, 0);
                put(1, 1, 0);
                put(1, 2, 0);

                put(2, 0, 1);
                put(2, 1, 2);
                put(2, 2, 1);
            }
        };
    }

    @Override
    public Mat highlightEdge(Mat picture) {
        Convolution convolution = new Convolution();
        return convolution.doConvolution(picture, this.verticalKernel, this.horizontalKernel);
    }
}

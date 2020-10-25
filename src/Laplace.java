/*
 * Source code for the backend of Bachelor thesis.
 * Laplace class
 * 
 * (C) Patrik Ondriga (xondri08)
 */

import org.opencv.core.CvType;
import org.opencv.core.Mat;

/**
 * Extended ConvolutionMethod. Add Laplace operator for convolution edge detector.
 */
public class Laplace extends ConvolutionMethod {
    /// Kernel size constant
    private static final int kernelSize = 3;

    /**
     * Constructor
     * @param methodName method name
     */
    public Laplace(String methodName) {
        super(methodName);

        Mat kernelVertical = new Mat(kernelSize,kernelSize, CvType.CV_32F) {
            {
                put(0,0,0);
                put(0,1,1);
                put(0,2,0);

                put(1,0,1);
                put(1,1,-4);
                put(1,2,1);

                put(2,0,0);
                put(2,1,1);
                put(2,2,0);
            }
        };
        Mat kernelHorizontal = new Mat(kernelSize,kernelSize, CvType.CV_32F) {
            {
                put(0,0,1);
                put(0,1,1);
                put(0,2,1);

                put(1,0,1);
                put(1,1,-8);
                put(1,2,1);

                put(2,0,1);
                put(2,1,1);
                put(2,2,1);
            }
        };

        this.setHorizontalKernel(kernelHorizontal);
        this.setVerticalKernel(kernelVertical);
    }
}

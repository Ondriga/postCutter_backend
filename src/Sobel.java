/*
 * Source code for the backend of Bachelor thesis.
 * Sobel class
 * 
 * (C) Patrik Ondriga (xondri08)
 */

import org.opencv.core.CvType;
import org.opencv.core.Mat;

/**
 * Extended ConvolutionMethod. Add Sobel operator for convolution edge detector.
 */
public class Sobel extends ConvolutionMethod{
    /// Kernel size constant
    private static final int kernelSize = 3;

    /**
     * Constructor
     * @param methodName method name
     */
    public Sobel(String methodName) {
        super(methodName);

        Mat kernelVertical = new Mat(kernelSize,kernelSize, CvType.CV_32F) {
            {
                put(0,0,-1);
                put(0,1,0);
                put(0,2,1);

                put(1,0-2);
                put(1,1,0);
                put(1,2,2);

                put(2,0,-1);
                put(2,1,0);
                put(2,2,1);
            }
        };
        Mat kernelHorizontal = new Mat(kernelSize,kernelSize, CvType.CV_32F) {
            {
                put(0,0,-1);
                put(0,1,-2);
                put(0,2,-1);

                put(1,0,0);
                put(1,1,0);
                put(1,2,0);

                put(2,0,1);
                put(2,1,2);
                put(2,2,1);
            }
        };

        this.setHorizontalKernel(kernelHorizontal);
        this.setVerticalKernel(kernelVertical);
    }
}

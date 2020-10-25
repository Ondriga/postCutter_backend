/*
 * Source code for the backend of Bachelor thesis.
 * ConvolutionMethod class
 * 
 * (C) Patrik Ondriga (xondri08)
 */

import java.awt.image.BufferedImage;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

/**
 * Representing convolution method for edge detection, but there missing operator.
 */
public class ConvolutionMethod extends EdgeDetector {
    /// Kernel size constant
    private static final int KERNELSIZE = 1;
    /// Matrix of kernel for detection vertical line
    private Mat kernelVertical;
    /// Matrix of kernel for detection horizontal line
    private Mat kernelHorizontal;

    /**
     * Constructor
     * @param methodName Name of method
     */
    public ConvolutionMethod(String methodName) {
        super(methodName);
        this.kernelVertical = new Mat(KERNELSIZE,KERNELSIZE, CvType.CV_32F) {
            {
                put(0,0,1);
            }
        };
        this.kernelHorizontal = this.kernelVertical;
    }

    @Override
    public BufferedImage highlightEdge(String sourceImage) {
        Mat sourceMat = getGrayScale(sourceImage);
        Mat destinationMat = new Mat(sourceMat.rows(), sourceMat.cols(), sourceMat.type());
        Mat horizontalMat = new Mat(sourceMat.rows(), sourceMat.cols(), sourceMat.type());
        Mat verticalMat = new Mat(sourceMat.rows(), sourceMat.cols(), sourceMat.type());
         
        Imgproc.filter2D(sourceMat, verticalMat, -1, kernelVertical);
        Imgproc.filter2D(sourceMat, horizontalMat, -1, kernelHorizontal);

        return mat2BufferedImage(horizontalMat);
    }

    /**
     * Setter for horizontal kernel.
     * @param kernel kernel Matrix
     */
    public void setHorizontalKernel(Mat kernel){
        this.kernelHorizontal = kernel;
    }

    /**
     * Setter for vertical kernel.
     * @param kernel kernel Matrix
     */
    public void setVerticalKernel(Mat kernel){
        this.kernelVertical = kernel;
    }

}
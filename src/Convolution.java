/*
 * Source code for the backend of Bachelor thesis.
 * Convolution class
 * 
 * (C) Patrik Ondriga (xondri08)
 */

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

/**
 * Make convolution for one or two kernels.
 */
public class Convolution {

    /**
     * Make convolution picture with kernel.
     * @param srcMat picture matrix
     * @param kernel kernel matrix
     * @return convolution matrix of picture and kernel
     */
    public Mat doConvolution(Mat srcMat, Mat kernel) {
        Mat destMat = new Mat(srcMat.rows(), srcMat.cols(), srcMat.type());
        Imgproc.filter2D(srcMat, destMat, -1, kernel);
        
        return destMat;
    }

    /**
     * Make convolution picture with two kernels and calculate gradient.
     * @param srcMat picture matrix
     * @param verticalKernel vertical kernel matrix
     * @param horizontalKernel horizontal kernel matrix
     * @return convolution picture with both kernels
     */
    public Mat doConvolution(Mat srcMat, Mat verticalKernel, Mat horizontalKernel){
        Mat verticalMat = doConvolution(srcMat, verticalKernel);
        Mat horizontalMat = doConvolution(srcMat, horizontalKernel);
        Mat destMat = new Mat(srcMat.rows(), srcMat.cols(), srcMat.type());
        Core.addWeighted( verticalMat, 0.5, horizontalMat, 0.5, 0, destMat );

        return destMat; 
    }
}
import java.awt.image.BufferedImage;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class ConvolutionMethod extends EdgeDetector {
    private static final int kernelSize = 1;
    private Mat kernelVertical;
    private Mat kernelHorizontal;

    public ConvolutionMethod(String methodName) {
        super(methodName);
        this.kernelVertical = new Mat(kernelSize,kernelSize, CvType.CV_32F) {
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

    private Mat 

    public void setHorizontalKernel(Mat kernel){
        this.kernelHorizontal = kernel;
    }

    public void setVerticalKernel(Mat kernel){
        this.kernelVertical = kernel;
    }

}
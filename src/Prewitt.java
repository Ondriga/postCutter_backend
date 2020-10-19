import java.awt.image.BufferedImage;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class Prewitt extends EdgeDetector {
    private static final int kernelSize = 3;

    public Prewitt(String methodName) {
        super(methodName);
    }

    @Override
    public BufferedImage highlightEdge(String sourceImage) {
        Mat sourceMat = getGrayScale(sourceImage);
        Mat destinationMat = new Mat(sourceMat.rows(), sourceMat.cols(), sourceMat.type());

        Mat kernelVertical = new Mat(kernelSize,kernelSize, CvType.CV_32F) {
            {
                put(0,0,-1);
                put(0,1,0);
                put(0,2,1);

                put(1,0-1);
                put(1,1,0);
                put(1,2,1);

                put(2,0,-1);
                put(2,1,0);
                put(2,2,1);
            }
        };

        Mat kernelHorizontal = new Mat(kernelSize,kernelSize, CvType.CV_32F) {
            {
                put(0,0,-1);
                put(0,1,-1);
                put(0,2,-1);

                put(1,0,0);
                put(1,1,0);
                put(1,2,0);

                put(2,0,1);
                put(2,1,1);
                put(2,2,1);
            }
        };	 
         
        Imgproc.filter2D(sourceMat, destinationMat, -1, kernelVertical);

        return mat2BufferedImage(destinationMat);
    }

}
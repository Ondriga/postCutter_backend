import org.opencv.core.CvType;
import org.opencv.core.Mat;

public class Prewitt extends ConvolutionMethod{
    private static final int kernelSize = 3;

    public Prewitt(String methodName) {
        super(methodName);

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

        this.setHorizontalKernel(kernelHorizontal);
        this.setVerticalKernel(kernelVertical);
    }
}

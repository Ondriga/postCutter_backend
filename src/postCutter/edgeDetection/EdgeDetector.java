/*
 * Source code for the backend of Bachelor thesis.
 * EdgeDetector abstract class
 * 
 * (C) Patrik Ondriga (xondri08)
 */

package postCutter.edgeDetection;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

/**
 * Representing interface for all edge detection class.
 */
public abstract class EdgeDetector{

    /// Method name
    private String methodName;

    /**
     * Constructor
     * @param methodName method name
     */
    public EdgeDetector(String methodName){
        this.methodName = methodName;
    }

    /**
     * Show all edges in pictures.
     * @param picture input picture
     * @return modified picture with edge highlight in BufferImage format
     */
    public abstract Mat highlightEdge(Mat picture);

    /**
     * Getter for "methodName"
     * @return method name
     */
    public String getMethodName(){
        return this.methodName;
    }

}
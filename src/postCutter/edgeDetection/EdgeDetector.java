/*
 * Source code for the backend of Bachelor thesis.
 * EdgeDetector abstract class
 * 
 * (C) Patrik Ondriga (xondri08)
 */

package postCutter.edgeDetection;

import org.opencv.core.Mat;

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
/*
 * Source code for the backend of Bachelor thesis.
 * Cutter class
 * 
 * (C) Patrik Ondriga (xondri08)
 */

package postCutter;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Mat;

import postCutter.edgeDetection.*;
import postCutter.geometricShapes.line.LineHandler;
import postCutter.geometricShapes.line.MyLine;
import postCutter.geometricShapes.rectangle.MyRectangle;
import postCutter.geometricShapes.rectangle.RectangleHandler;

/**
 * Representing postCutter class. Providing full cut suggestion for picture.
 */
public class Cutter {
    /// List of edge detection methods.
    private List<EdgeDetector> edgeMethods = new ArrayList<>();
    /// Line handler.
    private LineHandler lineHandler = new LineHandler();
    /// Rectangle handler.
    private RectangleHandler rectangleHandler = new RectangleHandler();

    /**
     * Constructor.
     */
    public Cutter(){
        edgeMethods.add(new Prewitt("PREWITT OPERATOR"));
        edgeMethods.add(new Sobel("SOBEL OPERATOR"));
        edgeMethods.add(new Laplace("LAPLACE OPERATOR"));
        edgeMethods.add(new Canny("CANNY"));
    }

    /**
     * Find cut for picture. This method find vertical, horizontal lines and rectangle for final cut.
     * @param picture for which is cut suggestion.
     */
    public void findCut(Mat picture){
        for(EdgeDetector edgeMethod : edgeMethods){
            lineHandler.findLines(edgeMethod.highlightEdge(picture));
        }
        lineHandler.deleteNoise(picture.cols(), picture.rows());
        rectangleHandler.findRectangle(getHorizontalLines(), getVerticalLines(), picture.cols(), picture.rows());
    }

    /**
     * Getter for horizontal lines.
     * @return list of horizontal lines.
     */
    public List<MyLine> getHorizontalLines(){
        return lineHandler.getHorizontalLines();
    }

    /**
     * Getter for vertical lines.
     * @return list of vertical lines.
     */
    public List<MyLine> getVerticalLines(){
        return lineHandler.getVerticalLines();
    }

    /**
     * Getter for rectangle.
     * @return rectangle.
     */
    public MyRectangle getRectangle(){
        return rectangleHandler.getRectangle();
    }

    /**
     * This method clear all lines and rectangle.
     */
    public void clear(){
        this.lineHandler.clear();
        this.rectangleHandler.clear();
    }
}

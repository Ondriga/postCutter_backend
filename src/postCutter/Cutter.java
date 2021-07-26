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
import org.opencv.imgproc.Imgproc;

import postCutter.edgeDetection.*;
import postCutter.geometricShapes.Coordinate;
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

    private Mat picture = null;

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
     * Setter for original picture.
     * @param picture for cut suggestion.
     */
    public void loadPicture(Mat picture){
        clear();
        this.picture = picture;
        findCut();
    }

    /**
     * Find cut for picture. This method find vertical, horizontal lines and rectangle for final cut.
     */
    private void findCut(){
        if(this.picture != null){
            long startTime = System.nanoTime();//TODO profiling

            Mat grayScale = new Mat();
            Imgproc.cvtColor(this.picture, grayScale, Imgproc.COLOR_RGB2GRAY);
            for(EdgeDetector edgeMethod : edgeMethods){
                lineHandler.findLines(edgeMethod.highlightEdge(grayScale));
            }
            lineHandler.deleteNoise(grayScale.cols(), grayScale.rows());
            rectangleHandler.findRectangle(getHorizontalLines(), getVerticalLines(), grayScale.cols(), grayScale.rows());

            long endTime   = System.nanoTime();//TODO profiling
            double totalTime = (endTime - startTime) / 1000000000.0;//TODO profiling
            System.out.printf("Cas potrebny na vykonanie orezu: %.4f sekund\n", totalTime);//TODO profiling
            System.out.println("");//TODO profiling
        }
    }

    /**
     * Getter for horizontal lines
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
        this.picture = null;
    }

    /**
     * Get cropped picture by rectangle.
     * @return null if original picture is null, otherwise cropped picture by rectangle.
     */
    public Mat getCroppedImage(){
        if(this.picture == null){
            return null;
        }
        Coordinate cornerA = rectangleHandler.getRectangle().getCornerA();
        Coordinate cornerB = rectangleHandler.getRectangle().getCornerB();
        return this.picture.submat(cornerA.getY()+1, cornerB.getY()-1, cornerA.getX()+1, cornerB.getX()-1);
    }
}

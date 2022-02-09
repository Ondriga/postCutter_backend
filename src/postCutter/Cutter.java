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
    /// Matrix of picture.
    private Mat picture = null;
    /// Permissions for edge methods.
    private Boolean[] edgeMethodsPermission = {true, true, true, true};
    /// Monitor of progress.
    private MyProgress progress = null;

    /**
     * Constructor.
     */
    public Cutter(){
        edgeMethods.add(new Canny("CANNY"));
        edgeMethods.add(new Laplace("LAPLACE OPERATOR"));
        edgeMethods.add(new Sobel("SOBEL OPERATOR"));
        edgeMethods.add(new Prewitt("PREWITT OPERATOR"));
    }

    /**
     * Constructor with progress 
     * @param progress object managing progress information
     */
    public Cutter(MyProgress progress){
        this();
        this.progress = progress;
    }

    /**
     * Set permissions for edge methods. At least one method must be allowed.
     * @param prewitt permission.
     * @param sobel permission.
     * @param laplace permission.
     * @param canny permission.
     * @return true if set was successful, otherwise false.
     */
    public boolean setMethodsPermission(boolean prewitt, boolean sobel, boolean laplace, boolean canny){
        if(prewitt || sobel || laplace || canny){
            edgeMethodsPermission[3] = prewitt;
            edgeMethodsPermission[2] = sobel;
            edgeMethodsPermission[1] = laplace;
            edgeMethodsPermission[0] = canny;
            return true;
        }
        return false;
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
     * Set max value for progress bar.
     */
    private void setMaxProgressValue(){
        int maxValue = 0;
        for(int i=0; i<edgeMethods.size(); i++){
            if(edgeMethodsPermission[i]){
                maxValue += this.picture.rows()/10;
            }
        }
        this.progress.setMaxValue(maxValue);
    }

    /**
     * Find cut for picture. This method find vertical, horizontal lines and rectangle for final cut.
     */
    private void findCut(){
        if(this.picture != null){
            if(this.progress != null){
                setMaxProgressValue();
            }
            Mat grayScale = new Mat();
            Imgproc.cvtColor(this.picture, grayScale, Imgproc.COLOR_RGB2GRAY);
            for(int i=0; i<edgeMethods.size(); i++){
                if(edgeMethodsPermission[i]){
                    lineHandler.findLines(edgeMethods.get(i).highlightEdge(grayScale), this.progress);
                }
            }
            if(lineHandler.getStopFlag()){
                this.lineHandler.clear();
                return;
            }
            lineHandler.storeLinesAndDeleteNoise(grayScale.cols(), grayScale.rows());
            rectangleHandler.findRectangle(getHorizontalLines(), getVerticalLines(), grayScale.cols(), grayScale.rows());
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
     * Setter for rectangle.
     * @param rectangle for store.
     */
    public void setRectangle(MyRectangle rectangle){
        rectangleHandler.setRectangle(rectangle);
    }

    /**
     * Getter for rectangle.
     * @return rectangle.
     */
    public MyRectangle getRectangle(){
        return rectangleHandler.getRectangle();
    }

    /**
     * Trigger stop for lineHandler process.
     */
    public void stop(){
        this.lineHandler.stop();
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

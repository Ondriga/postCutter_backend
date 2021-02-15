package postCutter;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Mat;

import postCutter.edgeDetection.*;
import postCutter.geometricShapes.line.LineHandler;
import postCutter.geometricShapes.line.MyLine;
import postCutter.geometricShapes.rectangle.MyRectangle;
import postCutter.geometricShapes.rectangle.RectangleHandler;

public class Cutter {
    private List<EdgeDetector> edgeMethods = new ArrayList<>();
    private LineHandler lineHandler = new LineHandler();
    private RectangleHandler rectangleHandler = new RectangleHandler();

    public Cutter(Mat picture){
        edgeMethods.add(new Prewitt("PREWITT OPERATOR"));
        edgeMethods.add(new Sobel("SOBEL OPERATOR"));
        edgeMethods.add(new Laplace("LAPLACE OPERATOR"));
        edgeMethods.add(new Canny("CANNY"));

        for(EdgeDetector edgeMethod : edgeMethods){
            lineHandler.findLines(edgeMethod.highlightEdge(picture));
        }
        lineHandler.deleteNoise(picture.cols(), picture.rows());
        rectangleHandler.findRectangle(getHorizontalLines(), getVerticalLines(), picture.cols(), picture.rows());
    }

    public List<MyLine> getHorizontalLines(){
        return lineHandler.getHorizontalLines();
    }

    public List<MyLine> getVerticalLines(){
        return lineHandler.getVerticalLines();
    }

    public MyRectangle getRectangle(){
        return rectangleHandler.getRectangle();
    }
}

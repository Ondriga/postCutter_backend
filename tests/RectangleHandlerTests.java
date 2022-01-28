/*
 * Tests for the backend of Bachelor thesis.
 * RectangleHandlerTests class
 * 
 * (C) Patrik Ondriga (xondri08)
 */

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.CvType;

import postCutter.geometricShapes.Coordinate;
import postCutter.geometricShapes.line.LineHandler;
import postCutter.geometricShapes.rectangle.RectangleHandler;

/**
 * Tests for RectangleHandler class.
 */
public class RectangleHandlerTests {
    Mat mat = new Mat(50, 50, CvType.CV_8U, new Scalar(0));
    LineHandler lineHandler = new LineHandler();
    RectangleHandler rectangleHandler = new RectangleHandler();

    /**
     * Test for find rectangle, that is from left side to right side of canvas.
     */
    @Test
    public void rectangleOnEdgeTest(){
        for(int i=0; i<50; i++){
            mat.put(10, i, 255);
            mat.put(40, i, 255);
        }
        lineHandler.findLines(mat, null);
        lineHandler.storeLinesAndDeleteNoise(50, 50);
        rectangleHandler.findRectangle(lineHandler.getHorizontalLines(), lineHandler.getVerticalLines(), 50, 50);

        assertEquals("Corner A wasn`t [0, 10].", new Coordinate(0, 10), rectangleHandler.getRectangle().getCornerA());
        assertEquals("Corner B wasn`t [49, 40].", new Coordinate(49, 40), rectangleHandler.getRectangle().getCornerB());
    }

    /**
     * Test for find rectangle smaller, than width of canvas.
     */
    @Test
    public void smallRectangleTest(){
        for(int i=5; i<=40; i++){
            mat.put(10, i, 255);
            mat.put(40, i, 255);
        }
        for(int i=10; i<=40; i++){
            mat.put(i, 5, 255);
            mat.put(i, 40, 255);
        }
        lineHandler.findLines(mat, null);
        lineHandler.storeLinesAndDeleteNoise(40, 40);
        rectangleHandler.findRectangle(lineHandler.getHorizontalLines(), lineHandler.getVerticalLines(), 50, 50);

        assertEquals("Corner A wasn`t [5, 10].", new Coordinate(5, 10), rectangleHandler.getRectangle().getCornerA());
        assertEquals("Corner B wasn`t [40, 40].", new Coordinate(40, 40), rectangleHandler.getRectangle().getCornerB());
    }

    /**
     * Test where rectangle wasn`t find.
     */
    @Test
    public void emptyCanvasTest(){
        lineHandler.findLines(mat, null);

        rectangleHandler.findRectangle(lineHandler.getHorizontalLines(), lineHandler.getVerticalLines(), 50, 50);

        assertEquals("Corner A wasn`t [0, 0].", new Coordinate(0, 0), rectangleHandler.getRectangle().getCornerA());
        assertEquals("Corner B wasn`t [40, 40].", new Coordinate(49, 49), rectangleHandler.getRectangle().getCornerB());
    }
}

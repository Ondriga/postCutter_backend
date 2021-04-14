/*
 * Tests for the backend of Bachelor thesis.
 * LineHandlerTests class
 * 
 * (C) Patrik Ondriga (xondri08)
 */

import postCutter.geometricShapes.Coordinate;
import postCutter.geometricShapes.line.LineHandler;
import postCutter.geometricShapes.line.MyLine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.CvType;

/**
 * Tests for LineHandler class.
 */
public class LineHandlerTests {
    Mat mat1 = new Mat(60, 60, CvType.CV_8U, new Scalar(0));
    Mat mat2 = new Mat(60, 60, CvType.CV_8U, new Scalar(0));
    LineHandler lineHandler = new LineHandler();

    /**
     * Test for find horizontal line.
     */
    @Test
    public void horizontalLineTest(){
        mat1.put(0, 2, 255);
        mat1.put(1, 0, 255); 
        mat1.put(1, 1, 255); 
        mat1.put(1, 3, 255);
        mat1.put(2, 4, 255);
        for(int i=6; i<=10; i++){
            mat1.put(1, i, 255);
        }
        lineHandler.findLines(mat1);
        
        List<MyLine> list = lineHandler.getHorizontalLines();

        assertEquals("List size test", 1, list.size());
        
        assertTrue("Start point of horizontal line wasn`t [0, 1]", list.get(0).getStartPoint().equals(new Coordinate(0, 1)));
        assertTrue("End point of horizontal line wasn`t [10, 1]", list.get(0).getEndPoint().equals(new Coordinate(10, 1))); 
    }

    /**
     * Test for find vertical line.
     */
    @Test
    public void verticalLineTest(){
        mat1.put(0, 1, 255);
        mat1.put(1, 1, 255); 
        mat1.put(2, 2, 255); 
        mat1.put(3, 1, 255);
        mat1.put(4, 0, 255);
        for(int i=6; i<=10; i++){
            mat1.put(i, 1, 255);
        }

        lineHandler.findLines(mat1);

        List<MyLine> list = lineHandler.getVerticalLines();

        assertEquals("List size test", 1, list.size());
        
        assertTrue("Start point of vertical line wasn`t [1, 0]", list.get(0).getStartPoint().equals(new Coordinate(1, 0)));
        assertTrue("End point of vertical line wasn`t [1, 10]", list.get(0).getEndPoint().equals(new Coordinate(1, 10)));
    }

    /**
     * Test for find horizontal and horizontal lines at the same time. 
     */
    @Test
    public void veticalHorizontalLinesTest(){
        mat1.put(0, 2, 255);
        mat1.put(1, 2, 255);
        mat1.put(1, 4, 255);
        mat1.put(2, 0, 255); 
        mat1.put(2, 1, 255); 
        mat1.put(2, 2, 255);
        mat1.put(4, 2, 255);
        for(int i=5; i<=20; i++){
            mat1.put(2, i, 255);
            mat1.put(i, 2, 255);
        }

        lineHandler.findLines(mat1);

        List<MyLine> listHorizontal = lineHandler.getHorizontalLines();
        List<MyLine> listVertical = lineHandler.getVerticalLines();

        assertEquals("Horizontal list size test", 1, listHorizontal.size());
        assertEquals("Vertical list size test", 1, listVertical.size());
        
        assertTrue("Start point of horizontal line wasn`t [0, 2]", listHorizontal.get(0).getStartPoint().equals(new Coordinate(0, 2)));
        assertTrue("End point of horizontal line wasn`t [20, 2]", listHorizontal.get(0).getEndPoint().equals(new Coordinate(20, 2)));

        assertTrue("Start point of vertical line wasn`t [2, 0]", listVertical.get(0).getStartPoint().equals(new Coordinate(2, 0)));
        assertTrue("End point of vertical line wasn`t [2, 20]", listVertical.get(0).getEndPoint().equals(new Coordinate(2, 20)));
    }

    /**
     * Test for extend horizontal line by line.
     */
    @Test
    public void extendLineByLineHorizontal(){
        for(int i=0; i<=10; i++){
            mat1.put(3, i, 255);
        }
        for(int i=8; i<=30; i++){
            mat2.put(2, i, 255);
        }

        lineHandler.findLines(mat1);
        lineHandler.findLines(mat2);
        List<MyLine> list = lineHandler.getHorizontalLines();

        assertEquals("Horizontal list size test", 1, list.size());
        assertTrue("Start point of horizontal line wasn`t [0, 2]", list.get(0).getStartPoint().equals(new Coordinate(0, 2)));
        assertTrue("End point of horizontal line wasn`t [30, 2]", list.get(0).getEndPoint().equals(new Coordinate(30, 2)));
    }

    /**
     * Test for extend vertical line by line.
     */
    @Test
    public void extendLineByLineVertical(){
        for(int i=0; i<=15; i++){
            mat1.put(i, 0, 255);
        }
        for(int i=13; i<=23; i++){
            mat2.put(i, 1, 255);
        }

        lineHandler.findLines(mat1);
        lineHandler.findLines(mat2);
        List<MyLine> list = lineHandler.getVerticalLines();

        assertEquals("Vertical list size test", 1, list.size());
        assertTrue("Start point of vertical line wasn`t [0, 0]", list.get(0).getStartPoint().equals(new Coordinate(0, 0)));
        assertTrue("End point of vertical line wasn`t [0, 23]", list.get(0).getEndPoint().equals(new Coordinate(0, 23)));
    }

    /**
     * Test for remove small lines from list.
     */
    @Test
    public void removeNoise(){
        for(int i=0; i<=10; i++){
            mat1.put(i, 0, 255);
            mat1.put(i, 6, 255);
            mat1.put(i, 12, 255);
        }
        for(int i=0; i<=23; i++){
            mat1.put(i, 20, 255);
        }

        lineHandler.findLines(mat1);

        assertEquals("List size test before remove noise", 4, lineHandler.getVerticalLines().size());
        lineHandler.deleteNoise(60, 60);
        assertEquals("List size test after remove noise", 1, lineHandler.getVerticalLines().size());
    }

    /**
     * Test for connect 3 horizontal lines.
     */
    @Test
    public void connect3HorizontalLines(){
        for(int i=0; i<=10; i++){
            mat2.put(0, i, 255);
        }
        for(int i=9; i<=20; i++){
            mat1.put(0, i,255);
        }
        for(int i=18; i<=30; i++){
            mat2.put(0, i, 255);
        }

        lineHandler.findLines(mat1);
        lineHandler.findLines(mat2);
        List<MyLine> list = lineHandler.getHorizontalLines();

        assertEquals("Horizontal list size test", 1, list.size());
        assertTrue("Start point of horizontal line wasn`t [0, 0]", list.get(0).getStartPoint().equals(new Coordinate(0, 0)));
    
        assertTrue("End point of horizontal line wasn`t [30, 0]", list.get(0).getEndPoint().equals(new Coordinate(30, 0)));
    }

    /**
     * Test for connect 3 vertical lines.
     */
    @Test
    public void connect3VerticalLines(){
        for(int i=0; i<=10; i++){
            mat1.put(i, 0, 255);
        }
        for(int i=11; i<=21; i++){
            mat2.put(i, 0, 255);
        }
        for(int i=22; i<=33; i++){
            mat1.put(i, 0, 255);
        }

        lineHandler.findLines(mat1);
        lineHandler.findLines(mat2);
        List<MyLine> list = lineHandler.getVerticalLines();

        assertEquals("Vertical list size test", 1, list.size());
        assertTrue("Start point of vertical line wasn`t [0, 0]", list.get(0).getStartPoint().equals(new Coordinate(0, 0)));
        assertTrue("End point of vertical line wasn`t [0, 33]", list.get(0).getEndPoint().equals(new Coordinate(0, 33)));
    }

    /**
     * Test for connect 2 same vertical lines but with different x value.
     */
    @Test
    public void VerticalLinesNearEachOther(){
        for(int i=0; i<=10; i++){
            mat1.put(i, 0, 255);
            mat2.put(i, 2, 255);
        }

        lineHandler.findLines(mat1);
        lineHandler.findLines(mat2);
        List<MyLine> list = lineHandler.getVerticalLines();

        assertEquals("Vertical list size test", 1, list.size());
        assertTrue("Start point of vertical line wasn`t [0, 0]", list.get(0).getStartPoint().equals(new Coordinate(0, 0)));
        assertTrue("End point of vertical line wasn`t [0, 10]", list.get(0).getEndPoint().equals(new Coordinate(0, 10)));
    }

    /**
     * Test for connect 2 same horizontal lines but with different y value.
     */
    @Test
    public void HorizontalLinesNearEachOther(){
        for(int i=0; i<=10; i++){
            mat1.put(0, i, 255);
            mat2.put(2, i, 255);
        }

        lineHandler.findLines(mat1);
        lineHandler.findLines(mat2);
        List<MyLine> list = lineHandler.getHorizontalLines();

        assertEquals("Horizontal list size test", 1, list.size());
        assertTrue("Start point of horizontal line wasn`t [0, 0]", list.get(0).getStartPoint().equals(new Coordinate(0, 0)));
        assertTrue("End point of horizontal line wasn`t [10, 0]", list.get(0).getEndPoint().equals(new Coordinate(10, 0)));
    }

    /**
     * Test for 2 horizontal lines, that covering each other.
     */
    @Test
    public void HorizontalCover(){
        for(int i=10; i<=20; i++){
            mat1.put(0, i, 255);
        }

        for(int i=0; i<=30; i++){
            mat2.put(0, i, 255);
        }

        lineHandler.findLines(mat1);
        lineHandler.findLines(mat2);
        List<MyLine> list = lineHandler.getHorizontalLines();

        assertEquals("Horizontal list size test", 1, list.size());
        assertTrue("Start point of horizontal line wasn`t [0, 0]", list.get(0).getStartPoint().equals(new Coordinate(0, 0)));
        assertTrue("End point of horizontal line wasn`t [30, 0]", list.get(0).getEndPoint().equals(new Coordinate(30, 0)));
    }

    /**
     * Test for horizontal lines, that are on different canvases.
     */
    @Test
    public void HorizontalMultipleSameLines(){
        Mat mat3 = new Mat(60, 60, CvType.CV_8U, new Scalar(0));
        Mat mat4 = new Mat(60, 60, CvType.CV_8U, new Scalar(0));
        for(int i=0; i<=15; i++){
            mat1.put(0, i, 255);
            mat1.put(1, i, 255);
            mat2.put(0, i, 255);
            mat2.put(2, i, 255);
            mat3.put(0, i, 255);
            mat4.put(0, i+2, 255);
        }

        for(int i=0; i<=15; i++){
            mat1.put(10, i, 255);
            mat1.put(11, i, 255);
            mat2.put(10, i, 255);
            mat2.put(12, i, 255);
            mat3.put(10, i, 255);
            mat4.put(10, i+2, 255);
        }

        lineHandler.findLines(mat1);
        lineHandler.findLines(mat2);
        lineHandler.findLines(mat3);
        lineHandler.findLines(mat4);
        List<MyLine> list = lineHandler.getHorizontalLines();

        assertEquals("Horizontal list size test", 2, list.size());
        assertTrue("Start point of first horizontal line wasn`t [0, 0]", list.get(0).getStartPoint().equals(new Coordinate(0, 0)));
        assertTrue("End point of first horizontal line wasn`t [17, 0]", list.get(0).getEndPoint().equals(new Coordinate(17, 0)));
        assertTrue("Start point of second horizontal line wasn`t [0, 10]", list.get(1).getStartPoint().equals(new Coordinate(0, 10)));
        assertTrue("End point of second horizontal line wasn`t [17, 10]", list.get(1).getEndPoint().equals(new Coordinate(17, 10)));
    }

    /**
     * Test for finding vertical lines from rectangle.
     */
    @Test
    public void identicalLinesTest(){
        for(int i=5; i<=40; i++){
            mat1.put(10, i, 255);
            mat1.put(40, i, 255);
        }
        for(int i=10; i<=40; i++){
            mat1.put(i, 5, 255);
            mat1.put(i, 40, 255);
        }

        lineHandler.findLines(mat1);

        List<MyLine> list = lineHandler.getVerticalLines();

        assertEquals("List size test", 2, list.size());
        
        assertTrue("Start point of first vertical line wasn`t [5, 10]", list.get(0).getStartPoint().equals(new Coordinate(5, 10)));
        assertTrue("End point of first vertical line wasn`t [5, 40]", list.get(0).getEndPoint().equals(new Coordinate(5, 40)));

        assertTrue("Start point of first vertical line wasn`t [40, 10]", list.get(1).getStartPoint().equals(new Coordinate(40, 10)));
        assertTrue("End point of first vertical line wasn`t [40, 40]", list.get(1).getEndPoint().equals(new Coordinate(40, 40)));
    }
}

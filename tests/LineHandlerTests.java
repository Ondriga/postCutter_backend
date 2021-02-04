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
import org.opencv.core.Core;

public class LineHandlerTests {
    static{
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    Mat mat1 = new Mat(50, 50, CvType.CV_8U, new Scalar(0));
    Mat mat2 = new Mat(50, 50, CvType.CV_8U, new Scalar(0));
    LineHandler lineHandler = new LineHandler();

    @Test
    public void horizontalLineTest(){
        mat1.put(0, 2, 255);
        mat1.put(1, 0, 255); 
        mat1.put(1, 1, 255); 
        mat1.put(1, 3, 255);
        mat1.put(2, 4, 255); 
        lineHandler.findLines(mat1);
        
        List<MyLine> list = lineHandler.getHorizontalLines();

        assertEquals("List size test", 1, list.size());
        
        assertTrue("Start point of horizontal line wasn`t [0, 1]", list.get(0).getStartPoint().equals(new Coordinate(0, 1)));
        assertTrue("End point of horizontal line wasn`t [4, 1]", list.get(0).getEndPoint().equals(new Coordinate(4, 1))); 
    }

    @Test
    public void verticalLineTest(){
        mat1.put(0, 1, 255);
        mat1.put(1, 2, 255); 
        mat1.put(2, 1, 255); 
        mat1.put(3, 1, 255);
        mat1.put(4, 0, 255);
        lineHandler.findLines(mat1);

        List<MyLine> list = lineHandler.getVerticalLines();

        assertEquals("List size test", 1, list.size());
        
        assertTrue("Start point of vertical line wasn`t [1, 0]", list.get(0).getStartPoint().equals(new Coordinate(1, 0)));
        assertTrue("End point of vertical line wasn`t [1, 4]", list.get(0).getEndPoint().equals(new Coordinate(1, 4)));
    }

    @Test
    public void veticalHorizontalLinesTest(){
        mat1.put(0, 2, 255);
        mat1.put(1, 2, 255);
        mat1.put(1, 4, 255);
        mat1.put(2, 0, 255); 
        mat1.put(2, 1, 255); 
        mat1.put(2, 2, 255);
        mat1.put(4, 2, 255);
        lineHandler.findLines(mat1);

        List<MyLine> listHorizontal = lineHandler.getHorizontalLines();
        List<MyLine> listVertical = lineHandler.getVerticalLines();

        assertEquals("Horizontal list size test", 1, listHorizontal.size());
        assertEquals("Vertical list size test", 1, listVertical.size());
        
        assertTrue("Start point of horizontal line wasn`t [0, 2]", listHorizontal.get(0).getStartPoint().equals(new Coordinate(0, 2)));
        assertTrue("End point of horizontal line wasn`t [4, 2]", listHorizontal.get(0).getEndPoint().equals(new Coordinate(4, 2)));

        assertTrue("Start point of vertical line wasn`t [2, 0]", listVertical.get(0).getStartPoint().equals(new Coordinate(2, 0)));
        assertTrue("End point of vertical line wasn`t [2, 4]", listVertical.get(0).getEndPoint().equals(new Coordinate(2, 4)));
    }

    @Test
    public void extendLineByLineHorizontal(){
        mat1.put(3, 0, 255);
        mat1.put(3, 2, 255);
        mat1.put(3, 3, 255);
        mat1.put(3, 4, 255);

        mat2.put(2, 2, 255);
        mat2.put(2, 3, 255);
        mat2.put(2, 4, 255);
        mat2.put(2, 5, 255);
        mat2.put(2, 6, 255);
        mat2.put(2, 7, 255);

        lineHandler.findLines(mat1);
        lineHandler.findLines(mat2);
        List<MyLine> list = lineHandler.getHorizontalLines();

        assertEquals("Horizontal list size test", 1, list.size());
        assertTrue("Start point of horizontal line wasn`t [0, 2]", list.get(0).getStartPoint().equals(new Coordinate(0, 2)));
        assertTrue("End point of horizontal line wasn`t [7, 2]", list.get(0).getEndPoint().equals(new Coordinate(7, 2)));
    }

    @Test
    public void extendLineByLineVertical(){
        mat1.put(0, 0, 255);
        mat1.put(1, 0, 255);
        mat1.put(2, 0, 255);
        mat1.put(3, 0, 255);
        mat1.put(4, 0, 255);
        mat1.put(5, 0, 255);
        mat1.put(6, 0, 255);

        mat2.put(3, 1, 255);
        mat2.put(4, 1, 255);
        mat2.put(5, 1, 255);
        mat2.put(6, 1, 255);
        mat2.put(7, 1, 255);

        lineHandler.findLines(mat1);
        lineHandler.findLines(mat2);
        List<MyLine> list = lineHandler.getVerticalLines();

        assertEquals("Vertical list size test", 1, list.size());
        assertTrue("Start point of vertical line wasn`t [0, 0]", list.get(0).getStartPoint().equals(new Coordinate(0, 0)));
        assertTrue("End point of vertical line wasn`t [0, 7]", list.get(0).getEndPoint().equals(new Coordinate(0, 7)));
    }

    @Test
    public void removeNoise(){
        mat1.put(0, 0, 255);
        mat1.put(1, 0, 255);
        mat1.put(2, 0, 255);
        mat1.put(3, 0, 255);
        mat1.put(4, 0, 255);
        mat1.put(5, 0, 255);

        mat1.put(0, 20, 255);
        mat1.put(1, 20, 255);
        mat1.put(2, 20, 255);
        mat1.put(3, 20, 255);
        mat1.put(4, 20, 255);
        mat1.put(5, 20, 255);
        mat1.put(6, 20, 255);
        mat1.put(7, 20, 255);
        mat1.put(8, 20, 255);
        mat1.put(9, 20, 255);
        mat1.put(10, 20, 255);
        mat1.put(11, 20, 255);
        mat1.put(12, 20, 255);
        mat1.put(13, 20, 255);
        mat1.put(14, 20, 255);
        mat1.put(15, 20, 255);
        mat1.put(16, 20, 255);
        mat1.put(17, 20, 255);
        mat1.put(18, 20, 255);
        mat1.put(19, 20, 255);
        mat1.put(20, 20, 255);
        mat1.put(21, 20, 255);
        mat1.put(22, 20, 255);
        mat1.put(23, 20, 255);

        lineHandler.findLines(mat1);

        assertEquals("List size test before remove noise", 2, lineHandler.getVerticalLines().size());
        lineHandler.deleteNoise(50, 50);
        assertEquals("List size test after remove noise", 1, lineHandler.getVerticalLines().size());
    }

    @Test
    public void connect3HorizontalLines(){
        mat1.put(0, 0, 255);
        mat1.put(0, 1, 255);
        mat1.put(0, 2, 255);
        mat1.put(0, 3, 255);
        mat1.put(0, 4, 255);
        mat1.put(0, 5, 255);

        mat2.put(0, 6, 255);
        mat2.put(0, 7, 255);
        mat2.put(0, 8, 255);
        mat2.put(0, 9, 255);
        mat2.put(0, 10, 255);
        mat2.put(0, 11, 255);

        mat1.put(0, 14, 255);
        mat1.put(0, 15, 255);
        mat1.put(0, 16, 255);
        mat1.put(0, 17, 255);
        mat1.put(0, 18, 255);
        mat1.put(0, 19, 255);

        lineHandler.findLines(mat1);
        lineHandler.findLines(mat2);
        List<MyLine> list = lineHandler.getHorizontalLines();

        assertEquals("Horizontal list size test", 1, list.size());
        assertTrue("Start point of horizontal line wasn`t [0, 0]", list.get(0).getStartPoint().equals(new Coordinate(0, 0)));
    
        assertTrue("End point of horizontal line wasn`t [19, 0]", list.get(0).getEndPoint().equals(new Coordinate(19, 0)));
    }

    @Test
    public void connect3VerticalLines(){
        mat1.put(0, 0, 255);
        mat1.put(1, 0, 255);
        mat1.put(2, 0, 255);
        mat1.put(3, 0, 255);
        mat1.put(4, 0, 255);
        mat1.put(5, 0, 255);

        mat2.put(6, 0, 255);
        mat2.put(7, 0, 255);
        mat2.put(8, 0, 255);
        mat2.put(9, 0, 255);
        mat2.put(10, 0, 255);
        mat2.put(11, 0, 255);

        mat1.put(14, 0, 255);
        mat1.put(15, 0, 255);
        mat1.put(16, 0, 255);
        mat1.put(17, 0, 255);
        mat1.put(18, 0, 255);
        mat1.put(19, 0, 255);

        lineHandler.findLines(mat1);
        lineHandler.findLines(mat2);
        List<MyLine> list = lineHandler.getVerticalLines();

        assertEquals("Vertical list size test", 1, list.size());
        assertTrue("Start point of vertical line wasn`t [0, 0]", list.get(0).getStartPoint().equals(new Coordinate(0, 0)));
        assertTrue("End point of vertical line wasn`t [0, 19]", list.get(0).getEndPoint().equals(new Coordinate(0, 19)));
    }
}

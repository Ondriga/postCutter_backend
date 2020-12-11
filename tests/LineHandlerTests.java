import line.Coordinate;
import line.LineHandler;
import line.MyLine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.opencv.core.Mat;
import org.opencv.core.CvType;
import org.opencv.core.Core;

public class LineHandlerTests {
    static{
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    Mat mat = new Mat(4, 4, CvType.CV_8U);
    LineHandler lineHandler = new LineHandler();

    @Test
    public void horizontalLineTest(){
        mat.put(0, 0, 255, 0, 255, 255, 0, 255, 0, 0, 0, 0, 0, 0, 0, 0);
        lineHandler.findLines(mat);
        
        List<MyLine> list = lineHandler.getHorizontalLines();

        assertEquals("List size test", 3, list.size());
        
        assertTrue("Start point of first line wasn`t [0, 0]", list.get(0).getStartPoint().equals(new Coordinate(0, 0)));
        assertTrue("End point of first line wasn`t [3, 0]", list.get(0).getEndPoint().equals(new Coordinate(3, 0)));

        assertTrue("Start point of second line wasn`t [0, 3]", list.get(1).getStartPoint().equals(new Coordinate(0, 3)));
        assertTrue("End point of second line wasn`t [3, 3]", list.get(1).getEndPoint().equals(new Coordinate(3, 3)));
        
        assertTrue("Start point of third line wasn`t [0, 1]", list.get(2).getStartPoint().equals(new Coordinate(0, 1)));
        assertTrue("End point of third line wasn`t [3, 1]", list.get(2).getEndPoint().equals(new Coordinate(3, 1))); 
    }

    @Test
    public void verticalLineTest(){
        mat.put(0, 255, 0, 0, 0, 0, 255, 0, 0, 255, 0, 0, 0, 255, 0, 0);
        lineHandler.findLines(mat);

        List<MyLine> list = lineHandler.getVerticalLines();

        assertEquals("List size test", 3, list.size());

        assertTrue("Start point of first line wasn`t [0, 0]", list.get(0).getStartPoint().equals(new Coordinate(0, 0)));
        assertTrue("End point of first line wasn`t [0, 3]", list.get(0).getEndPoint().equals(new Coordinate(0, 3)));

        assertTrue("Start point of second line wasn`t [3, 0]", list.get(1).getStartPoint().equals(new Coordinate(3, 0)));
        assertTrue("End point of second line wasn`t [3, 3]", list.get(1).getEndPoint().equals(new Coordinate(3, 3)));
        
        assertTrue("Start point of third line wasn`t [1, 0]", list.get(2).getStartPoint().equals(new Coordinate(1, 0)));
        assertTrue("End point of third line wasn`t [1, 3]", list.get(2).getEndPoint().equals(new Coordinate(1, 3)));
    }

    @Test
    public void veticalHorizontalLinesTest(){
        mat.put(0, 0, 0, 0, 0, 0, 255, 0, 255, 255, 255, 0, 0, 0, 0, 0);
        lineHandler.findLines(mat);

        List<MyLine> listHorizontal = lineHandler.getHorizontalLines();
        List<MyLine> listVertical = lineHandler.getVerticalLines();

        assertEquals("Horizontal list size test", 3, listHorizontal.size());
        assertEquals("Vertical list size test", 3, listVertical.size());
        
        assertTrue("Start point of third horizontal line wasn`t [0, 2]", listHorizontal.get(2).getStartPoint().equals(new Coordinate(0, 2)));
        assertTrue("End point of third horizontal line wasn`t [2, 2]", listHorizontal.get(2).getEndPoint().equals(new Coordinate(2, 2)));

        assertTrue("Start point of third vertical line wasn`t [2, 1]", listHorizontal.get(2).getStartPoint().equals(new Coordinate(2, 1)));
        assertTrue("End point of third vertical line wasn`t [2, 2]", listHorizontal.get(2).getEndPoint().equals(new Coordinate(2, 2)));
    }
}

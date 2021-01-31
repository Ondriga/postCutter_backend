import line.Coordinate;
import line.LineHandler;
import line.MyLine;

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

    Mat mat = new Mat(8, 8, CvType.CV_8U, new Scalar(0));
    LineHandler lineHandler = new LineHandler();

    @Test
    public void horizontalLineTest(){
        mat.put(0, 2, 255);
        mat.put(1, 0, 255); 
        mat.put(1, 1, 255); 
        mat.put(1, 3, 255); 
        lineHandler.findLines(mat);
        
        List<MyLine> list = lineHandler.getHorizontalLines();

        assertEquals("List size test", 1, list.size());
        
        assertTrue("Start point of horizontal line wasn`t [0, 1]", list.get(0).getStartPoint().equals(new Coordinate(0, 1)));
        assertTrue("End point of horizontal line wasn`t [3, 1]", list.get(0).getEndPoint().equals(new Coordinate(3, 1))); 
    }

    @Test
    public void verticalLineTest(){
        mat.put(0, 1, 255);
        mat.put(1, 2, 255); 
        mat.put(2, 1, 255); 
        mat.put(3, 1, 255); 
        lineHandler.findLines(mat);

        List<MyLine> list = lineHandler.getVerticalLines();

        assertEquals("List size test", 1, list.size());
        
        assertTrue("Start point of vertical line wasn`t [1, 0]", list.get(0).getStartPoint().equals(new Coordinate(1, 0)));
        assertTrue("End point of vertical line wasn`t [1, 3]", list.get(0).getEndPoint().equals(new Coordinate(1, 3)));
    }

    @Test
    public void veticalHorizontalLinesTest(){
        mat.put(1, 2, 255);
        mat.put(2, 0, 255); 
        mat.put(2, 1, 255); 
        mat.put(2, 2, 255);
        lineHandler.findLines(mat);

        List<MyLine> listHorizontal = lineHandler.getHorizontalLines();
        List<MyLine> listVertical = lineHandler.getVerticalLines();

        assertEquals("Horizontal list size test", 1, listHorizontal.size());
        assertEquals("Vertical list size test", 1, listVertical.size());
        
        assertTrue("Start point of third horizontal line wasn`t [0, 2]", listHorizontal.get(0).getStartPoint().equals(new Coordinate(0, 2)));
        assertTrue("End point of third horizontal line wasn`t [2, 2]", listHorizontal.get(0).getEndPoint().equals(new Coordinate(2, 2)));

        assertTrue("Start point of third vertical line wasn`t [2, 1]", listVertical.get(0).getStartPoint().equals(new Coordinate(2, 1)));
        assertTrue("End point of third vertical line wasn`t [2, 2]", listVertical.get(0).getEndPoint().equals(new Coordinate(2, 2)));
    }
}

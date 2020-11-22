import line.Coordinate;
import line.LineHandler;
import line.MyLine;

import static org.junit.Assert.assertEquals;
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
        mat.put(0, 0, 0, 0, 255, 0, 0, 0, 255, 0, 0, 255, 255, 0, 0, 0, 0, 0);
        lineHandler.findLines(mat);
        
        List<MyLine> list = lineHandler.getHorizontalLines();

        assertEquals(3, list.size());
        
        assertTrue(list.get(0).getStartPoint().equals(new Coordinate(0, 0)));
        assertTrue(list.get(0).getEndPoint().equals(new Coordinate(3, 0)));

        assertTrue(list.get(1).getStartPoint().equals(new Coordinate(0, 3)));
        assertTrue(list.get(1).getEndPoint().equals(new Coordinate(3, 3)));
        
        assertTrue(list.get(2).getStartPoint().equals(new Coordinate(1, 2)));
        assertTrue(list.get(2).getEndPoint().equals(new Coordinate(2, 2))); 
    }

    @Test
    public void verticalLineTest(){
        mat.put(0, 0, 0, 0, 255, 0, 0, 0, 255, 0, 0, 255, 255, 0, 0, 0, 0, 0);
        lineHandler.findLines(mat);

        List<MyLine> list = lineHandler.getVerticalLines();

        assertEquals(3, list.size());

        assertTrue(list.get(0).getStartPoint().equals(new Coordinate(0, 0)));
        assertTrue(list.get(0).getEndPoint().equals(new Coordinate(0, 3)));

        assertTrue(list.get(1).getStartPoint().equals(new Coordinate(3, 0)));
        assertTrue(list.get(1).getEndPoint().equals(new Coordinate(3, 3)));
        
        assertTrue(list.get(2).getStartPoint().equals(new Coordinate(2, 0)));
        assertTrue(list.get(2).getEndPoint().equals(new Coordinate(2, 2)));
    }
}

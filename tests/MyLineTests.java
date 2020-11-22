import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import line.*;

public class MyLineTests {
    Coordinate coor1 = new Coordinate(5, 20);
    Coordinate coor2 = new Coordinate(5, 40);
    Coordinate coor3 = new Coordinate(6, 3);
    Coordinate coor4 = new Coordinate(7, 3);

    MyLine line1 = MyLine.createLine(coor1, coor2);
    MyLine line2 = MyLine.createLine(coor3, coor4);

    @Test
    public void createMyLineTest(){
        assertNotNull(line1);
        assertNotNull(line2);
        assertNull(MyLine.createLine(coor1, coor3));
    }

    @Test
    public void coordinateOrderTest(){
        MyLine line3 = MyLine.createLine(coor2, coor1);
        MyLine line4 = MyLine.createLine(coor4, coor3);
        
        assertTrue(line1.getStartPoint().equals(coor1));
        assertTrue(line1.getEndPoint().equals(coor2));
        
        assertTrue(line2.getStartPoint().equals(coor3));
        assertTrue(line2.getEndPoint().equals(coor4));

        assertTrue(line3.getStartPoint().equals(coor1));
        assertTrue(line3.getEndPoint().equals(coor2));

        assertTrue(line4.getStartPoint().equals(coor3));
        assertTrue(line4.getEndPoint().equals(coor4));
    }
    
    @Test
    public void lineLengthTest(){
        assertEquals(21, line1.length());
        assertEquals(2, line2.length());
    }

    @Test
    public void extendLineTest(){
        assertFalse(line1.extendByOne(new Coordinate(4, 35)));
        assertTrue(line1.getStartPoint().equals(coor1));
        assertFalse(line1.extendByOne(new Coordinate(4, 20)));
        assertFalse(line1.extendByOne(new Coordinate(4, 40)));
        assertFalse(line1.extendByOne(new Coordinate(6, 20)));
        assertFalse(line1.extendByOne(new Coordinate(6, 40)));
        assertFalse(line1.extendByOne(new Coordinate(5, 21)));
        assertFalse(line1.extendByOne(new Coordinate(5, 39)));
        assertFalse(line1.extendByOne(new Coordinate(5, 18)));
        assertTrue(line1.extendByOne(new Coordinate(5, 19)));
        assertTrue(line1.extendByOne(new Coordinate(5, 41)));
        assertTrue(line1.getStartPoint().equals(new Coordinate(5, 19)));
        assertTrue(line1.getEndPoint().equals(new Coordinate(5, 41)));

        assertFalse(line2.extendByOne(new Coordinate(6, 3)));
        assertFalse(line2.extendByOne(new Coordinate(9, 3)));
        assertTrue(line2.extendByOne(new Coordinate(5, 3)));
        assertTrue(line2.getStartPoint().equals(new Coordinate(5, 3)));
    }
}

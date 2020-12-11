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
    MyLine linedot = MyLine.createLine(coor1, coor1);

    @Test
    public void createMyLineTest(){
        assertNotNull("Test if create was successful (vertical line)", line1);
        assertNotNull("Test if create was successful (horizontal line)", line2);
        assertNull("Test if create wasn`t successful (diagonal line)", MyLine.createLine(coor1, coor3));
        assertNotNull("Test if create was successful (dot)", linedot);
    }

    @Test
    public void coordinateOrderTest(){
        MyLine line3 = MyLine.createLine(coor2, coor1);
        MyLine line4 = MyLine.createLine(coor4, coor3);
        
        assertTrue("Bad starting point (vertical line, create with correct order)", line1.getStartPoint().equals(coor1));
        assertTrue("Bad ending point (vertical line, create with correct order)", line1.getEndPoint().equals(coor2));
        
        assertTrue("Bad starting point (horizontal line, create with correct order)", line2.getStartPoint().equals(coor3));
        assertTrue("Bad ending point (horizontal line, create with correct order)", line2.getEndPoint().equals(coor4));

        assertTrue("Bad starting point (vertical line, create with wrong order)", line3.getStartPoint().equals(coor1));
        assertTrue("Bad ending point (vertical line, create with wrong order)", line3.getEndPoint().equals(coor2));

        assertTrue("Bad starting point (horizontal line, create with wrong order)", line4.getStartPoint().equals(coor3));
        assertTrue("Bad ending point (horizontal line, create with wrong order)", line4.getEndPoint().equals(coor4));
    }
    
    @Test
    public void lineLengthTest(){
        assertEquals("List size test", 21, line1.length());
        assertEquals("List size test", 2, line2.length());
    }

    @Test
    public void extendLineTest(){
        assertFalse("Try extend with wrong coordinate. (vertical line coordinates[5, 20][5, 40], new coordinate[4, 35])", line1.extendByOne(new Coordinate(4, 35)));
        assertTrue("Start point was changed after bad extend by one. (vertical line)", line1.getStartPoint().equals(coor1));
        assertFalse("Try extend with wrong coordinate. (vertical line coordinates[5, 20][5, 40], new coordinate[4, 20])", line1.extendByOne(new Coordinate(4, 20)));
        assertFalse("Try extend with wrong coordinate. (vertical line coordinates[5, 20][5, 40], new coordinate[4, 40])", line1.extendByOne(new Coordinate(4, 40)));
        assertFalse("Try extend with wrong coordinate. (vertical line coordinates[5, 20][5, 40], new coordinate[6, 20])", line1.extendByOne(new Coordinate(6, 20)));
        assertFalse("Try extend with wrong coordinate. (vertical line coordinates[5, 20][5, 40], new coordinate[6, 40])", line1.extendByOne(new Coordinate(6, 40)));
        assertFalse("Try extend with wrong coordinate. (vertical line coordinates[5, 20][5, 40], new coordinate[5, 21])", line1.extendByOne(new Coordinate(5, 21)));
        assertFalse("Try extend with wrong coordinate. (vertical line coordinates[5, 20][5, 40], new coordinate[5, 39])", line1.extendByOne(new Coordinate(5, 39)));
        assertFalse("Try extend with wrong coordinate. (vertical line coordinates[5, 20][5, 40], new coordinate[5, 18])", line1.extendByOne(new Coordinate(5, 18)));
        assertTrue("Try extend with correct coordinate. (vertical line coordinates[5, 20][5, 40], new coordinate[5, 19])", line1.extendByOne(new Coordinate(5, 19)));
        assertTrue("Try extend with correct coordinate. (vertical line coordinates[5, 20][5, 40], new coordinate[5, 41])", line1.extendByOne(new Coordinate(5, 41)));
        assertTrue("Start point wasn`t changed after successful extend by one. (vertical line)", line1.getStartPoint().equals(new Coordinate(5, 19)));
        assertTrue("End point wasn`t changed after successful extend by one. (vertical line)", line1.getEndPoint().equals(new Coordinate(5, 41)));

        assertFalse("Try extend with wrong coordinate. (horizontal line coordinates[6, 3][7, 3], new coordinate[6, 3])", line2.extendByOne(new Coordinate(6, 3)));
        assertFalse("Try extend with wrong coordinate. (horizontal line coordinates[6, 3][7, 3], new coordinate[9, 3])", line2.extendByOne(new Coordinate(9, 3)));
        assertTrue("Try extend with correct coordinate. (horizontal line coordinates[6, 3][7, 3], new coordinate[5, 3])", line2.extendByOne(new Coordinate(5, 3)));
        assertTrue("Start point wasn`t changed after successful extend by one. (horizontal line)", line2.getStartPoint().equals(new Coordinate(5, 3)));

        assertTrue("Try extend with correct coordinate. (dot coordinates[5, 20][5, 20], new coordinate[5, 21])", linedot.extendByOne(new Coordinate(5, 21)));
        assertTrue("Start point wasn`t changed after successful extend by one. (from dot to vertical line)", linedot.getStartPoint().equals(coor1));
        assertTrue("End point wasn`t changed after successful extend by one. (from dot to vertical line)", linedot.getEndPoint().equals(new Coordinate(5, 21)));

        MyLine lineDotHorizontal = MyLine.createLine(coor1, coor1);

        assertTrue("Try extend with correct coordinate. (dot coordinates[5, 20][5, 20], new coordinate[6, 20])", lineDotHorizontal.extendByOne(new Coordinate(6, 20)));
        assertTrue("Start point wasn`t changed after successful extend by one. (from dot to horizontal line)", lineDotHorizontal.getStartPoint().equals(coor1));
        assertTrue("End point wasn`t changed after successful extend by one. (from dot to horizontal line)", lineDotHorizontal.getEndPoint().equals(new Coordinate(6, 20)));
    }
}

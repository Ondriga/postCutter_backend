import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import postCutter.geometricShapes.line.*;
import postCutter.geometricShapes.Coordinate;

public class MyLineTests {
    Coordinate coor1 = new Coordinate(5, 20);
    Coordinate coor2 = new Coordinate(5, 40);
    Coordinate coor3 = new Coordinate(6, 3);
    Coordinate coor4 = new Coordinate(7, 3);
    Coordinate coor5 = new Coordinate(5, 80);
    Coordinate coor6 = new Coordinate(10, 20);
    Coordinate coor7 = new Coordinate(10, 80);
    Coordinate coor8 = new Coordinate(0, 40);
    Coordinate coor9 = new Coordinate(0, 80);
    Coordinate coor10 = new Coordinate(7, 43);
    Coordinate coor11 = new Coordinate(7, 200);
    Coordinate coor12 = new Coordinate(20, 20);
    Coordinate coor13 = new Coordinate(40, 20);
    Coordinate coor14 = new Coordinate(20, 0);
    Coordinate coor15 = new Coordinate(40, 0);
    Coordinate coor16 = new Coordinate(40, 3);
    Coordinate coor17 = new Coordinate(9, 2);
    Coordinate coor18 = new Coordinate(200, 2);

    MyLine line1 = VerticalLine.createLine(coor1, coor2);
    MyLine line2 = HorizontalLine.createLine(coor3, coor4);
    MyLine linedot = VerticalLine.createLine(coor1, coor1);

    @Test
    public void createMyLineTest(){
        assertNotNull("Test if create was successful (vertical line)", line1);
        assertNotNull("Test if create was successful (horizontal line)", line2);
        assertNull("Test if create wasn`t successful (diagonal line)", HorizontalLine.createLine(coor1, coor3));
        assertNotNull("Test if create was successful (dot)", linedot);
    }

    @Test
    public void coordinateOrderTest(){
        MyLine line3 = VerticalLine.createLine(coor2, coor1);
        MyLine line4 = HorizontalLine.createLine(coor4, coor3);
        
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
    public void extendLineByDotTest(){
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

        MyLine lineDotHorizontal = HorizontalLine.createLine(coor1, coor1);

        assertTrue("Try extend with correct coordinate. (dot coordinates[5, 20][5, 20], new coordinate[6, 20])", lineDotHorizontal.extendByOne(new Coordinate(6, 20)));
        assertTrue("Start point wasn`t changed after successful extend by one. (from dot to horizontal line)", lineDotHorizontal.getStartPoint().equals(coor1));
        assertTrue("End point wasn`t changed after successful extend by one. (from dot to horizontal line)", lineDotHorizontal.getEndPoint().equals(new Coordinate(6, 20)));
    }

    @Test
    public void extendLineByLineTestForVertical(){
        MyLine vertical1 = VerticalLine.createLine(coor6, coor7);
        MyLine vertical2 = VerticalLine.createLine(coor8, coor9);
        MyLine vertical3 = VerticalLine.createLine(coor2, coor5);
        MyLine vertical4 = VerticalLine.createLine(coor10, coor11);

        assertEquals("Test, if extend fail", -1, line1.extendByLine(vertical1));
        assertEquals("Test, if extend fail", 1, line1.extendByLine(vertical2));

        assertEquals("Test if extend by line with one same coordinate", 0, line1.extendByLine(vertical3));
        assertTrue("Try extend with connect line. (New start of vertical line coordinates[5, 20])", line1.getStartPoint().equals(coor1));
        assertTrue("Try extend with connect line. (New end of vertical line coordinates[5, 80])", line1.getEndPoint().equals(coor5));

        assertEquals("Test if extend by line with coordinate near the end of line", 0, line1.extendByLine(vertical4));
        assertTrue("Try extend with connect line. (New start of vertical line coordinates[7, 20])", line1.getStartPoint().equals(new Coordinate(7, 20)));
        assertTrue("Try extend with connect line. (New end of vertical line coordinates[7, 200])", line1.getEndPoint().equals(coor11));
    }

    @Test
    public void extendLineByLineTestForHorizontall(){
        MyLine horizontal1 = HorizontalLine.createLine(coor12, coor13);
        MyLine horizontal2 = HorizontalLine.createLine(coor14, coor15);
        MyLine horizontal3 = HorizontalLine.createLine(coor4, coor16);
        MyLine horizontal4 = HorizontalLine.createLine(coor17, coor18);

        assertEquals("Test, if extend fail", -1, line2.extendByLine(horizontal1));
        assertEquals("Test, if extend fail", 1, line2.extendByLine(horizontal2));

        assertEquals("Test if extend by line with one same coordinate", 0, line2.extendByLine(horizontal3));
        assertTrue("Try extend with connect line. (New start of horizontal line coordinates[6, 3])", line2.getStartPoint().equals(coor3));
        assertTrue("Try extend with connect line. (New end of horizontal line coordinates[40, 3])", line2.getEndPoint().equals(coor16));
        
        assertEquals("Test if extend by line with coordinate near the end of line", 0, line2.extendByLine(horizontal4));
        assertTrue("Try extend with connect line. (New start of horizontal line coordinates[6, 2])", line2.getStartPoint().equals(new Coordinate(6, 2)));
        assertTrue("Try extend with connect line. (New end of horizontal line coordinates[200, 2])", line2.getEndPoint().equals(coor18));
    }

    @Test
    public void similarTest(){
        MyLine verticalLine1 = VerticalLine.createLine(new Coordinate(20, 20), new Coordinate(20, 44));
        MyLine verticalLine2 = VerticalLine.createLine(new Coordinate(10, 40), new Coordinate(10, 88));
        MyLine horizontalLine1 = HorizontalLine.createLine(new Coordinate(3, 20), new Coordinate(7, 20));
        MyLine horizontalLine2 = HorizontalLine.createLine(new Coordinate(50, 20), new Coordinate(90, 20));

        assertTrue("Vertical lines are similar.", verticalLine1.isSimilar(line1));
        assertFalse("Vertical lines are different.", verticalLine2.isSimilar(line1));
        
        assertTrue("Horizontal lines are similar", horizontalLine1.isSimilar(line2));
        assertFalse("Horizontal lines are different", horizontalLine2.isSimilar(line2));

        assertFalse("One line is vertical and second is horizontal.", line1.isSimilar(line2));
    }
}

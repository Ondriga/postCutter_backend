/*
 * Tests for the backend of Bachelor thesis.
 * CoordinateTests class
 * 
 * (C) Patrik Ondriga (xondri08)
 */

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import postCutter.geometricShapes.Coordinate;

/**
 * Tests for Coordinate class.
 */
public class CoordinateTests {
    Coordinate coord1 = new Coordinate(4, 5);
    Coordinate coord2 = new Coordinate(4, 5);
    Coordinate coord3 = new Coordinate(5, 4);

    /**
     * Test if 2 coordinates equals.
     */
    @Test
    public void testCoordinateEquals(){
        assertTrue("Coordinates have the same value of x and y.", coord1.equals(coord2));
        assertFalse("Coordinates have different value x or y. [coordinate1(4, 5) coordinate2(5, 4)]", coord1.equals(coord3));
    }
}

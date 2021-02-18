/*
 * Tests for the backend of Bachelor thesis.
 * MyRectangleTests class
 * 
 * (C) Patrik Ondriga (xondri08)
 */

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import postCutter.geometricShapes.Coordinate;
import postCutter.geometricShapes.rectangle.MyRectangle;

/**
 * Tests for MyRectangle class.
 */
public class MyRectangleTests {
    Coordinate corner1 = new Coordinate(5, 5);
    Coordinate corner2 = new Coordinate(20, 20);

    /**
     * Test for create rectangle.
     */
    @Test
    public void createTest(){
        assertNotNull("Corect corner coordinates.", MyRectangle.createRectangle(corner1, corner2));
        assertNull("Corner coordinates are the same.", MyRectangle.createRectangle(corner1, corner1));
        assertNull("Wrong order of corners.", MyRectangle.createRectangle(corner2, corner1));
    }
}

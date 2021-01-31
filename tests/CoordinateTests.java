import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import postCutter.cutting.geometricShapes.Coordinate;

public class CoordinateTests {
    Coordinate coord1 = new Coordinate(4, 5);
    Coordinate coord2 = new Coordinate(4, 5);
    Coordinate coord3 = new Coordinate(5, 4);

    @Test
    public void testCoordinateEquals(){
        assertTrue("Coordinates have the same value of x and y.", coord1.equals(coord2));
        assertFalse("Coordinates have different value x or y. [coordinate1(4, 5) coordinate2(5, 4)]", coord1.equals(coord3));
    }
}

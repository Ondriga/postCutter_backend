import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import line.Coordinate;

public class CoordinateTests {
    Coordinate coord1 = new Coordinate(4, 5);
    Coordinate coord2 = new Coordinate(4, 5);
    Coordinate coord3 = new Coordinate(5, 4);

    @Test
    public void testCoordinateEquals(){
        assertTrue(coord1.equals(coord2));
        assertFalse(coord1.equals(coord3));
    }
}

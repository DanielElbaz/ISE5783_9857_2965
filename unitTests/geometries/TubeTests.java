package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class TubeTests {

    @Test
    void testGetNormal() {
        Tube tube = new Tube(1, new Ray(new Point(0, 0, 0), new Vector(1, 0, 0)));

        // ============ Equivalence Partitions Tests ==============
        // TC01: tests for calculation of normal to the tube
        assertEquals(new Vector(0, 0, 1),
                tube.getNormal(new Point(1, 0, 1)),
                "ERROR: Wrong calculation of normal!!");

        // =============== Boundary Values Tests ==================
        // TC02: Test when the point is orthogonal to the ray's head goes to the ZERO vector
        assertThrows(IllegalArgumentException.class, () -> tube.getNormal(new Point(1, 0, 0)),
                "Since when ZERO vector is allowed?!");
    }
}
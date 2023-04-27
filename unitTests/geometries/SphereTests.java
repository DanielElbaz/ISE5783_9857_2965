package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class SphereTests {

    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: tests for calculation of normal to the sphere//
        Sphere sphere = new Sphere(5, new Point(0, 0, 0));

        assertEquals(new Vector(0, 0, 1), sphere.getNormal(new Point(0, 0, 5)),
                "ERROR: Wrong calculation of the normal!!");
    }
}
package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class TriangleTests {
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: tests for calculation of normal to the triangle
        Triangle pl = new Triangle(
                new Point(0, 0, 1),
                new Point(1, 0, 0),
                new Point(0, 1, 0));
        double sqrt3 = Math.sqrt(1d / 3);

        assertEquals(new Vector(sqrt3, sqrt3, sqrt3), pl.getNormal(new Point(0, 0, 1)),
                "ERROR: What is that normal?!");
    }

}
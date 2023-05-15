package primitives;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RayTest {

    @Test
    void findClosestPointTest() {
        Ray ray = new Ray(new Point(1,0,1),
                new Vector(-1,0,0));
        List<Point> points;

        // ============ Equivalence Partitions Tests ==============
        // The closest point is somewhere in the middle of the list
        points = List.of(new Point(-8,0,1),
                new Point(-2,0,1),
                new Point(0,0,1),
                new Point(-3,0,1),
                new Point(-5,0,1));
        assertEquals(new Point(0,0,1), ray.findClosestPoint(points), "point in the middle");

        // =============== Boundary Values Tests ==================
        // The closest point is in the beginning of list
        points = List.of(new Point(0,0,1),
                new Point(-8,0,1),
                new Point(-3,0,1),
                new Point(-5,0,1));
        assertEquals(new Point(0,0,1), ray.findClosestPoint(points), "point in the beginning");

        // The closest point is in the end of list
        points = List.of(new Point(-8,0,1),
                new Point(-2,0,1),
                new Point(-3,0,1),
                new Point(-5,0,1),
                new Point(0,0,1));
        assertEquals(new Point(0,0,1), ray.findClosestPoint(points), "point in the end");

        // Empty list
        points = new ArrayList<>();
        assertNull(ray.findClosestPoint(points), "where the hell did you find this point?!!!");
    }
}
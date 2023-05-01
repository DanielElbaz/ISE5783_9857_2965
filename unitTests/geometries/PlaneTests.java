package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTests {

    @Test
    public void testConstructor() {
        Point p1 = new Point(1, 0, 0);
        Point p2 = new Point(0, 1, 0);
        Point p3 = new Point(0, 0, 1);
        try {
            new Plane(p1, p2, p3);
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct plane \n" + e.getMessage());
        }
        assertThrows(IllegalArgumentException.class,
                () -> new Plane(new Point(1, 0, 0),
                        new Point(2, 0, 0),
                        new Point(1, 1, 1)),
                "Constructed a plane with 3 points that are in the same line");

    }

    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============//
        Plane plane = new Plane(
                new Point(1, 0, 0),
                new Point(0, 1, 0),
                new Point(0, 0, 1));

        double sqrt3 = Math.sqrt(1d / 3);
        assertTrue(plane.getNormal().equals(new Vector(sqrt3, sqrt3, sqrt3)) ||
                        plane.getNormal().scale(-1).equals(new Vector(sqrt3, sqrt3, sqrt3)) || plane.getNormal().length() != 1,
                "Ouch! The normal is wrong! :/ \n");
    }

    /**
     * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Plane plane = new Plane(
                new Point(1, -1, 0),
                new Point(1, 1, 0),
                new Point(0, 0, 1));

        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray's line is cross the plane (1 point)
        Point p0 = new Point(2,0,-1);
        List<Point> result = plane.findIntersections(new Ray(new Point(2,0,0),
                new Vector(0,0,-1)));
        assertEquals(1,result.size(),"it must be one intersection!!!");
        assertEquals(List.of(p0),result,"wrong point...");
        // TC02: Ray's line doesn't cross the plane (0 points)
        assertNull(plane.findIntersections(new Ray(new Point(2,0,0),
                new Vector(0,0,1))),"hey! there ara no intersections at all!!!");
        // =============== Boundary Values Tests ==================
        // **** Group: ray's line parallel the plane
        //TC13: Ray is out of the plane(0 points)
        assertNull(plane.findIntersections(new Ray(new Point(0,1,0),
                new Vector(0,-1,0))),"the ray is parallel!");
        //TC14: Ray is contained in the plane(0 points)
        assertNull(plane.findIntersections(new Ray(new Point(1,2,0),
                new Vector(-1,0,1))),"the ray is parallel!");
        // **** Group: Ray's line perpendicular to the plane
        //TC15: Ray starts before the plane (1 point)
        result = plane.findIntersections(new Ray(new Point(3,0,0),
                new Vector(-1,0,-1)));
        assertEquals(1,result.size(),"it must be one intersection!!!");
        assertEquals(List.of(p0),result,"wrong point...");
        //TC16": Ray starts at plane (0 points)
        assertNull(plane.findIntersections(new Ray(p0,
                new Vector(1,0,1))),"the ray starts at the plane!!");
        //TC17: Ray starts after the plane
        assertNull(plane.findIntersections(new Ray(new Point(2,0,0),
                new Vector(1,0,1))),"the ray starts at the plane!!");
        // ****Lonely cases
        //TC18: non-perpendicular ray starts at the plain
        assertNull(plane.findIntersections(new Ray(new Point(1,1,0),
                new Vector(0,0,1))),"the non-perpendicular ray starts at the plane!!");
        // TC19: non-perpendicular ray starts at the plain's point of reference
        assertNull(plane.findIntersections(new Ray(new Point(1,0,0),
                new Vector(0,0,1))),
                "the non-perpendicular ray starts at the plane's point of reference!!");
    }
}





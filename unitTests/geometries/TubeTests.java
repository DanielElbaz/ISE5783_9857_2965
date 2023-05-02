package geometries;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

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
    @Test
    @Disabled
    public void testFindIntersections() {
        Tube tube = new Tube(1,new Ray(new Point(1,0,0),
                new Vector(0,0,1)));
        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray's line is outside the tube (0 points)
        assertNull(tube.findIntersections(new Ray(new Point(3,2,0),
                new Vector(0,1,0))),"ray doesn't the tube direction");
        // TC02: Ray starts before and cross the tube (2 points)
        List<Point> resoult = tube.findIntersections(new Ray(new Point(0,2,0),
                new Vector(1,-1,0)));
        Point p0 = new Point(1,1,0);
        Point p1 = new Point(2,0,0);
        assertEquals(2,resoult.size(),"there are 2 points!!!");
        assertEquals(List.of(p0,p1),resoult,"oooops! it's not the points...");
        // TC03: Ray starts inside the tube (1 point)
        resoult = tube.findIntersections(new Ray(new Point(1.5,0.5,0),
                new Vector(1,-1,0)));
        assertEquals(1,resoult.size(),"there are 1 point!!!");
        assertEquals(List.of(p1),resoult,"oooops! it's not the point...");
        // TC04: Ray starts after the tube
        assertNull(tube.findIntersections(new Ray(new Point(3,-1,0),
                new Vector(1,-1,0))),"hey! ray starts after the tube!");
        // =============== Boundary Values Tests ==================
        // **** Group: Ray's line crosses the tube (but not the axis ray)
        // TC11: Ray starts at tube and goes inside (1 point)
        resoult = tube.findIntersections(new Ray(new Point(1,1,0),
                new Vector(1,-1,0)));
        assertEquals(1,resoult.size(),"there are 1 point!!!");
        assertEquals(List.of(p1),resoult,"oooops! it's not the point...");
        // TC12: Ray starts at tube and goes outside (0 points)
        assertNull(tube.findIntersections(new Ray(new Point(2,0,0),
                new Vector(1,-1,0))),"the ray gos out!...");
        // **** Group: Ray's line goes through the axis ray
        // TC13: Ray starts before the tube (2 points)
        resoult = tube.findIntersections(new Ray(new Point(1,3,0),
                new Vector(0,-1,0)));
        p1 = new Point(1,-1,0);
        assertEquals(2,resoult.size(),"there are 2 points!!!");
        assertEquals(List.of(p0,p1),resoult,"oooops! it's not the points...");
        // TC14: Ray starts at tube and goes inside (1 point)
        resoult = tube.findIntersections(new Ray(new Point(1,1,0),
                new Vector(0,-1,0)));
        assertEquals(1,resoult.size(),"there are 1 point!!!");
        assertEquals(List.of(p1),resoult,"oooops! it's not the point...");
        // TC15: Ray starts inside (1 point)
        resoult = tube.findIntersections(new Ray(new Point(1,0.5,0),
                new Vector(0,-1,0)));
        assertEquals(1,resoult.size(),"there are 1 point!!!");
        assertEquals(List.of(p1),resoult,"oooops! it's not the point...");
        // TC16: Ray starts at the axis ray (1 point)
        resoult = tube.findIntersections(new Ray(new Point(1,0,0),
                new Vector(0,-1,0)));
        assertEquals(1,resoult.size(),"there are 1 point!!!");
        assertEquals(List.of(p1),resoult,"oooops! it's not the point...");
        // TC17: Ray starts at tube and goes outside (0 points)
        assertNull(tube.findIntersections(new Ray(new Point(1,-1,0),
                new Vector(1,-1,0))),"the ray gos out!...");
        // TC18: Ray starts after tube (0 points)
        assertNull(tube.findIntersections(new Ray(new Point(1,-2,0),
                new Vector(1,-1,0))),"the ray starts after the tube!!!");
        // **** Group: Ray's line is tangent to the tube (all tests 0 points)
        // TC19: Ray starts before the tangent point
        assertNull(tube.findIntersections(new Ray(new Point(0,1,1),
                new Vector(1,0,0))),"tangent ray has no intersections...");
        // TC20: Ray starts at the tangent point
        assertNull(tube.findIntersections(new Ray(new Point(1,1,1),
                new Vector(1,0,0))),"tangent ray starts at tube has no intersection!");
        // TC21: Ray starts after the tangent point
        assertNull(tube.findIntersections(new Ray(new Point(3,1,1),
                new Vector(1,0,0))),"tangent ray starts after tube...");
        // **** Group: Special cases
        // TC22: Ray's line is outside, ray is orthogonal to ray start to tube's axis point
        assertNull(tube.findIntersections(new Ray(new Point(1,3,0),
                new Vector(1,0,0))),"ray is orthogonal");
        // TC23: Ray contained in tube boundary
        assertNull(tube.findIntersections(new Ray(new Point(1,1,1),
                new Vector(0,0,1))),"ray is contained!!!!!!!!!");
    }
}
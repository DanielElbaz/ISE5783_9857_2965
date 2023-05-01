package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GeometriesTest {

    @Test
    void testFindIntersections() {
        Geometries geometries = new Geometries();
        // =============== Boundary Values Tests ==================
        // TC11: empty list
        assertNull(geometries.findIntersections(new Ray(new Point(1,1,1),
                new Vector(1,2,3))),"hey! the list is empty!!!");
        //TC12: full list but no intersection with any shape
        geometries.add(new Sphere(1,new Point(1,0,1)),
                new Triangle(new Point(-1,0,2),
                        new Point(-1,-1,0),
                        new Point(-1,1,0)),
                new Polygon(new Point(4,-3,0),
                        new Point(4,3,0),
                        new Point(4,2,2),
                        new Point(4,-2,2)));
        assertNull(geometries.findIntersections(new Ray(new Point(2,0,4),
                new Vector(0,0,1))),"the ray direction is out of all shapes!!!");
        //TC13: full list but intersection with only one shape
        List<Point> result = geometries.findIntersections(new Ray(new Point(3,0,1),
                new Vector(1,0,0)));
        assertEquals(1,result.size(),"it have to be only one point!!!");
        //TC14: full list and intersections with all shapes
        result = geometries.findIntersections(new Ray(new Point(6,0,1),
                new Vector(-1,0,0)));
        assertEquals(4,result.size(),"it have to be four points!!!");
        // ============ Equivalence Partitions Tests ==============
        //TC01: full list and intersections with some shapes but not all of them
        result = geometries.findIntersections(new Ray(new Point(3,0,1),
                new Vector(-1,0,0)));
        assertEquals(3,result.size(),"it have to be 3 points!!!");
    }
}
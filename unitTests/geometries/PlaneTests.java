package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTests {

    @Test
    public void testConstructor() {
        Point p1 = new Point(1, 0, 0);
        Point p2 = new Point(0, 1, 0);
        Point p3 = new Point(0, 0, 1);
        try{
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
                plane.getNormal().scale(-1).equals(new Vector(sqrt3, sqrt3, sqrt3))||plane.getNormal().length()!=1,
                    "Ouch! The normal is wrong! :/ \n");
    }
    }





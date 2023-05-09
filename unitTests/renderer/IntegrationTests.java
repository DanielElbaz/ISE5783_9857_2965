package renderer;

import geometries.Intersectable;
import geometries.Sphere;
import geometries.Triangle;
import geometries.Plane;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntegrationTests {
    @Test
    void SphereTestConstructRay() {
        List<Ray> rays = BuildCameraRay(new Point(0, 0, 0));
        List<Ray> rays2 = BuildCameraRay(new Point(0, 0, 0.5));

        // TC01: Ray's line crosses the sphere (2 points)
        Sphere sphere1 = new Sphere(1, new Point(0, 0, -3));
        assertEquals(2, SumIntersections(sphere1, rays), "Wrong number of intersections");

        // TC02: Ray's line crosses the sphere (18 points)
        Sphere sphere2= new Sphere(2.5, new Point(0, 0, -2.5));
        assertEquals(18, SumIntersections(sphere2, rays2), "Wrong number of intersections");

        // TC03: Ray's line crosses the sphere (10 points)
        Sphere sphere3= new Sphere(2, new Point(0, 0, -2));
        assertEquals(10, SumIntersections(sphere3, rays2), "Wrong number of intersections");

        // TC04: Ray's line crosses the sphere (9 points)
        Sphere sphere4= new Sphere(4, new Point(0, 0, -1));
        assertEquals(9, SumIntersections(sphere4, rays), "Wrong number of intersections");

        // TC05: Ray's line is behind the sphere (1 point)
        Sphere sphere5= new Sphere(0.5, new Point(0, 0, 1));
        assertEquals(0, SumIntersections(sphere5, rays), "Wrong number of intersections");

    }
    @Test
    void PlaneTestConstructRay(){
        List<Ray> rays = BuildCameraRay(new Point(0, 0, 0));

        // TC01: Ray's lines are crossing the plane (9 points)
        Plane plane1 = new Plane(new Point(0, 0, -5), new Vector(0, 0, -1));
        assertEquals(9, SumIntersections(plane1, rays), "Wrong number of intersections");

        // TC02: The plane is on the side crossing the view plane (6 points)
        Plane plane2 = new Plane(new Point(0, 0, -5), new Vector(0, 1, 1));
        assertEquals(6, SumIntersections(plane2, rays), "Wrong number of intersections");

        // TC03: The plane is on the side crossing the view plane (0 points)
        Plane plane3 = new Plane(new Point(0, 0, 5), new Vector(0, 1, 1));
        assertEquals(0, SumIntersections(plane3, rays), "Wrong number of intersections");

    }


    @Test
    void TriangleTestConstructRay(){
        List<Ray>rays=BuildCameraRay(new Point(0,0,0));

        // TC01: Ray's line is inside the triangle (1 point)
        Triangle triangle1=new Triangle(new Point(0,1,-2),new Point(1,-1,-2),new Point(-1,-1,-2));
        assertEquals(1,SumIntersections(triangle1,rays),"Wrong number of intersections");

        // TC02: Ray's line are crossing twice the triangle (2 points)
        Triangle triangle2=new Triangle(new Point(0,20,-2),new Point(1,-1,-2),new Point(-1,-1,-2));
        assertEquals(2,SumIntersections(triangle2,rays),"Wrong number of intersections");

    }
    /**
     * Sum the number of intersections
     * @param geo geometry from Intersectable in order to adapt to all geometries
     * @param rays list of rays
     * @return number of intersections
     */
    private int SumIntersections(Intersectable geo, List<Ray> rays){
        int sum = 0;
        for(Ray ray : rays){
            if(geo.findIntersections(ray) != null)
                sum+=geo.findIntersections(ray).size();
        }
        return sum;
    }

    /**
     * Build Camera and rays
     * @param p0 camera location
     * @return list of rays
     */
    private List<Ray> BuildCameraRay(Point p0) {
        // build camera
        Camera myCamera = new Camera(p0, new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setVPSize(3, 3).setVPDistance(1);

        // build list of rays
        List<Ray> rays = new ArrayList<Ray>();
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                rays.add(myCamera.constructRayThroughPixel(3, 3, i, j));
            }
        return rays;
    }
}

package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * The Sphere class extends RadialGeometry and represents a sphere in a 3D space.
 */
public class Sphere extends RadialGeometry{
    private final Point center;

    public Sphere(double radius, Point center) {
        super(radius);
        this.center = center;
    }


    public Point getCenter() {
        return center;
    }

    public Vector getNormal(Point p) {
        Vector v = p.subtract(center);
        return v.normalize();
    }
}

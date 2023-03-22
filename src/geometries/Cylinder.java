package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * The Cylinder class extends Tube and represents a cylinder in a 3D space.
 */
public class Cylinder extends Tube{
    private final double height;

    public Cylinder(double radius, Ray axisRay, double height) {
        super(radius, axisRay);
        this.height = height;
    }

    public double getHeight() {
        return height;
    }
    public Vector getNormal(Point p) {
        return null;
    }
}

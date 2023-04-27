package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

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
    public Vector getNormal(Point point) {
        Point p0 = axisRay.getP0();
        Vector v = axisRay.getDir();

        if (point.equals(p0))
            return v;

        // projection of P-p0 on the ray:
        Vector u = point.subtract(p0);

        // distance from p0 to the o who is in from of point
        double t = alignZero(u.dotProduct(v));

        // if the point is at a base
        if (t == 0 || isZero(height - t))
            return v;

        //the other point on the axis facing the given point
        Point p = p0.add(v.scale(t));

        return point.subtract(p).normalize();
    }
}

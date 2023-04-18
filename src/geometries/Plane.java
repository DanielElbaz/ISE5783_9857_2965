package geometries;

import primitives.Point;
import primitives.Vector;

import static primitives.Util.isZero;

/**
 * The Plane class represents a plane in a 3D space.
 */
public class Plane implements Geometry {
    private final Point q0;
    private final Vector normal;

    public Point getQ0() {
        return q0;
    }

    public Plane(Point q0, Vector normal) {
        this.q0 = q0;
        this.normal = normal;
    }
    /**
     * Constructor for Plane
     * @param p1;
     * @param p2;
     * @param p3;
     * 3 points that the plane will be constructed from
     */
    public Plane(Point p1, Point p2, Point p3) {
        this.q0 = p1;
        Vector q2=p2.subtract(p1);
        Vector q3=p3.subtract(p1);
        if(isZero(q2.dotProduct(q3)))
            throw new IllegalArgumentException("The points are on the same line");
        this.normal = q2.crossProduct(q3).normalize();
    }

    public Vector getNormal(Point p) {
        return getNormal();
    }

    public Vector getNormal() {
        return normal;
    }
}

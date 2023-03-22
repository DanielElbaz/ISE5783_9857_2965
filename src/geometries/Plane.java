package geometries;

import primitives.Point;
import primitives.Vector;

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
        this.normal = p2.subtract(p1).crossProduct(p3.subtract(p1));
    }

    public Vector getNormal(Point p) {
        return null;
    }

    public Vector getNormal() {
        return normal;
    }
}

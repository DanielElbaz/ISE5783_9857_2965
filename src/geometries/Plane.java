package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
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
       try{
           Vector v = q2.crossProduct(q3);
                  } catch (IllegalArgumentException e){
           throw new IllegalArgumentException("The points are on the same line");
       }
        this.normal = q2.crossProduct(q3).normalize();
    }

    public Vector getNormal(Point p) {
        return getNormal();
    }

    public Vector getNormal() {
        return normal;
    }
    @Override
    /**
     * @param ray
     * @return
     */
    public List<Point> findIntersections(Ray ray) { //TODO: check if the ray is in the plane
        Vector dir = ray.getDir();
        double nv = normal.dotProduct(dir);
        if(isZero(nv)) {
            return null;
        }

        Point p0 = ray.getP0();
        double NQminP0 = normal.dotProduct(q0.subtract(p0));
        double t = alignZero(NQminP0/nv); //TODO: check if t is negative
        if(t > 0){
            Point intersectionPoint = p0.add(dir.scale(t)); //TODO: check if the point is on the plane
            return List.of(intersectionPoint);
        }
        else { // t <= 0
            return null;
        }
    }
}

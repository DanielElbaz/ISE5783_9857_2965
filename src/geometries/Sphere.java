package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

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
    @Override
    public List<Point> findIntersections(Ray ray) {
        Point p0 = ray.getP0();
        Vector dir = ray.getDir();
        if(p0.equals(center)){
            return List.of(p0.add(dir.scale(radius)));
        }
        Vector U = center.subtract(p0);
        double tm = U.dotProduct(dir);
        double d = Math.sqrt(U.lengthSquared() - (tm*tm));
        if(d >= radius){
            return null;
        }
        double th = Math.sqrt(radius*radius - d*d);
        double t1 = tm - th;
        double t2 = tm + th;
        if(t2 <= 0){
            return null;
        } else if(t1 > 0){
            return List.of(p0.add(dir.scale(t1)),p0.add(dir.scale(t2)));
        }
        else {
            return List.of(p0.add(dir.scale(t2)));
        }
    }
}

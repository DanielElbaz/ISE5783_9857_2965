package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

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
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        Point p0 = ray.getP0(); // the origin of the ray
        Vector dir = ray.getDir();
        if(p0.equals(center)){
            return List.of(new GeoPoint(this,p0)); // the ray starts at the center of the sphere
        }
        Vector U = center.subtract(p0); // the vector from the origin of the ray to the center of the sphere
        double tm = U.dotProduct(dir); // the length of the projection of U on the ray
        double d = Math.sqrt(U.lengthSquared() - (tm*tm)); // the distance between the center of the sphere and the ray
        if(alignZero(radius - d) <= 0){ // the ray doesn't intersect the sphere
            return null;
        }
        double th = Math.sqrt(radius*radius - d*d); // the length of the projection of U on the ray
        double t1 = tm - th; // the length of the ray from the origin to the first intersection point
        double t2 = tm + th; // the length of the ray from the origin to the second intersection point
        if(alignZero(t2) <= 0){ // the ray starts after the sphere
            return null;
        } else if(alignZero(t1) > 0){ // the ray starts before the sphere
            return List.of(new GeoPoint(this, ray.getPoint(t1)),new GeoPoint(this, ray.getPoint(t2))); // the ray starts before the sphere and ends after the sphere
        }
        else { // the ray starts inside the sphere
            return List.of(new GeoPoint(this, ray.getPoint(t2)));
        }
    }
}

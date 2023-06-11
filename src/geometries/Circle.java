package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

public class Circle extends Geometry {


    protected final double radius;
    protected final Plane plane;

    public Circle(double radius, Point point, Vector normal) {
        if(alignZero(radius) <= 0){
            throw new IllegalArgumentException();
        }
        this.radius = radius;
        this.plane = new Plane(point,normal);
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public Vector getNormal(Point point) {
        return plane.getNormal();
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> intersections = plane.findGeoIntersectionsHelper(ray,maxDistance);
        if(intersections == null){
            return null;
        }
        Point p0 = intersections.get(0).point;
        try {
            p0.subtract(plane.getQ0());
        }catch (IllegalArgumentException e){
            return List.of(new GeoPoint(this,p0));
        }

        if(alignZero(p0.subtract(plane.getQ0()).length() - radius) >= 0){
            return null;
        }
        return List.of(new GeoPoint(this,p0));
    }
}

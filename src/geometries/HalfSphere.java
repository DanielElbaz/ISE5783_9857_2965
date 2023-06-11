package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

public class HalfSphere extends Sphere{

    protected Vector direct;

    public HalfSphere(double radius, Point center, Vector dir) {
        super(radius, center);
        this.direct = dir.normalize();
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> intersections = super.findGeoIntersectionsHelper(ray,maxDistance);
        if(intersections == null){
            return null;
        }
        Vector dir = this.direct.scale(-1);

        if(intersections.size() == 2){
            Point p1 = intersections.get(0).point;
            Point p2 = intersections.get(1).point;

            double pose1 = alignZero(p1.subtract(center).dotProduct(dir));
            double pose2 = alignZero(p2.subtract(center).dotProduct(dir));

            if(pose1 > 0 && pose2 > 0){
                return List.of(new GeoPoint(this, p1), new GeoPoint(this,p2));
            }
            if(pose1 >= 0 && pose2 <= 0){
                return List.of(new GeoPoint(this, p1));
            }
            if(pose1 <= 0 && pose2 > 0){
                return List.of(new GeoPoint(this,p2));
            }
            return null;
        }
        Point p1 = intersections.get(0).point;
        double pose1 = alignZero(p1.subtract(center).dotProduct(dir));

        return pose1 > 0 ? List.of(new GeoPoint(this,p1)): null;
    }
}

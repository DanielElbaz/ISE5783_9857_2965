package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

public class Ring extends Circle{
    private double innerRadius;

    public Ring(double radius, Point point, Vector normal, double innerRadius) {
        super(radius, point, normal);
        this.innerRadius = innerRadius;
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> intersections = plane.findGeoIntersectionsHelper(ray,maxDistance);
        if(intersections == null){
            return null;
        }
        Point p0 = intersections.get(0).point;
        double leng;
        try {
           leng =  p0.subtract(plane.getQ0()).length();
        }catch (IllegalArgumentException e){
            return List.of(new GeoPoint(this,p0));
        }

        if(alignZero( leng - radius) >= 0
                || alignZero(leng - innerRadius ) <= 0){
            return null;
        }
        return List.of(new GeoPoint(this,p0));
    }

}

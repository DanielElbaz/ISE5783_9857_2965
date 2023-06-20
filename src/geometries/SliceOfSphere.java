package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class SliceOfSphere extends HalfSphere{
    private double minRad;
    private double maxRad;

    public SliceOfSphere(double radius, Point center, Vector dir, double minRad, double maxRad) {
        super(radius, center, dir);
        if(minRad >= maxRad){
            throw new IllegalArgumentException("min can't be bigger then max!!!");
        }
        this.minRad = minRad;
        this.maxRad = maxRad;
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        if (isZero(minRad) && maxRad == radius){
            return super.findGeoIntersectionsHelper(ray, maxDistance);
        }

        List<GeoPoint> intersections = super.findGeoIntersectionsHelper(ray,maxDistance);

        if(intersections == null){
            return null;
        }

        if(intersections.size() == 2){
            Point p1 = intersections.get(0).point;
            Point p2 = intersections.get(1).point;
            Vector dir = this.direct.scale(-1);

            Vector v1 = p1.subtract(center);

            Point pX = center.add(dir.scale(v1.dotProduct(dir)));
            double rad1 = alignZero(dir.scale(v1.dotProduct(dir)).length() - radius) == 0? 0
                    : p1.subtract(pX).length();
            Vector v2 = p2.subtract(center);

            pX = center.add(dir.scale(v2.dotProduct(dir)));
            double rad2 = alignZero(dir.scale(v2.dotProduct(dir)).length() - radius) == 0? 0
                    : p2.subtract(pX).length();

            boolean p1In = (alignZero(rad1 - minRad) >= 0 && alignZero(rad1 - maxRad) <= 0);
            boolean p2In = (alignZero(rad2 - minRad) >= 0 && alignZero(rad2 - maxRad) <= 0);

            if (!(p1In || p2In)){
                return null;
            }
            if(p1In && !p2In){
                return List.of(new GeoPoint(this, p1));
            }
            if(!p1In && p2In){
                return List.of(new GeoPoint(this, p2));
            }

            return List.of(new GeoPoint(this, p1),new GeoPoint(this, p2));
        }

        Point p1 = intersections.get(0).point;
        Vector dir = this.direct.scale(-1);

        Vector v1 = p1.subtract(center);

        Point pX = center.add(dir.scale(v1.dotProduct(dir)));
        double rad1 = alignZero(dir.scale(v1.dotProduct(dir)).length() - radius) == 0? 0
                : p1.subtract(pX).length();

        boolean p1In = (alignZero(rad1 - minRad) >= 0 && alignZero(rad1 - maxRad) <= 0);

        return p1In? List.of(new GeoPoint(this, p1)) : null;
    }
}

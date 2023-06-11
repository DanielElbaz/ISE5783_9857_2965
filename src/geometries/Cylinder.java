package geometries;

import primitives.*;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class Cylinder extends Tube {   // implements Geometry
    final double height;
    final Circle base1;
    final Circle base2;

    public Cylinder(double radius, Ray axisRay, double height) {
        super(axisRay, radius);
        this.height = height;
        Vector v = axisRay.getDir();
        base1 = new Circle(radius,axisRay.getP0(), v);
        base2 = new Circle(radius,axisRay.getPoint(this.height), v);
    }

    @Override
    public Vector getNormal(Point p) {
        Point o = axisRay.getP0();
        Vector v = axisRay.getDir();

        // projection of P-O on the ray:
        double t;
        try {
            t = alignZero(p.subtract(o).dotProduct(v));
        } catch (IllegalArgumentException e) { // P = O
            return v;
        }

        // if the point is at a base
        if (isZero(t) || isZero(height - t)) { // if it's close to 0, we'll get ZERO vector exception
            return v;
        }

        try {
            o = o.add(v.scale(t));
        } catch (IllegalArgumentException e) {
            return v;
        }
        return p.subtract(o).normalize();
    }

    /**
     * implemented by Dan zilberstein
     * @return
     */
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
////        //to do rethink the all thing
////        List<Point3D> result = super.findIntersections(ray);
////        if(result != null){
////            Point3D p = result.get(0);
////            Vector v = p.subtract(axisRay.getP0());
////           //to do
////        }
////        //to do do the caps
////        return result;
//
//        Vector vAxis = axisRay.getDir();
//        Vector v = ray.getDir();
//
//        Point p0 = ray.getP0();
//        Point pC = axisRay.getP0();
//        Point p1;
//        Point p2;
//
//        // intersections of the ray with infinite cylinder {without the bases)
//        List<GeoPoint> intersections = super.findGeoIntersectionsHelper(ray, maxDistance);
//
//        double vAxisV = alignZero(vAxis.dotProduct(v)); // cos(angle between ray directions)
//
//        if (intersections == null) { // no intersections with the infinite cylinder
//            try {
//                vAxis.crossProduct(v); // if ray and axis are parallel - throw exception
//                return null; // they are not parallel - the ray is outside the cylinder
//            } catch (Exception e) {
//            }
//
//            // The rays are parallel
//            Vector vP0PC;
//            try {
//                vP0PC = pC.subtract(p0); // vector from P0 to Pc (O1)
//            } catch (Exception e) { // the rays start at the same point
//                // check whether the ray goes into the cylinder
//                return vAxisV > 0 ? //
//                        List.of(new GeoPoint(this,ray.getPoint(height))) : null;
//            }
//
//            double t1 = alignZero(vP0PC.dotProduct(v)); // project Pc (O1) on the ray
//            p1 = ray.getPoint(t1); // intersection point with base1
//
//            // Check the distance between the rays
//            if (alignZero(p1.distance(pC) - radius) >= 0)
//                return null;
//
//            // intersection points with base2
//            double t2 = alignZero(vAxisV > 0 ? t1 + height : t1 - height);
//            p2 = ray.getPoint(t2);
//            // the ray goes through the bases - test bases vs. ray head and return points
//            // accordingly
//            if (t1 > 0 && t2 > 0)
//                return List.of(new GeoPoint(this,p1), new GeoPoint(this,p2));
//            if (t1 > 0)
//                return List.of(new GeoPoint(this,p1));
//            if (t2 > 0)
//                return List.of(new GeoPoint(this,p2));
//            return null;
//        }
//
//        // Ray - infinite cylinder intersection points
//        p1 = intersections.get(0).point;
//        p2 = intersections.get(1).point;
//
//        // get projection of the points on the axis vs. base1 and update the
//        // points if both under base1 or they are from different sides of base1
//        double p1OnAxis = alignZero(p1.subtract(pC).dotProduct(vAxis));
//        double p2OnAxis = alignZero(p2.subtract(pC).dotProduct(vAxis));
//        if (p1OnAxis < 0 && p2OnAxis < 0)
//            p1 = null;
//        else if (p1OnAxis < 0)
//            p1 = base1.findGeoIntersectionsHelper(ray, maxDistance).get(0).point;
//        else
//            /* p2OnAxis < 0 */ p2 = base1.findGeoIntersectionsHelper(ray, maxDistance).get(0).point;
//
//        // get projection of the points on the axis vs. base2 and update the
//        // points if both above base2 or they are from different sides of base2
//        double p1OnAxisMinusH = alignZero(p1OnAxis - height);
//        double p2OnAxisMinusH = alignZero(p2OnAxis - height);
//        if (p1OnAxisMinusH > 0 && p2OnAxisMinusH > 0)
//            p1 = null;
//        else if (p1OnAxisMinusH > 0)
//            p1 = base2.findGeoIntersectionsHelper(ray, maxDistance).get(0).point;
//        else
//            /* p2OnAxisMinusH > 0 */ p2 = base2.findGeoIntersectionsHelper(ray, maxDistance).get(0).point;
//
//        // Check the points and return list of points accordingly
//        if (p1 != null && p2 != null)
//            return List.of(new GeoPoint(this,p1), new GeoPoint(this,p2));
//        if (p1 != null)
//            return List.of(new GeoPoint(this,p1));
//        if (p2 != null)
//            return List.of(new GeoPoint(this,p2));
//        return null;

        List<GeoPoint> tubeIntersections = super.findGeoIntersectionsHelper(ray,maxDistance);
        List<GeoPoint> baseIntersections = base1.findGeoIntersectionsHelper(ray,maxDistance);
        List<GeoPoint> topIntersections = base2.findGeoIntersectionsHelper(ray,maxDistance);

        if(topIntersections == null && baseIntersections == null && tubeIntersections == null){
            return null;
        }

        Point p0 = this.axisRay.getP0();
        Vector dir = this.axisRay.getDir();

        if(tubeIntersections != null) {
            if (tubeIntersections.size() == 2) {
                Point p1 = tubeIntersections.get(0).point;
                Point p2 = tubeIntersections.get(1).point;

                double posP1 = alignZero(p1.subtract(p0).dotProduct(dir));
                double posP2 = alignZero(p2.subtract(p0).dotProduct(dir));
                posP1 = posP1 == 0 ? height + 1 : posP1; // to make sure we are not multiple vector with 0
                posP2 = posP2 == 0 ? height + 1 : posP2; // to make sure we are not multiple vector with 0

                if (posP1 <= 0 && posP2 <= 0) { // intersections are under the cylinder
                    return null;
                }
                if (posP1 > 0 && posP2 > 0) {

                    if (dir.scale(posP1).length() >= height && dir.scale(posP2).length() >= height) { // intersections are above the cylinder
                        return null;
                    }
                    if (dir.scale(posP1).length() < height && dir.scale(posP2).length() < height) { // intersections are on the cylinder
                        return List.of(new GeoPoint(this, p1), new GeoPoint(this, p2));
                    }
                    if (dir.scale(posP1).length() < height && dir.scale(posP2).length() >= height) {
                        if (topIntersections == null) {
                            return List.of(new GeoPoint(this, p1));
                        }
                        return List.of(new GeoPoint(this, p1), new GeoPoint(this, topIntersections.get(0).point));
                    }
                    if (dir.scale(posP1).length() >= height && dir.scale(posP2).length() < height) {
                        if (topIntersections == null) {
                            return List.of(new GeoPoint(this, p2));
                        }
                        return List.of(new GeoPoint(this, p2), new GeoPoint(this, topIntersections.get(0).point));
                    }
                }
                if (posP1 <= 0 && posP2 > 0) {
                    double p2height = dir.dotProduct(p2.subtract(p0));
                    if (p2height < height) {
                        if (baseIntersections == null) {
                            return List.of(new GeoPoint(this, p2));
                        }
                        return List.of(new GeoPoint(this, p2), new GeoPoint(this, baseIntersections.get(0).point));
                    }
                    if (p2height >= height) {
                        if (baseIntersections == null && topIntersections == null) {
                            return null;
                        }
                        if (baseIntersections != null && topIntersections == null) {
                            return List.of(new GeoPoint(this, baseIntersections.get(0).point));
                        }
                        if (baseIntersections == null && topIntersections != null) {
                            return List.of(new GeoPoint(this, topIntersections.get(0).point));
                        }
                        if (baseIntersections != null && topIntersections != null) {
                            return List.of(new GeoPoint(this, baseIntersections.get(0).point), new GeoPoint(this, topIntersections.get(0).point));
                        }
                    }
                }
                if (posP1 > 0 && posP2 <= 0) {
                    double p1height = dir.dotProduct(p1.subtract(p0));
                    if (p1height < height) {
                        if (baseIntersections == null) {
                            return List.of(new GeoPoint(this, p1));
                        }
                        return List.of(new GeoPoint(this, p1), new GeoPoint(this, baseIntersections.get(0).point));
                    }
                    if (p1height >= height) {
                        if (baseIntersections == null && topIntersections == null) {
                            return null;
                        }
                        if (baseIntersections != null && topIntersections == null) {
                            return List.of(new GeoPoint(this, baseIntersections.get(0).point));
                        }
                        if (baseIntersections == null && topIntersections != null) {
                            return List.of(new GeoPoint(this, topIntersections.get(0).point));
                        }
                        if (baseIntersections != null && topIntersections != null) {
                            return List.of(new GeoPoint(this, baseIntersections.get(0).point), new GeoPoint(this, topIntersections.get(0).point));
                        }
                    }
                }
            } else if (tubeIntersections.size() == 1) {

                Point p1 = tubeIntersections.get(0).point;
                double posP1 = alignZero(p1.subtract(p0).dotProduct(dir));
                if (posP1 <= 0 || dir.scale(posP1).length() >= height) {
                    if (baseIntersections == null && topIntersections == null) {
                        return null;
                    }
                    if (baseIntersections != null && topIntersections == null) {
                        return List.of(new GeoPoint(this, baseIntersections.get(0).point));
                    }
                    if (baseIntersections == null && topIntersections != null) {
                        return List.of(new GeoPoint(this, topIntersections.get(0).point));
                    }
                    if (baseIntersections != null && topIntersections != null) {
                        return List.of(new GeoPoint(this, baseIntersections.get(0).point), new GeoPoint(this, topIntersections.get(0).point));
                    }
                }
                if (baseIntersections == null && topIntersections == null) {
                    return List.of(new GeoPoint(this, p1));
                }
                if (baseIntersections != null && topIntersections == null) {
                    return List.of(new GeoPoint(this, p1), new GeoPoint(this, baseIntersections.get(0).point));
                }
                if (baseIntersections == null && topIntersections != null) {
                    return List.of(new GeoPoint(this, p1), new GeoPoint(this, topIntersections.get(0).point));
                }
            }
        }
        else {
            if(baseIntersections != null && topIntersections == null){
                return List.of(new GeoPoint(this, baseIntersections.get(0).point));
            }
            if(baseIntersections == null && topIntersections != null){
                return List.of(new GeoPoint(this, topIntersections.get(0).point));
            }
        }
        return List.of(new GeoPoint(this, baseIntersections.get(0).point),new GeoPoint(this, topIntersections.get(0).point));
    }
}
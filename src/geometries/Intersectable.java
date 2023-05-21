package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

public abstract class Intersectable { // interface for all the geometries that can be intersected by a ray
    /**
     * Returns a list of intersection points of the ray with the geometry.
     *
     * @param ray the ray with which the intersection is checked.
     * @return a list of intersection points of the ray with the geometry.
     */


    public List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
    }

    public static class GeoPoint {
        /**
         * The geometry of the point.
         */
        public Geometry geometry;
        /**
         * The point.
         */
        public Point point;

        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof GeoPoint)) {
                return false;
            }
            GeoPoint geoPoint = (GeoPoint) obj;
            return this == geoPoint & this.point.equals(geoPoint.point);
        }

        @Override
        public String toString() {
            return "geometry:" + geometry.toString()
                    + "point:" + point.toString();
        }
    }


    public List<GeoPoint> findGeoIntersections(Ray ray){
        return findGeoIntersectionsHelper(ray);
    }

    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);
}


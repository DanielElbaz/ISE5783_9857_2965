package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

public abstract class Intersectable { // interface for all the geometries that can be intersected by a ray
    /**
     * Returns a list of intersection points of the ray with the geometry.
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
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "Intersectable{}";
    }

    public abstract List<GeoPoint>findGeoIntersections(Ray ray);
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);
}


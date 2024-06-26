package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

public class Geometries extends Intersectable{
    private List<Intersectable> geometries;

    public Geometries() {
        this.geometries = new LinkedList<>();
    }

    public Geometries(Intersectable... geometries) {
        this.geometries = new LinkedList<>(List.of(geometries));
    }

    public void add(Intersectable... geometries){
        this.geometries.addAll(List.of(geometries));
    }
    /**
     * Returns a list of intersection points of the ray with the geometry.
     * @param ray the ray with which the intersection is checked.
     * @return a list of intersection points of the ray with the geometry.
     */
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray,double maxDistance) {
        if(geometries.isEmpty()){
            return null;
        }
        LinkedList<GeoPoint> results = null;
        for (Intersectable shape: // for each shape in the list of shapes
             geometries) {
            if(shape.findGeoIntersectionsHelper(ray,maxDistance) != null){ // if the ray intersects the shape
                if(results == null){ // if this is the first shape that the ray intersects
                    results = new LinkedList<>(); // create a new list of intersection points
                }
                results.addAll(shape.findGeoIntersections(ray));
            }
        }
        if(results == null || results.isEmpty()){ // if the ray doesn't intersect any shape
            return null;
        }
        return results;
    }
}

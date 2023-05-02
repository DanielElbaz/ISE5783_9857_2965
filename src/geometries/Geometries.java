package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

public class Geometries implements Intersectable{
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
    public List<Point> findIntersections(Ray ray) {
        if(geometries.isEmpty()){
            return null;
        }
        LinkedList<Point> results = null;
        for (Intersectable shape: // for each shape in the list of shapes
             geometries) {
            if(shape.findIntersections(ray) != null){ // if the ray intersects the shape
                if(results == null){ // if this is the first shape that the ray intersects
                    results = new LinkedList<>(); // create a new list of intersection points
                }
                results.addAll(shape.findIntersections(ray));
            }
        }
        if(results == null || results.isEmpty()){ // if the ray doesn't intersect any shape
            return null;
        }
        return results;
    }
}

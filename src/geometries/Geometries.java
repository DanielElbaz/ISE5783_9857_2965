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

    public List<Point> findIntersections(Ray ray) {
        if(geometries.isEmpty()){
            return null;
        }
        LinkedList<Point> results = null;
        for (Intersectable shape:
             geometries) {
            if(shape.findIntersections(ray) != null){
                if(results == null){
                    results = new LinkedList<>();
                }
                results.addAll(shape.findIntersections(ray));
            }
        }
        if(results == null || results.isEmpty()){
            return null;
        }
        return results;
    }
}

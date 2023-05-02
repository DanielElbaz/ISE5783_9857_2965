package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

public interface Intersectable { // interface for all the geometries that can be intersected by a ray
    List<Point> findIntersections(Ray ray);

}

package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import scene.Scene;

import java.util.List;
/**
 * class Camera is a class that represents the camera in the scene
 * it has a place, a vector up, a vector to, a vector right, a height, a width and a distance
 */
public class RayTracerBasic extends RayTracerBase{
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     * @param ray the ray from the camera to the scene
     * @return the color of the closest point to the camera
     */
    @Override
    public Color traceRay(Ray ray) {
        List<Point> intersections = scene.geometries.findIntersections(ray);
        if(intersections == null){
            return scene.background;
        }
        else {
            Point closestPoint = ray.findClosestPoint(intersections);
            return calcColor(closestPoint);
        }
    }
    /**
     * @param p
     * @return the color of the point
     */
    private Color calcColor(Point p){
        return scene.ambientLight.getIntensity();
    }
}

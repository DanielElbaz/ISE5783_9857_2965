package renderer;

import lighting.LightSource;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

import java.util.List;

import static primitives.Util.alignZero;

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
        List<GeoPoint> intersections = scene.geometries.findGeoIntersectionsHelper(ray);
        if(intersections == null){
            return scene.background;
        }
        else {
            GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
            return calcColor(closestPoint, ray);
        }
    }
    /**
     * @param p
     * @return the color of the point
     */
    private Color calcColor(GeoPoint p, Ray ray){
        return scene.ambientLight.getIntensity().add(calcLocalEffects(p, ray));
    }

    private Color calcLocalEffects(GeoPoint p, Ray ray) {
        Color color = p.geometry.getEmission();
        Vector v = ray.getDir ();
        Vector n = p.geometry.getNormal(p.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) return color;
        Material material = p.geometry.getMaterial();

        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(p.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                Color iL = lightSource.getIntensity(p.point);
                color = color.add(iL.scale(calcDiffusive(material, nl)),
                        iL.scale(calcSpecular(material, n, l, nl, v)));
            }
        }
        return color;
    }

    private Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v) {
        Vector r = l.subtract(n.scale(2*nl));
        double maxInPow = Math.pow(Math.max(0, alignZero(v.scale(-1).dotProduct(r))), material.nShininess);
        return material.kS.scale(maxInPow);
    }

    private Double3 calcDiffusive(Material mat, double nl){
        return mat.kD.scale(Math.abs(nl));
    }


}

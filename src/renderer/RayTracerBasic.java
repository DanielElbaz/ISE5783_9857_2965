package renderer;

import geometries.Intersectable;
import lighting.LightSource;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * class Camera is a class that represents the camera in the scene
 * it has a place, a vector up, a vector to, a vector right, a height, a width and a distance
 */
public class RayTracerBasic extends RayTracerBase{
    public RayTracerBasic(Scene scene) {
        super(scene);
    }
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;

    private GeoPoint findClosestIntersection(Ray ray){
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        return intersections == null? null: ray.findClosestGeoPoint(intersections);
    }
    /**
     * @param ray the ray from the camera to the scene
     * @return the color of the closest point to the camera
     */
    @Override
    public Color traceRay(Ray ray) {
        GeoPoint closestPoint = findClosestIntersection(ray); // the closest point to the camera
        return closestPoint == null? scene.background : calcColor(closestPoint, ray); // if there is no intersection
                                                                                    // return the background color
    }


    /**
     * @param p the point
     * @return the color of the point
     */
    private Color calcColor(GeoPoint p, Ray ray, int level, Double3 k){
        Color color = calcLocalEffects(p, ray, k);
        return 1 == level ? color : color.add(calcGlobalEffects(p, ray, level, k));
    }
    private Color calcColor(GeoPoint gp, Ray ray){
        return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, new Double3(1.0))
                .add(scene.ambientLight.getIntensity());
    }

    /**
     * @param p the point
     * @param ray the ray from the camera to the scene
     * @return the color of the point
     */
    private Color calcLocalEffects(GeoPoint p, Ray ray, Double3 k) {
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
                Double3 ktr = transparency(p, lightSource, l, n, nl);
                if (!ktr.product(k).lowerThan( MIN_CALC_COLOR_K )){
                    Color iL = lightSource.getIntensity(p.point).scale(ktr);
                    color = color.add(iL.scale(calcDiffusive(material, nl)),
                            iL.scale(calcSpecular(material, n, l, nl, v)));
                }
            }
        }
        return color;
    }

    /**
     * @param material the material of the geometry
     * @param n the normal of the geometry
     * @param l the vector from the light to the geometry
     * @param nl the dot product of n and l
     * @param v the vector from the camera to the geometry
     * @return the specular color
     */
    private Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v) {
        Vector r = l.subtract(n.scale(2*nl));
        double maxInPow = Math.pow(Math.max(0, alignZero(v.scale(-1).dotProduct(r))), material.nShininess);
        return material.kS.scale(maxInPow);
    }

    /**
     * @param mat the material of the geometry
     * @param nl the dot product of n and l
     * @return the diffusive color
     */
    private Double3 calcDiffusive(Material mat, double nl){
        return mat.kD.scale(Math.abs(nl));
    }

    private Color calcGlobalEffects(GeoPoint gp, Ray ray,
                                    int level, Double3 k) {
        Color color = Color.BLACK;
        Vector v = ray.getDir();
        Vector n = gp.geometry.getNormal(gp.point);
        Material material = gp.geometry.getMaterial();
        return calcColorGLobalEffect(constructReflectedRay( n,gp.point, v),level, k, material.kR)
                .add(calcColorGLobalEffect(constructRefractedRay( n, gp.point, v),level, k, material.kT));}

    private Color calcColorGLobalEffect(Ray ray, int level, Double3 k, Double3 kx) {
        Double3 kkx = k.product(kx);
        if (kkx.lowerThan(MIN_CALC_COLOR_K)) return Color.BLACK;
        GeoPoint gp = findClosestIntersection(ray);
        if (gp == null) return scene.background.scale(kx);
        return isZero(gp.geometry.getNormal(gp.point).dotProduct(ray.getDir()))? Color.BLACK
                : calcColor(gp, ray, level - 1, kkx).scale(kx);
    }

    /**
     * Checks if a given point is unshaded by finding intersections between the point and the light source.
     *
     * @param gp       The geometric point to check for shading
     * @param l        The direction from the point towards the light source
     * @param n        The normal vector at the point
     * @param light    The light source
     * @param nl       The dot product between the normal vector and the light direction
     * @return         {@code true} if the point is unshaded, {@code false} otherwise
     */
    private boolean unshaded(GeoPoint gp, Vector l, Vector n,LightSource light,double nl) {
        Ray lightRay = nl<0? new Ray(n,gp.point,l.scale(-1)): new Ray(n.scale(-1),gp.point,l.scale(-1));
        double maxDistance = light.getDistanceSquared(gp.point);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay,maxDistance);
        if(intersections == null){
            return true;
        }
        for (GeoPoint inter:
             intersections) {
            if(inter.geometry.getMaterial().kT == Double3.ZERO){
                return false;
            }
        }
        return true;
    }

    private Double3 transparency(GeoPoint gp, LightSource light, Vector l, Vector n, double nl) {
        Ray lightRay = nl<0? new Ray(n,gp.point,l.scale(-1)): new Ray(n.scale(-1),gp.point,l.scale(-1));
        double maxDistance = light.getDistanceSquared(gp.point);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay,maxDistance);
        if(intersections == null){
            return Double3.ONE;
        }
        Double3 ktr = Double3.ONE;
        for (GeoPoint inter:
                intersections) {
            ktr = ktr.product(inter.geometry.getMaterial().kT);
        }
        return ktr;
    }

    private Ray constructReflectedRay(Vector n, Point p, Vector dirSource){
        double nDir = dirSource.dotProduct(n);
        if (nDir > 0) {
            n = n.scale(-1);
        }
        return new Ray(n, p, dirSource.add(n.scale(-2*dirSource.dotProduct(n))));
    }

    private Ray constructRefractedRay(Vector n, Point p, Vector dir){
        double nDir = dir.dotProduct(n);
        if (nDir < 0) {
            n = n.scale(-1);
        }
        return new Ray(n, p, dir);
    }




}

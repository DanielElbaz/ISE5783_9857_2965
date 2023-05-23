package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * class RayTracerBase is an abstract class that represents the ray tracer base
 * it has a scene
 */
public abstract class RayTracerBase {
    protected Scene scene;

    /**
     * constructor for RayTracerBase
     * @param scene the scene
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    public abstract Color traceRay(Ray ray);
}

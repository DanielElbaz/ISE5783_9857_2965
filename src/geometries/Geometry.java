package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;

/**
 * The Geometry interface represents a geometry in a 3D space.
 */
public abstract class Geometry extends Intersectable{
   protected Color emission = Color.BLACK;
   private Material material = new Material();

    public Material getMaterial() {
        return material;
    }

    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    /**
    * Returns the emission color of the geometry.
    * @return the emission color of the geometry.
    */
    public Color getEmission() {
        return emission;
    }

    /**
     * Sets the emission color of the geometry.
     * @param emission the emission color of the geometry.
     */
   public Geometry setEmission(Color emission) {

       this.emission = emission;
       return this;
    }

    /**
     * Returns the normal to the geometry at the specified point.
     * @param p the point at which the normal is required.
     * @return the normal to the geometry at the specified point.
     */
    public abstract Vector getNormal(Point p);

}

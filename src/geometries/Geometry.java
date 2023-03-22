package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * The Geometry interface represents a geometry in a 3D space.
 */
public interface Geometry {
    /**
     * Returns the normal to the geometry at the specified point.
     * @param p the point at which the normal is required.
     * @return the normal to the geometry at the specified point.
     */
    public Vector getNormal(Point p);
}

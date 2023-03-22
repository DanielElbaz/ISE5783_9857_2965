package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**

 The Tube class extends RadialGeometry and represents a tube in a 3D space.
 */
public class Tube extends RadialGeometry {

    /**

     The axis ray of the tube.
     */
    protected final Ray axisRay;
    /**

     Constructs a new Tube object with the specified axis ray.
     @param axisRay the axis ray of the tube.
     */
    public Tube(double radius, Ray axisRay) {
        super(radius);
        this.axisRay = axisRay;
    }

    /**

     Returns the axis ray of the tube.
     @return the axis ray of the tube.
     */
    public Ray getAxisRay() {
        return axisRay;
    }
    /**

     Returns the normal to the tube at the specified point.
     @param p the point to calculate the normal at.
     @return the normal vector to the tube at the specified point.
     */
    public Vector getNormal(Point p) {
        return null;
    }
}


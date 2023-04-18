package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

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
        // Finding the normal:
        // n = normalize(p - o)
        // t = v * (p - p0)
        // o = p0 + t * v

        Vector v= axisRay.getDir();
        Point p0 =axisRay.getP0();

        Vector vec = p.subtract(p0);
        double t = v.dotProduct(vec);
        t = alignZero(t);

        //if t=0, then t*v is the zero vector and o=p0.
        Point o=p0;

        if(!isZero(t))
        {
            o = p0.add(v.scale(t));
        }
        else
            return vec.normalize();
        if (p.equals(o))
            throw new IllegalArgumentException("point cannot be on the tube axis");
        Vector res = p.subtract(o).normalize();
        return res;


    }
}


package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

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

    /**
     * @param ray
     * @return
     */
    @Override
    public List<Point> findIntersections(Ray ray) {
        /** explanation:
         *  a general tube equation is: (q-p0-(v,q-p0)v)^2-r^2=0
         *  while q is general point on the tube, p0 the central point and v the direction vector
         *  in our case - q belongs the ray, so we can substitute it with q0+tu
         *  while q0 is the point the ray starts from, and u the ray direction
         *  our equation now is((u-(u,v)v)t+(q0-p0-(q0-p0,v)v))^2-r^2
         *                     ((   a    )t+(       b        ))^2
         *  all we need is to find the 't'
         *  after arranging the equation we'll get quadratic equation At^2+Bt+c=0
         *  while: A = (u-(u,v)v)^2
         *         B = 2(u-(u,v)v,q0-p0-(q0-p0,v)v)
         *         C = (q0-p0-(q0-p0,v)v)^2-r^2
         */
        Point q0 = ray.getP0();
        Vector u = ray.getDir();
        Point p0 = axisRay.getP0();
        Vector v = axisRay.getDir();
        Vector a = u.add(v.scale(((u.dotProduct(v))*-1)));
        Vector b = (q0.subtract(p0)).subtract(v.scale((q0.subtract(p0)).dotProduct(v)));
        double A = a.dotProduct(a);
        double B = 2*a.dotProduct(b);
        double C = b.dotProduct(b) - (radius*radius);
        double discriminant = (B*B)-4*(A*C);
        if(discriminant <= 0){
            return null;
        }
        double SQRTdiscriminant = Math.sqrt(discriminant);
        double t1 = ((-1*B)-SQRTdiscriminant)/(2*A);
        double t2 = ((-1*B)+SQRTdiscriminant)/(2*A);
        if(t2<=0){
            return null;
        }
        if(t1<=0){
            return List.of(p0.add(v.scale(t2)));
        }
        return List.of(p0.add(v.scale(t1)),p0.add(v.scale(t2)));
    }
}


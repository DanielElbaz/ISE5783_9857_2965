package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

/**

 The Triangle class extends the Polygon class and represents a triangle in a 3D space.
 */
public class Triangle extends Polygon {

    /**

     Constructs a new Triangle object with the specified three vertices.
     @param p1 the first vertex of the triangle.
     @param p2 the second vertex of the triangle.
     @param p3 the third vertex of the triangle.
     */
    public Triangle(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);
    }
    @Override
    /**
     Returns the normal to the triangle at the specified point.
     @param p the point to calculate the normal at.
     @return the normal vector to the triangle at the specified point.
     */
    public List<Point> findIntersections(Ray ray) {
        List<Point> TIP = plane.findIntersections(ray); //TIP == Triangle Intersection Points
        if(TIP == null){
            return null;
        }
        Point returnedP = TIP.get(0);
        Point p1 = vertices.get(0);
        Point p2 = vertices.get(1); //p1, p2, p3 are the vertices of the triangle
        Point p3 = vertices.get(2);
        if(returnedP.equals(p1) || returnedP.equals(p2) || returnedP.equals(p3)){ //if the ray intersects the
                                                                            // triangle at one of its vertices,
            return null;
        }
        Vector dir = ray.getDir();
        Point p0 = ray.getP0();
        Vector v1 = p1.subtract(p0);//v1 = p1 - p0
        Vector v2 = p2.subtract(p0);
        Vector v3 = p3.subtract(p0);
        Vector n1 = (v1.crossProduct(v2)).normalize(); //n1 = v1 x v2
        Vector n2 = (v2.crossProduct(v3)).normalize();
        Vector n3 = (v3.crossProduct(v1)).normalize();
        double check1 = dir.dotProduct(n1); //check1 = v * n1
        double check2 = dir.dotProduct(n2);
        double check3 = dir.dotProduct(n3);
        if(alignZero(check1*check2) <= 0 || alignZero(check1*check3) <= 0){ //if check1 and check2 have different signs,
                                                        // then the ray intersects the triangle
            return null;
        }
        return TIP;

    }
}







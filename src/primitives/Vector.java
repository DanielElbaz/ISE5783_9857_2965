package primitives;

import static java.lang.Math.abs;

public class Vector extends Point {

    /**
     * Constructs a Vector object with the specified x, y and z coordinates.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @param z the z coordinate
     */
    public Vector(double x, double y, double z) {
        super(x, y, z);
        if (xyz.equals(Double3.ZERO))throw new IllegalArgumentException
                                    ("Are you crazy? Vector can't be (0,0,0)!");
    }

    /**
     * Constructs a Point object from a Double3 coordinates.
     *
     * @param double3 the Double3 object representing the point
     */
    Vector(Double3 double3) {

        this(double3.d1, double3.d2, double3.d3 );
    }

    /**
     * @return length of the vector
     * by the formula sqrt(x^2+y^2+z^2)
     */
    public double length(){
        return Math.sqrt(lengthSquared());
    }

    /**
     * @return length squared of the vector
     *
     */
    public double lengthSquared() {
        double x = xyz.d1;
        double y = xyz.d2;
        double z = xyz.d3;
        return x*x+y*y+z*z;
    }

    /**
     * @return normalize vector by the formula
     * (x/length,y/length,z/length)
     */
    public Vector normalize() {
        double len=length();
        return new Vector(xyz.d1/len,xyz.d2/len,xyz.d3/len);
    }

    /**
     *
     * @param otherVector;
     * @return new vector that is the cross product of this vector and the other vector;
     */
    public Vector crossProduct(Vector otherVector) {
        double x=this.xyz.d2*otherVector.xyz.d3-this.xyz.d3*otherVector.xyz.d2;
        double y=this.xyz.d3*otherVector.xyz.d1-this.xyz.d1*otherVector.xyz.d3;
        double z=this.xyz.d1*otherVector.xyz.d2-this.xyz.d2*otherVector.xyz.d1;
        return new Vector(x,y,z);
    }

    /**
     *
     * @param u;
     * @return the dot product of this vector and the other vector;
     */
    public double dotProduct(Vector u) {
        return  this.xyz.d1*u.xyz.d1+
                this.xyz.d2*u.xyz.d2+
                this.xyz.d3*u.xyz.d3;
    }

    /**
     * Determines whether this Point object is equal to another object.
     *
     * @param o the object to compare with this Point object
     * @return true if the two objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this==o) return true;
        if (!(o instanceof Vector vector)) return false;
        return super.equals(vector);
    }

    /**
     * Returns a string representation of this Point object.
     *
     * @return a string representation of this Point object
     */
    @Override
    public String toString() {
        return "->" + super.toString();
    }


    @Override
    public Vector add(Vector vector1) {
        return new Vector(xyz.d1+vector1.xyz.d1,
                xyz.d2+vector1.xyz.d2,
                xyz.d3+vector1.xyz.d3);
    }

    /**
     *
     * @param alpha;
     * @return new vector that is the scale of this vector by alpha;
     */
    public Vector scale(double alpha){
        return new Vector(xyz.d1*alpha,
                xyz.d2*alpha,
                xyz.d3*alpha);
    }

    /**
     * @param axis;
     * @param rad;
     * @return new vector that is the rotation of this vector by rad around the axis;
     */
    public Vector rotate(double rad, Vector axis) {
        //rad = rad%(2*(Math.PI));

        if (abs(rad) == Math.PI/2 || abs(rad) == 3*Math.PI/2){ // 90 or 270 degrees
            return axis.scale(Math.sin(rad)); // sin(90) = 1, sin(270) = -1
        } else if (abs(rad) == 0 || abs(rad) == Math.PI) { // 0 or 180 degrees
            return this.scale(Math.cos(rad)); // cos(0) = 1, cos(180) = -1
        }

        // to rotate the vector we create plane with the vector and the axis, and rotate the vector in the plane
        // do to that our vector is orthogonal to the axis, we 
        Vector V1 = this.scale(Math.cos(rad));
        Vector V2 = axis.scale(Math.sin(rad));
        return V1.add(V2);
    }
}

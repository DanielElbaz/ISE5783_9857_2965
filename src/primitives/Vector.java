package primitives;

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
    public double length(){
        return Math.sqrt(lengthSquared());
    }

    public double lengthSquared() {
        double x = xyz.d1;
        double y = xyz.d2;
        double z = xyz.d3;
        return x*x+y*y+z*z;
    }

    public Vector normalize() {
        double len=length();
        return new Vector(xyz.d1/len,xyz.d2/len,xyz.d3/len);
    }

    public Vector crossProduct(Vector otherVector) {
        double x=this.xyz.d2*otherVector.xyz.d3-this.xyz.d3*otherVector.xyz.d2;
        double y=this.xyz.d3*otherVector.xyz.d1-this.xyz.d1*otherVector.xyz.d3;
        double z=this.xyz.d1*otherVector.xyz.d2-this.xyz.d2*otherVector.xyz.d1;
        return new Vector(x,y,z);
    }

    public double dotProduct(Vector u) {

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
        return xyz.equals(vector.xyz);
    }
}

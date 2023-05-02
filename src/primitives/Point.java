package primitives;

import java.util.Objects;
/*
 * @author: Daniel Elbaz & Efraim Levy
 */

public class Point {
    final Double3 xyz;

    /**
     * Constructs a Point object with the specified x, y and z coordinates.
     *
     * @param x the x coordinate of the point
     * @param y the y coordinate of the point
     * @param z the z coordinate of the point
     */
    public Point(double x, double y, double z) {
        xyz = new Double3(x, y, z);
    }

    /**
     * Constructs a Point object from a Double3 coordinates.
     *
     * @param double3 the Double3 object representing the point
     */
    Point(Double3 double3) {
        this(double3.d1, double3.d2, double3.d3);
    }

    public double getX(){ return xyz.d1;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // check if the objects are the same
        if (!(o instanceof Point point)) return false; // check if the object is a Point object
        return xyz.equals(point.xyz); // check if the coordinates are equal
    }

    @Override
    public int hashCode() {
        return Objects.hash(xyz); // hash the coordinates using the Objects class
    }

    /**
     * Returns a string representation of this Point object.
     *
     * @return a string representation of this Point object
     */
    @Override
    public String toString() {
        return "Point :" + xyz; // return the string representation of the Double3 object
    }

    /**
     * Calculates the distance between this Point object and another Point object.
     *
     * @param other the other Point object
     * @return the distance between this Point object and the other Point object
     */
    public double distance(Point other) {
        return Math.sqrt(distanceSquared(other));
    }

    /**
     * Calculates the distance squared between this Point object and another Point object.
     *
     * @param other the other Point object
     * @return the distance squared between this Point object and the other Point object
     */
    public double distanceSquared(Point other) {
        double dx = other.xyz.d1 - xyz.d1; // calculate the difference in x coordinates
        double dy = other.xyz.d2 - xyz.d2; // calculate the difference in y coordinates
        double dz = other.xyz.d3 - xyz.d3; // calculate the difference in z coordinates
        return dx * dx + dy * dy + dz * dz; // return the sum of the squared differences
    }

    /**
     *
     * @param vector;
     * @return new point that is the sum of this point and the vector;
     */
    public Point add(Vector vector) {
        return new Point(xyz.add(vector.xyz));

    }

    /**
     *
     * @param secondPoint;
     * @return new vector that is the subtraction of this point and the second point;
     */
    public Vector subtract(Point secondPoint) {
        return new Vector(xyz.subtract(secondPoint.xyz));
    }


}


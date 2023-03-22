package geometries;

/**
 *  The RadialGeometry abstract class represents a radial geometries in a 3D space.
 */
public abstract class RadialGeometry implements Geometry {
    protected double radius;

    public RadialGeometry(double radius) {
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }
}

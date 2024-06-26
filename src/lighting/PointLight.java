package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class PointLight extends Light implements LightSource{
    private Point position;
    private double kC = 1;
    private double kL = 0;
    private double kQ = 0;

    /**
     * constructor for PointLight
     * @param intensity the color of the light
     * @param position the position of the light
     */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    /**
     * constructor for PointLight
     * @param kC the constant factor of the light
     */
    public PointLight setkC(double kC) {
        this.kC = kC;
        return this;
    }

    public PointLight setkL(double kL) {
        this.kL = kL;
        return this;
    }

    public PointLight setkQ(double kQ) {
        this.kQ = kQ;
        return this;
    }

    /**
     * constructor for PointLight
     * @param p the position of the light
     */
    @Override
    public Color getIntensity(Point p) {
        double distanceSquared = p.distanceSquared(position);
        double distance = Math.sqrt(distanceSquared);
        double attenuation = 1/(kC + kL*distance + kQ*distanceSquared);
        return this.getIntensity().scale(attenuation);
    }

    /**
     * constructor for PointLight
     * @param p the position of the light
     */
    @Override
    public Vector getL(Point p) {
        return p.subtract(this.position).normalize();
    }

    @Override
    public double getDistanceSquared(Point point) {
        return position.distanceSquared(point);
    }
}

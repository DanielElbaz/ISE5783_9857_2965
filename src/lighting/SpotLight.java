package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class SpotLight extends PointLight{
    private Vector direction;
    private int narrowBeam = 1;

    /**
     * constructor for SpotLight
     * @param narrowBeam the narrow beam of the light
     */
    public SpotLight setNarrowBeam(int narrowBeam) {
        this.narrowBeam = narrowBeam;
        return this;
    }

    /**
     * constructor for SpotLight
     * @param intensity the color of the light
     * @param position the position of the light
     * @param direction the direction of the light
     */
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }

    /**
     * constructor for SpotLight
     * @param p the position of the light
     */
    @Override
    public Color getIntensity(Point p) {
        double rangeFactor = Math.max(0, direction.dotProduct(getL(p)));
        double rangeInPow = Math.pow(rangeFactor,narrowBeam);

        return super.getIntensity(p).scale(rangeInPow);
    }
}

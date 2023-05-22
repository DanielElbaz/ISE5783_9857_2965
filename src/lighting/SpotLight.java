package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class SpotLight extends PointLight{
    private Vector direction;
    private int narrowBeam = 1;

    public SpotLight setNarrowBeam(int narrowBeam) {
        this.narrowBeam = narrowBeam;
        return this;
    }

    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }

    @Override
    public Color getIntensity(Point p) {
        double rangeFactor = Math.max(0, direction.dotProduct(getL(p)));
        double rangeInPow = Math.pow(rangeFactor,narrowBeam);

        return super.getIntensity(p).scale(rangeInPow);
    }
}

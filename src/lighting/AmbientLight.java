package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * class AmbientLight is a class that represents the ambient light in the scene
 * it has a color and a factor
 */
public class AmbientLight {
    private Color intensity;

    public AmbientLight(Color Ia, Double3 Ka) {
        this.intensity = Ia.scale(Ka);
    }

    public AmbientLight(Color Ia, Double Ka) {
        this.intensity = Ia.scale(Ka);
    }

    public static AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);

    public Color getIntensity() {
        return intensity;
    }


}

package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * class AmbientLight is a class that represents the ambient light in the scene
 * it has a color and a factor
 */
public class AmbientLight extends Light{

    public AmbientLight(Color Ia, Double3 Ka) {
        super(Ia.scale(Ka));
    }

    public AmbientLight(Color Ia, Double Ka) {
        super(Ia.scale(Ka));
    }

    public static AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);


}

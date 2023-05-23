package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * class AmbientLight is a class that represents the ambient light in the scene
 * it has a color and a factor
 */
public class AmbientLight extends Light{

    /**
     * constructor for AmbientLight
     * @param Ia the color of the light
     * @param Ka the factor of the light
     */
    public AmbientLight(Color Ia, Double3 Ka) {
        super(Ia.scale(Ka));
    }

    /**
     * constructor for AmbientLight
     * @param Ia the color of the light
     * @param Ka the factor of the light
     */
    public AmbientLight(Color Ia, Double Ka) {
        super(Ia.scale(Ka));
    }

    /**
     * constructor for AmbientLight
     */
    public static AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);


}

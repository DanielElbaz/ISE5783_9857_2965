package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import lighting.LightSource;
import primitives.Color;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * class Scene is a class that represents the scene
 * it has a name, a background, an ambient light and a geometries
 */
public class Scene {
    public String name;
    public Color background = Color.BLACK;
    public AmbientLight ambientLight = AmbientLight.NONE;
    public Geometries geometries = new Geometries();
    public List<LightSource> lights = new LinkedList<>();

    public Scene(String name) {
        this.name = name;
    }

    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }

    public Scene setLights(LightSource... lightSources){
        lights.addAll(List.of(lightSources));
        return this;
    }
}

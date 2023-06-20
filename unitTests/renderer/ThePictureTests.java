package renderer;

import static java.awt.Color.*;

import geometries.*;
import lighting.DirectionalLight;
import lighting.PointLight;
import org.junit.jupiter.api.Test;

import lighting.AmbientLight;
import lighting.SpotLight;
import primitives.*;
import renderer.*;
import scene.Scene;


public class ThePictureTests {
    private Scene scene = new Scene("Test scene");

    @Test
    public void My_picture() {
        Camera camera = new Camera(new Point(6500, 0, 1100), new Point(0, 0, 750)) //
                .setVPSize(200, 200).setVPDistance(750).setSamples(0);

        scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));

        scene.geometries.add(
                new Polygon(new Point(-7000,-800,0),new Point(-7000,800,0),
                    new Point(150,800,0),new Point(150,-800,0))
                .setEmission(new Color(242,242,242)).setMaterial(new Material().setkD(0.5).setkS(0.5).setShininess(100).setkR(0.01)),

                new Polygon(new Point(-7000,-800,1600),new Point(-7000,800,1600),
                    new Point(150,800,1600),new Point(150,-800,1600))
                .setEmission(new Color(255,0,0)).setMaterial(new Material().setkD(0.5).setkS(0.5).setShininess(100).setkR(0.01)),

                new Polygon(new Point(-7000,-800,0),new Point(150,-800,0),
                        new Point(150,-800,1600),new Point(-7000,-800,1600))
                        .setEmission(new Color(0,255,0)).setMaterial(new Material().setkD(0.5).setkS(0.5).setShininess(100).setkR(0.01)),

                new Polygon(new Point(-7000,800,0),new Point(-7000,800,1600),
                        new Point(150,800,1600),new Point(150,800,0))
                        .setEmission(new Color(0,0,255)).setMaterial(new Material().setkD(0.5).setkS(0.5).setShininess(100).setkR(0.01)),

                new Polygon(new Point(-7000,-800,0),new Point(-7000,800,0),
                        new Point(-7000,800,1600),new Point(-7000,-800,1600))
                        .setEmission(new Color(128,128,128)).setMaterial(new Material().setkD(0.5).setkS(0.5).setShininess(100).setkR(0.01)),
                //until here the box

                new Cylinder(400,new Ray(new Point (-2500,-200,300), new Vector(0,0,1)),50)
                        .setEmission(new Color(139, 69, 19)).setMaterial(new Material().setkD(0.5).setkS(0.5).setShininess(100).setkR(0.01)),

                new Cylinder(60,new Ray(new Point (-2500,-200,20), new Vector(0,0,1)),330)
                        .setEmission(new Color(139, 69, 19)).setMaterial(new Material().setkD(0.5).setkS(0.5).setShininess(100).setkR(0.01)),

                new Cylinder(250,new Ray(new Point (-2500,-200,0), new Vector(0,0,1)),20)
                        .setEmission(new Color(139, 69, 19)).setMaterial(new Material().setkD(0.5).setkS(0.5).setShininess(100).setkR(0.01)),
                //until here the table

                new Cylinder(50,new Ray(new Point(-2400,-150,350),new Vector(0,0,1)),5)
                        .setEmission(new Color(0,191,255)).setMaterial(new Material().setkD(0.5).setkS(0.5).setShininess(100).setkR(1)),

                new Cylinder(8,new Ray(new Point(-2400,-150,355),new Vector(0,0,1)),150)
                        .setEmission(new Color(0,191,255)).setMaterial(new Material().setkD(0.5).setkS(0.5).setShininess(100).setkR(1)),

                new HalfSphere(60,new Point(-2400,-150,505),new Vector(0,0,1))
                        .setMaterial(new Material().setkR(1).setkS(0.5).setkD(0.5))
                        .setEmission(new Color(0,191,255)),
                //until here the glass
                new HalfSphere(60,new Point(-2350,-150,500),new Vector(0,0,1))
                        .setMaterial(new Material().setkR(0.1).setkS(0.5).setkD(0.5))
                        .setEmission(new Color(128, 0, 32)),
                //until here the wine
                new Polygon(new Point(-2500,790,800),new Point(-5000,790,800),
                        new Point(-5000,790,1200),new Point(-2500,790,1250))
                        .setEmission(new Color(0,191,255)).setMaterial(new Material().setkD(0.5).setkS(0.5).setShininess(100).setkR(1)),
                //until here the window
                new HalfSphere(100,new Point(-2400,-400,450),new Vector(0,0,1))
                        .setMaterial(new Material().setkR(0.1).setkS(0.5).setkD(0.5))
                        .setEmission(new Color(red)),
                //until here the bowl
                new Cylinder(20,new Ray(new Point(-2000,300,0), new Vector(0,0,1)),250)
                        .setEmission(new Color(139, 69, 19)).setMaterial(new Material().setkD(0.5).setkS(0.5).setShininess(100).setkR(0.1)),
                new Cylinder(20,new Ray(new Point(-1500,200,0), new Vector(0,0,1)),250)
                        .setEmission(new Color(139, 69, 19)).setMaterial(new Material().setkD(0.5).setkS(0.5).setShininess(100).setkR(0.1)),
                new Cylinder(20,new Ray(new Point(-1500,400,0), new Vector(0,0,1)),250)
                        .setEmission(new Color(139, 69, 19)).setMaterial(new Material().setkD(0.5).setkS(0.5).setShininess(100).setkR(0.1)),
                new Cylinder(20,new Ray(new Point(-2000,500,0), new Vector(0,0,1)),250)
                        .setEmission(new Color(139, 69, 19)).setMaterial(new Material().setkD(0.5).setkS(0.5).setShininess(100).setkR(0.1)),
                new Polygon(new Point(-1400,190,250),new Point(-1400,410,250),
                        new Point(-2100,510,250),new Point(-2100,310,250))
                        .setEmission(new Color(139, 69, 19)).setMaterial(new Material().setkD(0.5).setkS(0.5).setShininess(100)),
                new Polygon(new Point(-1400,410,250),new Point(-2100,510,250),
                        new Point(-2100,510,550),new Point(-1400,410,550))
                        .setEmission(new Color(139, 69, 19)).setMaterial(new Material().setkD(0.5).setkS(0.5).setShininess(100)),
                //until here the chair
                new Polygon(new Point(-1000,-400,10),new Point(-1000,400,10),
                        new Point(-1500,400,10),new Point(-1500,-400,10))
                        .setEmission(new Color(128, 0, 32)).setMaterial(new Material().setkD(0.5).setkS(0.5).setShininess(100)),
                new Triangle(new Point(-1250,-100,12),new Point(-1300,100,12),new Point(-1200,100,12))
                        .setEmission(new Color(139, 117, 0)).setMaterial(new Material().setkD(0.5).setkS(0.5).setShininess(100)),
                //until here the carpet
                new Polygon(new Point(-6990,-400,600),new Point(-6990,400,600),
                        new Point(-6990,400,1000),new Point(-6990,-400,1000))
                        .setEmission(new Color(0, 0, 0)).setMaterial(new Material().setkD(0.5).setkS(0.5).setShininess(100)),
                new Sphere(15,new Point(-6980,350,600))
                        .setEmission(new Color(255, 0, 0)).setMaterial(new Material().setkD(0.5).setkS(0.5).setShininess(100)),

                new Polygon(new Point(-2500, -100, 310), new Point(-2500, 100, 310),
                        new Point(-2500, 100, 360), new Point(-2500, -100, 360))
                        .setEmission(new Color(0, 0, 0))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setShininess(100)),

                new Sphere(15, new Point(-2490, 70, 320))
                .setEmission(new Color(255, 0, 0))
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setShininess(100)),

                new Sphere(15, new Point(-2490, -10, 320))
                .setEmission(new Color(0, 255, 0))
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setShininess(100)),

                 new Sphere(15, new Point(-2490, -90, 320))
                .setEmission(new Color(0, 0, 255))
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setShininess(100)),
                //until here the TV and the remote

                new Polygon(new Point(-5500, -770, 600), new Point(-5500, -770, 1000),
                        new Point(-400, -770, 1000), new Point(-400, -770, 600))
                        .setEmission(new Color(218, 165, 32)) // Gold color for the painting frame
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setShininess(100).setkR(0.01)),

                new Polygon(new Point(-5400, -750, 650), new Point(-5400, -750, 950),
                        new Point(-500, -750, 950), new Point(-500, -750, 650))
                        .setEmission(new Color(255, 255, 255)) // White color for the painting
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setShininess(100).setkR(0.01)),
                //until here the painting
                new Cylinder(50,new Ray(new Point(-3000,0,1000), new Vector(0,0,1)),600)
                        .setEmission(new Color(184, 134, 19)).setMaterial(new Material().setkD(0.5).setkS(0.5).setShininess(100).setkR(0.1)),
                new HalfSphere(250,new Point(-3000,0,1000),new Vector(0,0,-1))
                        .setEmission(new Color(184, 134, 11)).setMaterial(new Material().setkD(0.5).setkS(0.5).setShininess(100).setkR(0.7)));




                scene.setLights(
                new SpotLight(new Color(700, 300, 500), new Point(1000, 200, 750), new Vector(-2, -0.4, -1.4))
                        .setNarrowBeam(400).setkL(0.0003).setkQ(0.000001),
                new PointLight(new Color(500, 300, 300), new Point(-2500, 5400, 2000))
                        .setkL(0.0003).setkQ(0.000001));

        ImageWriter imageWriter = new ImageWriter("box", 1000, 1000);

        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();

    }
}




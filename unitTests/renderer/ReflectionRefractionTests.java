/**
 *
 */
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

/** Tests for reflection and transparency functionality, test for partial
 * shadows
 * (with transparency)
 * @author dzilb */
public class ReflectionRefractionTests {
    private Scene scene = new Scene("Test scene");

    /** Produce a picture of a sphere lighted by a spot light */
    @Test
    public void twoSpheres() {
        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(150, 150).setVPDistance(1000);

        scene.geometries.add( //
                new Sphere(50d, new Point(0, 0, -50)).setEmission(new Color(BLUE)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setShininess(100).setkT(0.3)),
                new Sphere(25d, new Point(0, 0, -50)).setEmission(new Color(RED)) //
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setShininess(100)));
        scene.lights.add( //
                new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2)) //
                        .setkL(0.0004).setkQ(0.0000006));

        camera.setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500)) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }

    /** Produce a picture of a sphere lighted by a spot light */
    @Test
    public void twoSpheresOnMirrors() {
        Camera camera = new Camera(new Point(0, 0, 10000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(2500, 2500).setVPDistance(10000); //

        scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

        scene.geometries.add( //
                new Sphere(400d, new Point(-950, -900, -1000)).setEmission(new Color(0, 50, 100)) //
                        .setMaterial(new Material().setkD(0.25).setkS(0.25).setShininess(20)
                                .setkT(new Double3(0.5, 0, 0))),
                new Sphere(200d, new Point(-950, -900, -1000)).setEmission(new Color(100, 50, 20)) //
                        .setMaterial(new Material().setkD(0.25).setkS(0.25).setShininess(20)),
                new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                        new Point(670, 670, 3000)) //
                        .setEmission(new Color(20, 20, 20)) //
                        .setMaterial(new Material().setkR(1)),
                new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                        new Point(-1500, -1500, -2000)) //
                        .setEmission(new Color(20, 20, 20)) //
                        .setMaterial(new Material().setkR(new Double3(0.5, 0, 0.4))));

        scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point(-750, -750, -150), new Vector(-1, -1, -4)) //
                .setkL(0.00001).setkQ(0.000005));

        ImageWriter imageWriter = new ImageWriter("reflectionTwoSpheresMirrored", 500, 500);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }

    /** Produce a picture of a two triangles lighted by a spot light with a
     * partially
     * transparent Sphere producing partial shadow */
    @Test
    public void trianglesTransparentSphere() {
        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(200, 200).setVPDistance(1000);

        scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));

        scene.geometries.add( //
                new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135),
                        new Point(75, 75, -150)) //
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setShininess(60)), //
                new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150)) //
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setShininess(60)), //
                new Sphere(30d, new Point(60, 50, -50)).setEmission(new Color(BLUE)) //
                        .setMaterial(new Material().setkD(0.2).setkS(0.2).setShininess(30).setkT(0.6)));

        scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1)) //
                .setkL(4E-5).setkQ(2E-7));

        ImageWriter imageWriter = new ImageWriter("refractionShadow", 600, 600);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }

    @Test
    public void myPicture(){
        Camera camera = new Camera(new Point(9000, 1500, 1350),new Point(250,0,250)) //
                .setVPSize(200, 200).setVPDistance(750);

        scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));

        scene.geometries.add(new Sphere(200,new Point(250,0,250))
                        .setMaterial(new Material().setShininess(100).setkD(0.6).setkS(0.4).setkR(1))
                        .setEmission(new Color(PINK)),
                new Sphere(30, new Point(816,0,280))
                        .setMaterial(new Material().setShininess(100).setkD(0.8).setkS(0.2).setkR(0.2))
                        .setEmission(new Color(184,134,11)),
                new Sphere(30, new Point(250,566,280))
                        .setMaterial(new Material().setShininess(100).setkD(0.8).setkS(0.2).setkR(0.2))
                        .setEmission(new Color(184,134,11)),
                new Sphere(30, new Point(250,-566,280))
                        .setMaterial(new Material().setShininess(100).setkD(0.8).setkS(0.2).setkR(0.2))
                        .setEmission(new Color(184,134,11)),
                new Sphere(30, new Point(330,-375,900))
                        .setMaterial(new Material().setShininess(100).setkD(0.8).setkS(0.2).setkR(0.2))
                        .setEmission(new Color(184,134,11)),
                new Sphere(30, new Point(330,-370,800))
                        .setMaterial(new Material().setShininess(100).setkD(0.8).setkS(0.2).setkR(0.2))
                        .setEmission(new Color(184,134,11)),
                new Sphere(30, new Point(330,-366,720))
                        .setMaterial(new Material().setShininess(100).setkD(0.8).setkS(0.2).setkR(0.2))
                        .setEmission(new Color(184,134,11)),
                new Sphere(30, new Point(330,-361.5,630))
                        .setMaterial(new Material().setShininess(100).setkD(0.8).setkS(0.2).setkR(0.2))
                        .setEmission(new Color(184,134,11)),
                new Sphere(30, new Point(330,-357,540))
                        .setMaterial(new Material().setShininess(100).setkD(0.8).setkS(0.2).setkR(0.2))
                        .setEmission(new Color(184,134,11)),
                new Sphere(30, new Point(330,-352.5,450))
                        .setMaterial(new Material().setShininess(100).setkD(0.8).setkS(0.2).setkR(0.2))
                        .setEmission(new Color(184,134,11)),
                new Sphere(30, new Point(330,-348,360))
                        .setMaterial(new Material().setShininess(100).setkD(0.8).setkS(0.2).setkR(0.2))
                        .setEmission(new Color(184,134,11)),
                new Triangle(new Point(816,0,250),
                        new Point(250,566,250),
                        new Point(250,0,0))
                        .setMaterial(new Material().setShininess(80)
                                .setkT(0.7)).setEmission(new Color(magenta)),
                new Triangle(new Point(250,566,250),
                        new Point(-316,0,250),
                        new Point(250,0,0))
                        .setMaterial(new Material().setShininess(80)
                                .setkT(0.7)).setEmission(new Color(blue)),
                new Triangle(new Point(-316,0,250),
                        new Point(250,-566,250),
                        new Point(250,0,0))
                        .setMaterial(new Material().setShininess(80)
                                .setkT(0.7)).setEmission(new Color(green)),
                new Triangle(new Point(250,-566,250),
                        new Point(816,0,250),
                        new Point(250,0,0))
                        .setMaterial(new Material().setShininess(80)
                                .setkT(0.7)).setEmission(new Color(red)),
                new Triangle(new Point(3000,-1000,0),
                        new Point(-1000,-1000,0),
                        new Point(3000,1000,0))
                        .setMaterial(new Material().setShininess(100)
                                .setkR(0.6).setkS(80).setkD(20)),
                new Triangle(new Point(3000,1000,0),
                        new Point(-1000,1000,0),
                        new Point(-1000,-1000,0))
                        .setMaterial(new Material().setShininess(100)
                                .setkR(0.6).setkS(80).setkD(20)),
                new Tube(new Ray(new Point(400, -400,0),new Vector(0,-0.05,1)),40)
                        .setMaterial(new Material().setkR(0.3).setkD(0.5).setkS(0.5)
                                .setShininess(100)).setEmission(new Color(136,139,141)));
                          /* new Polygon(new Point(-200,-500,0),
                                       new Point(-200,-500,2000),
                                       new Point(-200,500,2000),
                                       new Point(-200,500,0))
                                       //.setEmission(new Color(GREEN))
                                       .setMaterial(new Material().setkD(0.5).setkS(0.5).setShininess(20)),
                           new Polygon(new Point(500,-500,-30),
                                       new Point(-200,-500,0),
                                       new Point(-200,500,0),
                                       new Point(500,500,-30))
                                   //.setEmission(new Color(MAGENTA))
                                   .setMaterial(new Material().setkD(0.5).setkS(0.5).setShininess(20)),
                           new Polygon(new Point(-500,250,0),
                                       new Point(0,250,0),
                                       new Point(0,250,500),
                                       new Point(-500,250,500))
                                   .setEmission(new Color(GREEN))
                                   .setMaterial(new Material().setkD(0.5).setkS(0.5).setShininess(20)),
                           new Polygon(new Point(0,-250,500),
                                       new Point(0,250,500),
                                       new Point(-500,250,500),
                                       new Point(-500,-250,500))
                                   .setEmission(new Color(YELLOW))
                                   .setMaterial(new Material().setkD(0.5).setkS(0.5).setShininess(20)),
                           new Polygon(new Point(0,-250,0),
                                       new Point(0,250,0),
                                       new Point(-500,250,0),
                                       new Point(-500,-250,0))
                                   .setEmission(new Color(BLUE))
                                   .setMaterial(new Material().setkD(0.5).setkS(0.5).setShininess(20)));*/

        scene.setLights(
                new SpotLight(new Color(700,300,500),new Point(1000,200,750),new Vector(-2,-0.4,-1.4))
                        .setNarrowBeam(400).setkL(0.0003).setkQ(0.000001),
                new PointLight(new Color(500,300,300),new Point(-2500,5400,2000))
                        .setkL(0.0003).setkQ(0.000001));

        ImageWriter imageWriter = new ImageWriter("myPicture", 600, 600);

        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }

    @Test
    public void spheresOnTube(){
        Camera camera = new Camera(new Point(9000, 0, 1500),new Point(0,0,750)) //
                .setVPSize(200, 200).setVPDistance(750);

        scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));

        scene.geometries.add(new Tube(new Ray(new Point(0,-1,0),new Vector(0,1,0)),300)
                .setMaterial(new Material().setShininess(300).setkS(0.5).setkD(0.5).setkR(1))
                .setEmission(new Color(184,134,11)));
        double radius = 200;
        int j = 300;
        Color color = new Color(136,139,141);
        for (j = j; j <= 1000;) {
            j += radius;
            for (int i =-1000; i <= 1000 ; i+= 400) {
                scene.geometries.add(new Sphere(radius,new Point(0,i,j))
                        .setMaterial(new Material().setShininess(300).setkS(0.5).setkD(0.5).setkR(1))
                        .setEmission(color));
            }
            j += radius;
            radius = radius/2;
            color = color.scale(0.7);
        }

        scene.setLights(
                new SpotLight(new Color(700,300,500),new Point(1000,200,750),new Vector(-2,-0.4,-1.4))
                        .setNarrowBeam(400).setkL(0.0003).setkQ(0.000001),
                new PointLight(new Color(500,300,300),new Point(-2500,5400,2000))
                        .setkL(0.0003).setkQ(0.000001));

        ImageWriter imageWriter = new ImageWriter("tubeAndSpheres", 600, 600);

        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();

    }

    @Test
    public void myTests(){
        Camera camera = new Camera(new Point(9000, 0, 1000),new Point(0,0,100)) //
                .setVPSize(200, 200).setVPDistance(750);

        scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));

        scene.geometries.add(new HalfSphere(250,new Point(1,-1,0),new Vector(0,0,1))
                .setMaterial(new Material().setkR(0.8).setkS(0.5).setkD(0.5))
                .setEmission(new Color(blue)),
                new HalfSphere(180,new Point(1,-1,0),new Vector(0,0,1))
                        .setMaterial(new Material().setkR(0.8).setkS(0.5).setkD(0.5))
                        .setEmission(new Color(blue)),
                new Ring(250,new Point(1,-1,0),new Vector(0,0,1),180)
                        .setMaterial(new Material().setkR(0.8).setkS(0.5).setkD(0.5))
                        .setEmission(new Color(blue)),
                new Sphere(180,new Point(1,-1,0))
                        .setMaterial(new Material().setkR(0.8).setkS(0.5).setkD(0.5))
                        .setEmission(new Color(ORANGE)),
                new Ring(250, new Point(1,-1,430),new Vector(1,0,0), 180)
                .setMaterial(new Material().setkR(0.8).setkS(0.5).setkD(0.5))
                .setEmission(new Color(blue)),
                new Tube(new Ray(new Point(0,0,560), new Vector(1,2,0)), 50)
                        .setMaterial(new Material().setkR(0.8).setkS(0.5).setkD(0.5))
                        .setEmission(new Color(136,139,141))
                );

        scene.setLights(
                new SpotLight(new Color(700,300,500),new Point(1000,200,750),new Vector(-2,-0.4,-1.4))
                        .setNarrowBeam(400).setkL(0.0003).setkQ(0.000001),
                new PointLight(new Color(500,300,300),new Point(-2500,5400,2000))
                        .setkL(0.0003).setkQ(0.000001));

        ImageWriter imageWriter = new ImageWriter("myTestes", 600, 600);

        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }

    @Test
    public void cylinderTest(){
        Camera camera = new Camera(new Point(7000, 0, 1000),new Point(0,0,100)) //
                .setVPSize(200, 200).setVPDistance(750).setSamples(9);

        scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));

        scene.geometries.add(new SliceOfSphare(200,new Point(0,-50,100),
                new Vector(-1,1,-5), 0,40)
                .setMaterial(new Material().setkR(1).setkS(0.6).setkD(0.4))
                .setEmission(new Color(BLUE)),
                new SliceOfSphare(200,new Point(0,-50,100),
                        new Vector(-1,1,-5), 40,80)
                        .setMaterial(new Material().setkR(1).setkS(0.6).setkD(0.4))
                        .setEmission(new Color(GREEN)),
                new SliceOfSphare(200,new Point(0,-50,100),
                        new Vector(-1,1,-5), 80,120)
                        .setMaterial(new Material().setkR(1).setkS(0.6).setkD(0.4))
                        .setEmission(new Color(red)),
                new SliceOfSphare(200,new Point(0,-50,100),
                        new Vector(-1,1,-5), 120,160)
                        .setMaterial(new Material().setkR(1).setkS(0.6).setkD(0.4))
                        .setEmission(new Color(CYAN)),
                new SliceOfSphare(200,new Point(0,-50,100),
                        new Vector(-1,1,-5), 160,200)
                        .setMaterial(new Material().setkR(1).setkS(0.6).setkD(0.4))
                        .setEmission(new Color(ORANGE)),
                new SliceOfSphare(200,new Point(0,-50,100),
                        new Vector(1,-1,5), 160,200)
                        .setMaterial(new Material().setkR(1).setkS(0.6).setkD(0.4))
                        .setEmission(new Color(BLUE)),
                new SliceOfSphare(200,new Point(0,-50,100),
                        new Vector(1,-1,5), 120,160)
                        .setMaterial(new Material().setkR(1).setkS(0.6).setkD(0.4))
                        .setEmission(new Color(MAGENTA)),
                new SliceOfSphare(200,new Point(0,-50,100),
                        new Vector(1,-1,5), 80,120)
                        .setMaterial(new Material().setkR(1).setkS(0.6).setkD(0.4))
                        .setEmission(new Color(PINK)),
                new SliceOfSphare(200,new Point(0,-50,100),
                        new Vector(1,-1,5), 40,80)
                        .setMaterial(new Material().setkR(1).setkS(0.6).setkD(0.4))
                        .setEmission(new Color(GREEN)),
                new SliceOfSphare(200,new Point(0,-50,100),
                        new Vector(1,-1,5), 0,40)
                        .setMaterial(new Material().setkR(1).setkS(0.6).setkD(0.4))
                        .setEmission(new Color(BLUE)),
                new Ring(370,new Point(0,-50,100),new Vector(1,-1,5),350)
                        .setMaterial(new Material().setkR(1).setkS(0.6).setkD(0.4))
                        .setEmission(new Color(184,134,11)),
                new Ring(390,new Point(0,-50,100),new Vector(1,-1,5),370)
                        .setMaterial(new Material().setkR(1).setkS(0.6).setkD(0.4))
                        .setEmission(new Color(136,139,141)),
                new Ring(410,new Point(0,-50,100),new Vector(1,-1,5),390)
                        .setMaterial(new Material().setkR(1).setkS(0.6).setkD(0.4))
                        .setEmission(new Color(184,134,11)),
                new Ring(430,new Point(0,-50,100),new Vector(1,-1,5),410)
                        .setMaterial(new Material().setkR(1).setkS(0.6).setkD(0.4))
                        .setEmission(new Color(136,139,141)),
                new Ring(450,new Point(0,-50,100),new Vector(1,-1,5),430)
                        .setMaterial(new Material().setkR(1).setkS(0.6).setkD(0.4))
                        .setEmission(new Color(184,134,11)));

        scene.setLights(
                new SpotLight(new Color(700,300,500),new Point(1000,200,750),new Vector(-2,-0.4,-1.4))
                        .setNarrowBeam(400).setkL(0.0003).setkQ(0.000001),
                new PointLight(new Color(500,300,300),new Point(-2500,5400,2000))
                        .setkL(0.0003).setkQ(0.000001));

        ImageWriter imageWriter = new ImageWriter("saturn", 1000, 1000);

        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }
}
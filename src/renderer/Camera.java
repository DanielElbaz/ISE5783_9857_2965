package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.MissingResourceException;

import static primitives.Util.isZero;

public class Camera {
    private Point place;
    private Vector vUp;
    private Vector vTo;
    private Vector vRight;
    private double height;
    private double width;
    private double distance;
    private ImageWriter imageWriter;
    private RayTracerBase rayTracerBase;

    public Point getPlace() {
        return place;
    }

    public Vector getvUp() {
        return vUp;
    }

    public Vector getvTo() {
        return vTo;
    }

    public Vector getvRight() {
        return vRight;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public double getDistance() {
        return distance;
    }

    public Camera(Point place, Vector vTo, Vector vUp) {
        if(!isZero(vUp.dotProduct(vTo)))
            throw new IllegalArgumentException("The vectors are not orthogonal");
        this.place = place;
        this.vUp = vUp.normalize();
        this.vTo = vTo.normalize();
        this.vRight= vTo.crossProduct(vUp).normalize();
    }
    public Camera setVPSize(double width, double height){
        this.width=width;
        this.height=height;
        return this;
    }
    public Camera setVPDistance(double distance){
        this.distance=distance;
        return this;
    }
    public Camera setImageWriter(ImageWriter imageWriter){
        this.imageWriter = imageWriter;
        return this;
    }
    public Camera setRayTracer(RayTracerBase tracer){
        this.rayTracerBase = tracer;
        return this;
    }
    /**
     * Constructs a ray through a pixel
     * @param nX the number of pixels in the x direction
     * @param nY the number of pixels in the y direction
     * @param j the x coordinate of the pixel
     * @param i the y coordinate of the pixel
     * @return the ray through the pixel
     */
    public Ray constructRayThroughPixel(int nX, int nY, int j, int i){
        Point Pc=place.add(vTo.scale(distance)); //the center of the view plane
        double Ry=height/nY;
        double Rx=width/nX;
        double yi=-(i-(nY-1)/2d)*Ry; //the y coordinate of the pixel
        double xj=(j-(nX-1)/2d)*Rx; //the x coordinate of the pixel
        Point Pij=Pc; //the point on the view plane
        if(!isZero(xj))
            //the point on the view plane with the x coordinate of the pixel added to it
            Pij=Pij.add(vRight.scale(xj));
        if(!isZero(yi))
            //the point on the view plane with the y coordinate of the pixel added to it
            // (the minus is because the y-axis is upside down)
            Pij=Pij.add(vUp.scale(yi));
        Vector Vij=Pij.subtract(place); //the vector from the camera to the point on the view plane
        return new Ray(place,Vij); //the ray from the camera to the point on the view plane
    }

    public void renderImage(){
        if(this.place == null ||
        this.vTo == null ||
        this.vUp == null ||
        this.vRight == null||
        this.distance == 0 ||
        this.height == 0 ||
        this.width == 0 ||
        this.rayTracerBase == null ||
        this.imageWriter == null){
            throw new MissingResourceException("some filed is empty","Camera","");
            //throw new UnsupportedOperationException();
        }
        int xPix = imageWriter.getNx();
        int yPix = imageWriter.getNy();
        for (int i = 0; i < xPix; i++) {
            for (int j = 0; j < yPix; j++) {
                Color pixColor = castRay(i,j);
                imageWriter.writePixel(i,j,pixColor);
            }
        }
    }
    public void printGrid(int interval, Color color){
        if(this.imageWriter == null){
            throw new MissingResourceException("filed empty","Camera", "ImageWriter");
        }
        int xPix = imageWriter.getNx();
        int yPix = imageWriter.getNy();
        for (int i = 0; i < xPix; i++) {
            for (int j = 0; j < yPix; j++) {
                if(i%100 == 0 || j%100 == 0){
                    imageWriter.writePixel(i,j,color);
                }
            }
        }
    }
    public void writeToImage(){
        if(this.imageWriter == null){
            throw new MissingResourceException("filed empty","Camera", "ImageWriter");
        }

        imageWriter.writeToImage();
    }
    private Color castRay(int pixX, int pixY){
        Ray ray = constructRayThroughPixel(imageWriter.getNx(),imageWriter.getNx(),pixX,pixY);
        return rayTracerBase.traceRay(ray);
    }

    public Camera rotateLeft(double rad){
        Vector rotVup = vUp.rotate(rad, vRight);
        return new Camera(place,vTo,rotVup);
    }

    public Camera rotateRight(double rad){
        Vector rotVup = vUp.rotate(rad, vRight.scale(-1));
        return new Camera(place,vTo,rotVup);
    }
}

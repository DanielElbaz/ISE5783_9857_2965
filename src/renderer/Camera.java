package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import java.util.stream.*;

import java.util.*;

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

    private static Random rand = new Random();

    private int numOfSamples = 0;

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
        if (!isZero(vUp.dotProduct(vTo)))
            throw new IllegalArgumentException("The vectors are not orthogonal");
        this.place = place;
        this.vUp = vUp.normalize();
        this.vTo = vTo.normalize();
        this.vRight = vTo.crossProduct(vUp).normalize();
    }

    public Camera(Point place, Point to){

        this(place, new Point(to.getX(), to.getY(), place.getZ()).subtract(place), new Vector(0, 0, 1));
        double alpha = Math.acos(this.vTo.dotProduct(to.subtract(place).normalize()));
        this.Turn_up(alpha);
    }
    public Camera Turn_right(double alpha){
        this.vRight= this.vRight.rotate(alpha, this.vTo.scale(-1));
        this.vTo=this.vUp.crossProduct(vRight);
        return this;
    }
    public Camera Turn_up(double alpha){
        this.vUp= this.vUp.rotate(alpha, this.vTo);
        this.vTo=this.vUp.crossProduct(vRight);
        return this;
    }

    public Camera setVPSize(double width, double height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public Camera setVPDistance(double distance) {
        this.distance = distance;
        return this;
    }

    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    public Camera setRayTracer(RayTracerBase tracer) {
        this.rayTracerBase = tracer;
        return this;
    }

    public Camera setSamples(int num){
        if(num < 0){
            throw new IllegalArgumentException("Samples can't be negative!!!");
        }
        this.numOfSamples = num;
        return this;
    }

    /**
     * Constructs a ray through a pixel
     *
     * @param nX the number of pixels in the x direction
     * @param nY the number of pixels in the y direction
     * @param j  the x coordinate of the pixel
     * @param i  the y coordinate of the pixel
     * @return the ray through the pixel
     */
    public Ray constructRayThroughPixel(int nX, int nY, int j, int i) {
        Point Pc = place.add(vTo.scale(distance)); //the center of the view plane
        double Ry = height / nY;
        double Rx = width / nX;
        double yi = -(i - (nY - 1) / 2d) * Ry; //the y coordinate of the pixel
        double xj = (j - (nX - 1) / 2d) * Rx; //the x coordinate of the pixel
        Point Pij = Pc; //the point on the view plane
        if (!isZero(xj))
            //the point on the view plane with the x coordinate of the pixel added to it
            Pij = Pij.add(vRight.scale(xj));
        if (!isZero(yi))
            //the point on the view plane with the y coordinate of the pixel added to it
            // (the minus is because the y-axis is upside down)
            Pij = Pij.add(vUp.scale(yi));
        Vector Vij = Pij.subtract(place); //the vector from the camera to the point on the view plane
        return new Ray(place, Vij); //the ray from the camera to the point on the view plane
    }



    public LinkedList<Ray> constructRaysThroughPixel(int nX, int nY, int j, int i, int numSamples) {
        LinkedList<Ray> rays = new LinkedList<>();
        Point Pc = place.add(vTo.scale(distance)); // the center of the view plane
        double Ry = height / nY;
        double Rx = width / nX;
        double subPixelSizeX = Rx / numSamples;
        double subPixelSizeY = Ry / numSamples;

        for (int s = 0; s < numSamples; s++) {
            for (int t = 0; t < numSamples; t++) {
                // Calculate the sub-pixel offset within the sub-pixel
                double subPixelOffsetX = (Math.random() + t) * subPixelSizeX;
                double subPixelOffsetY = (Math.random() + s) * subPixelSizeY;

                double yi = -(i - (nY - 1) / 2d + subPixelOffsetY) * Ry; // the y coordinate of the sub-pixel
                double xj = (j - (nX - 1) / 2d + subPixelOffsetX) * Rx; // the x coordinate of the sub-pixel

                Point Pij = Pc; // the point on the view plane

                if (!isZero(xj))
                    Pij = Pij.add(vRight.scale(xj)); // add x-coordinate offset to the view plane point

                if (!isZero(yi))
                    Pij = Pij.add(vUp.scale(yi)); // add y-coordinate offset to the view plane point

                Vector Vij = Pij.subtract(place); // the vector from the camera to the point on the view plane
                rays.add(new Ray(place, Vij)); // add the ray to the list of rays
            }
        }

        return rays;
    }



    /**
     * Renders the image using the camera settings.
     * Throws a MissingResourceException if any required field is empty.
     */
    public Camera renderImage() {
        if (this.place == null ||
                this.vTo == null ||
                this.vUp == null ||
                this.vRight == null ||
                this.distance == 0 ||
                this.height == 0 ||
                this.width == 0 ||
                this.rayTracerBase == null ||
                this.imageWriter == null) {
            throw new MissingResourceException("Some field is empty", "Camera", "");
        }
        int xPix = imageWriter.getNx();
        int yPix = imageWriter.getNy();

        Pixel.initialize(yPix,xPix, Pixel.printInterval);
        IntStream.range(0,yPix).parallel().forEach(i->{
            IntStream.range(0,xPix).parallel().forEach(j->{
                recursiveCastRays(j,i);
                Pixel.pixelDone();
                Pixel.printPixel();
            });
        });

        /*for (int i = 0; i < xPix; i++) {
            for (int j = 0; j < yPix; j++) {
                Color pixColor = recursiveCastRays(i,j);
                imageWriter.writePixel(i, j, pixColor);
            }
        }*/
        return this;
    }

    /**
     * Prints a grid pattern on the image at specified intervals with the given color.
     *
     * @param interval the interval for the grid pattern
     * @param color    the color of the grid lines
     * @throws MissingResourceException if the ImageWriter field is empty
     */
    public void printGrid(int interval, Color color) {
        if (this.imageWriter == null) {
            throw new MissingResourceException("Field is empty", "Camera", "ImageWriter");
        }
        int xPix = imageWriter.getNx();
        int yPix = imageWriter.getNy();
        for (int i = 0; i < xPix; i++) {
            for (int j = 0; j < yPix; j++) {
                if (i % interval == 0 || j % interval == 0) {
                    imageWriter.writePixel(i, j, color);
                }
            }
        }
    }

    /**
     * Writes the rendered image to the output file.
     *
     * @throws MissingResourceException if the ImageWriter field is empty
     */
    public void writeToImage() {
        if (this.imageWriter == null) {
            throw new MissingResourceException("Field is empty", "Camera", "ImageWriter");
        }
        imageWriter.writeToImage();
    }

    /**
     * Casts a ray through the specified pixel and returns the color of the intersected object.
     *
     * @param pixX the x-coordinate of the pixel
     * @param pixY the y-coordinate of the pixel
     * @return the color of the intersected object
     */
    private Color castRay(int pixX, int pixY) {
        Ray ray = constructRayThroughPixel(imageWriter.getNx(), imageWriter.getNx(), pixX, pixY);
        return rayTracerBase.traceRay(ray);
    }

    private Color castRays(int pixX, int pixY) {
        if(this.numOfSamples<2){
            return castRay(pixX,pixY);
        }
        LinkedList<Ray> rays = constructRaysThroughPixel(imageWriter.getNx(), imageWriter.getNx(), pixX, pixY, 32);
        Color color = Color.BLACK;
        for (Ray ray:
             rays) {
            color = color.add(rayTracerBase.traceRay(ray));
        }
        return color.reduce(rays.size());
    }


    /**
     * Rotates the camera to the right by the specified angle in radians.
     *
     * @param rad the angle in radians
     * @return a new Camera object after the rotation
     */
    public Camera rotateRight(double rad) {
        Vector rotVup = vUp.rotate(rad, vRight);
        return new Camera(place, vTo, rotVup);
    }


    /**
     * Rotates the camera to the left by the specified angle in radians.
     *
     * @param rad the angle in radians
     * @return a new Camera object after the rotation
     */
    public Camera rotateLeft(double rad) {
        Vector rotVup = vUp.rotate(rad, vRight.scale(-1));
        return new Camera(place, vTo, rotVup);
    }
    public Point findPixelCenter(int nX, int nY, int j, int i) {
        Point Pc = place.add(vTo.scale(distance)); //the center of the view plane
        double Ry = height / nY;
        double Rx = width / nX;
        double yi = -(i - (nY - 1) / 2d) * Ry; //the y coordinate of the pixel
        double xj = (j - (nX - 1) / 2d) * Rx; //the x coordinate of the pixel
        Point Pij = Pc; //the point on the view plane
        if (!isZero(xj))
            //the point on the view plane with the x coordinate of the pixel added to it
            Pij = Pij.add(vRight.scale(xj));
        if (!isZero(yi))
            //the point on the view plane with the y coordinate of the pixel added to it
            // (the minus is because the y-axis is upside down)
            Pij = Pij.add(vUp.scale(yi));

        return Pij; //the ray from the camera to the point on the view plane
    }

    public List<Point> cornersPixel(Point Pij,double height,double width){

        Vector vRight = this.vRight.scale(width/2);
        Vector vUp = this.vUp.scale(height/2);

        Point p1 = Pij.subtract(vRight.scale(-width/2)).add(vUp.scale(-height/2));
        Point p2 = Pij.add(vRight.scale(width/2)).add(vUp.scale(-height/2));
        Point p3 = Pij.add(vRight.scale(width/2)).subtract(vUp.scale(-height/2));
        Point p4 = Pij.subtract(vRight.scale(-width/2)).subtract(vUp.scale(-height/2));


        List<Point> corners = new ArrayList<>();
        corners.add(p1);
        corners.add(p2);
        corners.add(p3);
        corners.add(p4);

        return corners;
    }
    public Color recursiveCastRays(int pixX,int pixY){
        if(this.numOfSamples<2){
            return castRay(pixX,pixY);
        }
        List<Point>corners= cornersPixel(findPixelCenter(imageWriter.getNx(),imageWriter.getNy(),pixX,pixY),height,width);
        return recCastRayHelper(corners.get(0),corners.get(1),corners.get(2),corners.get(3),1);
    }

    public Color recCastRayHelper(Point p1,Point p2,Point p3,Point p4,int level){
        Color pixelColor = rayTracerBase.traceRay(new Ray(place,p1.subtract(place)));

        if (pixelColor.equals(rayTracerBase.traceRay(new Ray(place,p2.subtract(place))))&&
                pixelColor.equals(rayTracerBase.traceRay(new Ray(place,p3.subtract(place))))&&
                pixelColor.equals(rayTracerBase.traceRay(new Ray(place,p4.subtract(place))))){
            return pixelColor;
        }

        double centerX=p2.subtract(p1).length()/2;
        double centerY=p2.subtract(p3).length()/2;
        Point center = p1.add(vRight.scale(centerX)).add(vUp.scale(-centerY));

        if (level >= this.numOfSamples){
            Ray ray = new Ray(place,center.subtract(place));
            return rayTracerBase.traceRay(ray);
       }

        Point p12 = p1.add(vRight.scale(centerX));
        Point p23 = p2.subtract(vUp.scale(centerY));
        Point p34 = p3.subtract(vRight.scale(centerX));
        Point p41 = p4.add(vUp.scale(centerY));

        Color c1 = recCastRayHelper(p1,p12,center,p41,level*2);
        Color c2 = recCastRayHelper(p12,p2,p23,center,level*2);
        Color c3 = recCastRayHelper(center,p23,p3,p34,level*2);
        Color c4 = recCastRayHelper(p41,center,p34,p4,level*2);

        Color color = Color.BLACK;
        color = color.add(c1).add(c2).add(c3).add(c4);
        return color.reduce(4);

    }

}

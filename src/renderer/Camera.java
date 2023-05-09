package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.isZero;

public class Camera {
    private Point place;
    private Vector vUp;
    private Vector vTo;
    private Vector vRight;
    private double height;
    private double width;
    private double distance;

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

}

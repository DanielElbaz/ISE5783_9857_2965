package renderer;

import primitives.Point;
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

    public Camera(Point place, Vector vUp, Vector vTo) {
        if(!isZero(vUp.dotProduct(vTo)))
            throw new IllegalArgumentException("The vectors are not orthogonal");
        this.place = place;
        this.vUp = vUp.normalize();
        this.vTo = vTo.normalize();
        this.vRight= vTo.crossProduct(vUp).normalize();
    }


}

package primitives;
import java.util.Objects;

public class Ray {
    final private Point p0;
    final private Vector dir;

    /**
     *
     * @param p0;
     * @param dir;
     * creates a new ray from a point and
     * the direction of the normalized vector
     */
    public Ray(Point p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir.normalize();
    }

    public Point getP0() {
        return p0;
    }

    public Vector getDir() {
        return dir;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ray ray)) return false;
        return p0.equals(ray.p0) && dir.equals(ray.dir);
    }

    @Override
    public int hashCode() {
        return Objects.hash(p0, dir);
    }

    @Override
    public String toString() {
        return "Ray{" +
                "p0=" + p0 +
                ", dir=" + dir +
                '}';
    }
    public Point getPoint(double t){
        return p0.add(dir.scale(t));
    }
}


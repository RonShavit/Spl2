package bgu.spl.mics;

/**
 * represents a Single point in space
 */
public class CloudPoint {
    public double getY() {
        return y;
    }

    public double getX() {
        return x;
    }

    double x;
    double y;

    public CloudPoint(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "X: "+x+ " Y:"+y;
    }
}

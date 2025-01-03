package bgu.spl.mics;

/**
 * A position of the robot at time {@code TIME}
 */
public class Pose {
    public float getYaw() {
        return yaw;
    }

    public float getY() {
        return y;
    }

    public float getX() {
        return x;
    }

    private float x;
    private float y;
    private float yaw;

    public int getTIME() {
        return TIME;
    }

    private final int TIME;

    public Pose(float x, float y, float yaw, int time)
    {
        this.x = x;
        this.y = y;
        this.yaw = yaw;
        this.TIME = time;
    }

}

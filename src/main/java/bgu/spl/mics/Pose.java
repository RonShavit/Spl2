package bgu.spl.mics;

public class Pose {
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

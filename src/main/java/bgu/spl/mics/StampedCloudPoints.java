package bgu.spl.mics;
import  java.util.concurrent.ConcurrentLinkedQueue;
public class StampedCloudPoints {
    private String id;
    private final int Time;
    private ConcurrentLinkedQueue<CloudPoint> cloudPoints;

    public StampedCloudPoints(int Time, String id)
    {
        this.id = id;
        this.Time = Time;
        cloudPoints = new ConcurrentLinkedQueue<>();
    }



}

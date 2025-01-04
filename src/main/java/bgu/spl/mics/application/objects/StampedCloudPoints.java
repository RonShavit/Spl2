package bgu.spl.mics.application.objects;
import  java.util.concurrent.ConcurrentLinkedQueue;

/**
 * a list of coordinates for object {@code id} at time {@code Time}
 */
public class StampedCloudPoints {
    private String id;
    private final int Time;
    private ConcurrentLinkedQueue<ConcurrentLinkedQueue<Double>> cloudPoints;

    public StampedCloudPoints(int Time, String id,ConcurrentLinkedQueue<ConcurrentLinkedQueue<Double>> cloudPoints )
    {
        this.id = id;
        this.Time = Time;
        this.cloudPoints = cloudPoints;
    }




}

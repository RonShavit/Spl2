package bgu.spl.mics;
import java.awt.geom.CubicCurve2D;
import  java.util.concurrent.ConcurrentLinkedQueue;
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

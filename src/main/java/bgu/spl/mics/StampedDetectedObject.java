package bgu.spl.mics;
import  java.util.concurrent.ConcurrentLinkedQueue;

public class StampedDetectedObject {
    final private int Time;
    ConcurrentLinkedQueue<DetectedObject> detectedObjects;

    public StampedDetectedObject(int time, ConcurrentLinkedQueue<DetectedObject> detectedObjects)

    {
        this.Time = time;
        this.detectedObjects = detectedObjects;
    }

    public int getTime()
    {
        return Time;
    }
}

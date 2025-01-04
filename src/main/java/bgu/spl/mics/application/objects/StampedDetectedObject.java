package bgu.spl.mics.application.objects;

import  java.util.concurrent.ConcurrentLinkedQueue;

/**
 * a list of {@link DetectedObject}s detected at time {@code Time}
 */
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

    public ConcurrentLinkedQueue<DetectedObject> getDetectedObjects() {
        return detectedObjects;
    }
}

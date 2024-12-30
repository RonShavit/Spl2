package bgu.spl.mics;

import java.util.concurrent.ConcurrentLinkedQueue;

public class LidarTrackerWorker {
    final private int id;
    final private int frequency;
    Status status;
    ConcurrentLinkedQueue<TrackedObject> lastTrackedObjects;

    public LidarTrackerWorker (int id, int frequency)
    {
        this.id = id;
        this.frequency = frequency;
    }

    enum Status
    {
        Up,
        Down,
        Error;
    }
}

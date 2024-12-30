package bgu.spl.mics;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TrackedObject {
    String id;
    int time;
    String description;
    ConcurrentLinkedQueue<CloudPoint> coordinates;

    public TrackedObject(String id, int time, String description)
    {
        this.id = id;
        this.time = time;
        this.description = description;
        this.coordinates = new ConcurrentLinkedQueue<>();
    }

    public TrackedObject(String id, int time, String description, ConcurrentLinkedQueue<CloudPoint> coordinates)
    {
        this.id = id;
        this.time = time;
        this.description = description;
        this.coordinates = coordinates;
    }

    public ConcurrentLinkedQueue<CloudPoint> getCoordinates()
    {
        return coordinates;
    }

}

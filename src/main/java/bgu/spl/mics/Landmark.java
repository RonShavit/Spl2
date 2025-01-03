package bgu.spl.mics;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * represents a landmark on the map
 */
public class Landmark {
    final private String id;
    private String description;
    private ConcurrentLinkedQueue<CloudPoint> coordinates;

    public String getId() {
        return id;
    }

    public Landmark(String id, String description)
    {
        this.id = id;
        this.description = description;
        this.coordinates = new ConcurrentLinkedQueue<>();
    }

    public void addCloudPoint(CloudPoint point)
    {
        coordinates.add(point);
        averagePosition();
    }

    /**
     * Averages the position of all the points in the Landmark
     */
    public void averagePosition()
    {
        int counter = 0;
        double sumX = 0;
        double sumY = 0;
        for (CloudPoint point:coordinates)
        {
            sumX+=point.getX();
            sumY+=point.getY();
            counter++;
            coordinates.remove(point);
        }
        coordinates.add(new CloudPoint(sumX/counter,sumY/counter));

    }
}

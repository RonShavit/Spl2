package bgu.spl.mics.application.objects;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * represents a landmark on the map
 */
public class LandMark {
    final private String id;
    private String description;
    private ConcurrentLinkedQueue<CloudPoint> coordinates;

    public String getId() {
        return id;
    }

    public LandMark(String id, String description)
    {
        this.id = id;
        this.description = description;
        this.coordinates = new ConcurrentLinkedQueue<>();
    }

    public void addCloudPoint(CloudPoint point)
    {
        coordinates.add(point);

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

    public ConcurrentLinkedQueue<CloudPoint> getCoordinates() {
        return coordinates;
    }
}

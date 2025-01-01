package bgu.spl.mics;

import java.util.concurrent.ConcurrentLinkedQueue;

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
    }

    public void avaragePoseition()
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

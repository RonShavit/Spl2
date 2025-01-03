package bgu.spl.mics;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * represents a LiDAR tracker Worker unit
 */
public class LidarTrackerWorker {
    final private int id;
    final private int frequency;
    final private String path;
    Status status;
    ConcurrentLinkedQueue<TrackedObject> lastTrackedObjects;

    public LidarTrackerWorker (int id, int frequency, String path)
    {
        this.id = id;
        this.frequency = frequency;
        this.path = path;
        this.lastTrackedObjects = new ConcurrentLinkedQueue<>();

    }


    enum Status
    {
        Up,
        Down,
        Error;
    }

    public int getId() {
        return id;
    }

    /**
     *  tracks all {@link DetectedObject}s at time {@code stampedDetectedObject.time} and adds them to the {@link LidarDataBase}
     * @param stampedDetectedObject A {@link StampedDetectedObject}
     * @return A {@link ConcurrentLinkedQueue} of the tracked objects
     */
    public ConcurrentLinkedQueue<TrackedObject> analiseStampedDetectedObjects(StampedDetectedObject stampedDetectedObject)
    {


        if (stampedDetectedObject!=null) {
            this.lastTrackedObjects = new ConcurrentLinkedQueue<>();
            try {
                FileReader reader = new FileReader(path);

                JsonArray lidarData = JsonParser.parseReader(reader).getAsJsonArray();
                int time = stampedDetectedObject.getTime();
                ConcurrentLinkedQueue<DetectedObject> detectedObjects = stampedDetectedObject.getDetectedObjects();
                for (DetectedObject detectedObject : detectedObjects) {
                    String id = detectedObject.getId();
                    ConcurrentLinkedQueue<ConcurrentLinkedQueue<Double>> cloudPoints = new ConcurrentLinkedQueue<>();
                    ConcurrentLinkedQueue<CloudPoint> cloudPointsNoZ = new ConcurrentLinkedQueue<>();
                    for (JsonElement cloudPoint : lidarData) {

                        String lidarId = ((JsonObject) cloudPoint).get("id").getAsString();
                        int lidarTime = ((JsonObject) cloudPoint).get("time").getAsInt();
                        if (id.compareTo(lidarId) == 0 && lidarTime == time) {
                            JsonArray lidarCoordinates = ((JsonObject) cloudPoint).get("cloudPoints").getAsJsonArray();
                            for (JsonElement coordinate : lidarCoordinates) {
                                double x = ((JsonArray) coordinate).get(0).getAsInt();
                                double y = ((JsonArray) coordinate).get(1).getAsInt();
                                double z = ((JsonArray) coordinate).get(2).getAsInt();
                                ConcurrentLinkedQueue<Double> pointAsList = new ConcurrentLinkedQueue<>();
                                pointAsList.add(x);
                                pointAsList.add(y);
                                pointAsList.add(z);
                                cloudPoints.add(pointAsList);
                                CloudPoint point = new CloudPoint(x, y);
                                cloudPointsNoZ.add(point);

                            }

                        }
                    }
                    StampedCloudPoints stampedCloudPoints = new StampedCloudPoints(time, id, cloudPoints);
                    LidarDataBase.getInstance().addStampedCloudPoints(stampedCloudPoints);
                    lastTrackedObjects.add(new TrackedObject(id, time, detectedObject.getDescription()));

                }


            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return lastTrackedObjects;

    }
}

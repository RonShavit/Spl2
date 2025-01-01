package bgu.spl.mics;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import java.util.concurrent.ConcurrentLinkedQueue;

public class Camera {
    final private int id;
    final private int frequency;
    private Status status;
    ConcurrentLinkedQueue<StampedDetectedObject> stampedDetectedObjectsQueue;
    private final String path;

    enum Status
    {
        Up,
        Down,
        Error;
    }

    public int getId() {
        return id;
    }

    public Camera(int id, int frequency, String path)
    {
        this.id = id;
        this.frequency = frequency;
        this.path = path;
        stampedDetectedObjectsQueue = new ConcurrentLinkedQueue<>();
    }

    public StampedDetectedObject getCameraData(int tick)
    {
        StampedDetectedObject stampedDetectedObject = null;
        try {
            FileReader reader = new FileReader(path);
            String camName = "camera"+id;
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            JsonArray objectsArray = jsonObject.getAsJsonArray(camName);
            for (JsonElement jsonElement:objectsArray)
            {
                int time = ((JsonObject)jsonElement).get("time").getAsInt();
                if (time == tick) {
                    JsonArray objectsInTimeArray = ((JsonObject) jsonElement).getAsJsonArray("detectedObjects");
                    ConcurrentLinkedQueue<DetectedObject> detectedObjects = new ConcurrentLinkedQueue<>();
                    for (JsonElement detectedObject : objectsInTimeArray) {
                        String id = ((JsonObject)detectedObject).get("id").getAsString();
                        String description = ((JsonObject)detectedObject).get("description").getAsString();
                        detectedObjects.add(new DetectedObject(id,description));
                    }
                    stampedDetectedObject = new StampedDetectedObject(time,detectedObjects);
                }
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return stampedDetectedObject;
    }
}

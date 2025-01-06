package bgu.spl.mics.application.objects;
import bgu.spl.mics.*;
import bgu.spl.mics.application.messages.CrashedBroadcast;
import bgu.spl.mics.application.services.CameraService;
import com.google.gson.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Represents a Camera
 *
 */
public class Camera {
    final private int id;
    final private int frequency;
    private STATUS status;
    ConcurrentLinkedQueue<StampedDetectedObject> stampedDetectedObjectsQueue;
    private final String path;
    private ConcurrentLinkedQueue<DetectedObject> lastFrame;



    /**
     *
     * @return the id of the camera
     */
    public int getId() {
        return id;
    }


    public Camera(int id, int frequency, String path)
    {
        this.id = id;
        this.frequency = frequency;
        this.path = path;
        this.lastFrame = new ConcurrentLinkedQueue<>();
        stampedDetectedObjectsQueue = new ConcurrentLinkedQueue<>();
        status = STATUS.UP;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }

    /**
     *
     * @param tick the tick that we want the objects detected at
     * @return all objects found at {@code tick} tick
     */
    public StampedDetectedObject getCameraData(int tick, CameraService cameraService)
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
                if (time+frequency == tick) {
                    JsonArray objectsInTimeArray = ((JsonObject) jsonElement).getAsJsonArray("detectedObjects");
                    ConcurrentLinkedQueue<DetectedObject> detectedObjects = new ConcurrentLinkedQueue<>();
                    for (JsonElement detectedObject : objectsInTimeArray) {
                        String id = ((JsonObject)detectedObject).get("id").getAsString();
                        String description = ((JsonObject)detectedObject).get("description").getAsString();
                        if (id.compareTo("ERROR")==0)
                        {

                            try
                            {
                                OutputFileManager.getInstance().writeError(description,this);
                            }
                            catch (IOException e)
                            {

                            }
                            cameraService.sendBroadCast(new CrashedBroadcast());
                            cameraService.terminatMe();
                            status = STATUS.ERROR;

                        }
                        else {
                            detectedObjects.add(new DetectedObject(id, description));
                            StatisticalFolder.getInstance().increaseDetectedObjects();
                        }
                    }
                    if (detectedObjects!=null)
                        lastFrame = detectedObjects;
                    stampedDetectedObject = new StampedDetectedObject(time,detectedObjects);
                }
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return stampedDetectedObject;
    }

    public ConcurrentLinkedQueue<DetectedObject> getLastFrame() {
        return lastFrame;
    }

    public int getFrequency() {
        return frequency;
    }
}

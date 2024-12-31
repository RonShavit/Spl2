package bgu.spl.mics;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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

    public Camera(int id, int frequency, String path)
    {
        this.id = id;
        this.frequency = frequency;
        this.path = path;
    }

    public StampedDetectedObject getCameraData(int tick)
    {
        Gson gson = new Gson();
        try {
            // Reading the JSON file into a Map
            FileReader reader = new FileReader("data.json");
            Type type = new TypeToken<List<Camera>>(){}.getType();
            List<Camera> camera1Data = gson.fromJson(reader, type);


        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

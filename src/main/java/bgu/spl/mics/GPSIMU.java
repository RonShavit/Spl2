package bgu.spl.mics;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class GPSIMU {
    AtomicInteger currentTick;
    Status status;
    ConcurrentLinkedQueue<Pose> posesList;

    public GPSIMU(String path)
    {
        currentTick = new AtomicInteger(0);
        posesList = getPosesListFromJson(path);
    }

    public void increaseTick()
    {
        currentTick.compareAndSet(currentTick.intValue(),currentTick.intValue()+1);
    }

    public Pose getPoseInTick()
    {
        if (posesList != null) {
            for (Pose pose : posesList) {
                if (pose.getTIME() == currentTick.intValue()) {
                    return pose;
                }
            }
        }
        return null;
    }

    enum Status
    {
        Up,
        Down,
        Error;
    }

    private ConcurrentLinkedQueue<Pose> getPosesListFromJson(String path)
    {
        ConcurrentLinkedQueue<Pose> poses = new ConcurrentLinkedQueue<>();
        try (FileReader reader = new FileReader(path))
        {
            JsonArray jsonPosesArray = JsonParser.parseReader(reader).getAsJsonArray();
            for (JsonElement jsonPose: jsonPosesArray)
            {
                poses.add(new Pose(((JsonObject)jsonPose).get("x").getAsFloat(),((JsonObject)jsonPose).get("y").getAsFloat(),((JsonObject)jsonPose).get("yaw").getAsFloat(),((JsonObject)jsonPose).get("time").getAsInt()));
            }
        }
        catch (IOException e)
        {

        }
        return poses;
    }
}

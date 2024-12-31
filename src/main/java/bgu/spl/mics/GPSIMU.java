package bgu.spl.mics;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class GPSIMU {
    AtomicInteger currentTick;
    Status status;
    ConcurrentLinkedQueue<Pose> posesList;

    public GPSIMU()
    {
        currentTick = new AtomicInteger(0);
        posesList = null;// TODO : implement json through GSON
    }

    public void increaseTick()
    {
        currentTick.compareAndSet(currentTick.intValue(),currentTick.intValue()+1);
    }

    public Pose getPoseInTick()
    {
        for (Pose pose:posesList)
        {
            if (pose.getTIME()==currentTick.intValue())
            {
                return pose;
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
}

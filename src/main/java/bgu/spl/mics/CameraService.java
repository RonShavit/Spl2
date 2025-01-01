package bgu.spl.mics;

import java.awt.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class CameraService extends MicroService{
    Camera cam;
    public CameraService(String name, Camera cam)
    {
        super(name);
        this.cam = cam;
    }

    public void initialize()
    {

        subscribeBroadcast(TickBroadcast.class,new TickCallback(this));
        subscribeBroadcast(TerminatedBroadcast.class,new TerminatedCallback());
        subscribeBroadcast(CrashedBroadcast.class,new CrashedCallback(this));
    }


    public void updateTick()
    {
        super.updateTick();
        StampedDetectedObject stampedDetectedObjects =  cam.getCameraData(this.getTick().intValue());
        DetectObjectEvent detectObjectEvent = new DetectObjectEvent(stampedDetectedObjects);
        sendEvent(detectObjectEvent);
    }
}
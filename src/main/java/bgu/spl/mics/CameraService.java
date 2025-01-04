package bgu.spl.mics;

import java.awt.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Handles communication between a camera and the message bus (thus with the rest of the system)
 */
public class CameraService extends MicroService{
    Camera cam;
    public CameraService(Camera cam)
    {
        super("camera"+cam.getId());
        this.cam = cam;
    }

    public void initialize()
    {

        subscribeBroadcast(TickBroadcast.class,new TickCallback(this));
        subscribeBroadcast(TerminatedBroadcast.class,new TerminatedCallback());
        subscribeBroadcast(CrashedBroadcast.class,new CrashedCallback(this));
    }

    /**
     * Sends all detected objects detected at time {@code this.tick}
     */
    public void updateTick()
    {
        super.updateTick();
        StampedDetectedObject stampedDetectedObjects =  cam.getCameraData(this.getTick().intValue(), this);
        DetectObjectEvent detectObjectEvent = new DetectObjectEvent(stampedDetectedObjects);
        sendEvent(detectObjectEvent);
    }
}
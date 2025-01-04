package bgu.spl.mics.application.services;

import bgu.spl.mics.*;
import bgu.spl.mics.application.callbacks.CrashedCallback;
import bgu.spl.mics.application.callbacks.TerminatedCallback;
import bgu.spl.mics.application.callbacks.TickCallback;
import bgu.spl.mics.application.messages.CrashedBroadcast;
import bgu.spl.mics.application.messages.DetectObjectEvent;
import bgu.spl.mics.application.messages.TerminatedBroadcast;
import bgu.spl.mics.application.messages.TickBroadcast;
import bgu.spl.mics.application.objects.Camera;
import bgu.spl.mics.application.objects.STATUS;
import bgu.spl.mics.application.objects.StampedDetectedObject;

/**
 * Handles communication between a camera and the message bus (thus with the rest of the system)
 */
public class CameraService extends MicroService {
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


    public void terminateCam()
    {
        cam.setStatus(STATUS.DOWN);
    }
}
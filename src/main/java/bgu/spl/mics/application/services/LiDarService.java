package bgu.spl.mics.application.services;

import bgu.spl.mics.*;
import bgu.spl.mics.application.callbacks.CrashedCallback;
import bgu.spl.mics.application.callbacks.DetectedObjectCallback;
import bgu.spl.mics.application.callbacks.TerminatedCallback;
import bgu.spl.mics.application.callbacks.TickCallback;
import bgu.spl.mics.application.messages.*;
import bgu.spl.mics.application.objects.LiDarWorkerTracker;
import bgu.spl.mics.application.objects.STATUS;
import bgu.spl.mics.application.objects.StampedDetectedObject;
import bgu.spl.mics.application.objects.TrackedObject;

import java.util.concurrent.ConcurrentLinkedQueue;

public class LiDarService extends MicroService {
    LiDarWorkerTracker lidarTrackerWorker;
    public LiDarService(String name)
    {
        super(name);
    }

    public LiDarService(LiDarWorkerTracker lidarTrackerWorker) {
        super("LiDar"+ lidarTrackerWorker.getId());
        this.lidarTrackerWorker = lidarTrackerWorker;
    }

    public void initialize()
    {


        subscribeBroadcast(TickBroadcast.class,new TickCallback(this));
        subscribeBroadcast(TerminatedBroadcast.class,new TerminatedCallback());
        subscribeBroadcast(CrashedBroadcast.class,new CrashedCallback(this));

        subscribeEvent(DetectObjectEvent.class,new DetectedObjectCallback() );
    }

    /**
     * @PRE msg.getClass == DetectedObjectEvent.class
     * @param msg a message from the messageBus
     */
    @Override
    public void DetectedObjectMessage(Message msg) {
        StampedDetectedObject stampedDetectedObject = ((DetectObjectEvent)msg).getStampedDetectedObjects(); // Has to be safe casting
        ConcurrentLinkedQueue<TrackedObject> trackedObjects = lidarTrackerWorker.analiseStampedDetectedObjects(stampedDetectedObject, this);
        TrackedObjectEvent trackedObjectEvent = new TrackedObjectEvent(trackedObjects);
        sendEvent(trackedObjectEvent);
        ((Event<Boolean>) msg).resolveFuture(true);
    }

    public void terminateLiDar()
    {
        this.lidarTrackerWorker.setStatus(STATUS.DOWN);
    }
}

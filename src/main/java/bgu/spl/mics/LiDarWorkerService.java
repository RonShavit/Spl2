package bgu.spl.mics;

import java.util.concurrent.ConcurrentLinkedQueue;

public class LiDarWorkerService extends MicroService{
    LidarTrackerWorker lidarTrackerWorker;
    public LiDarWorkerService(String name)
    {
        super(name);
    }

    public LiDarWorkerService(String name, LidarTrackerWorker lidarTrackerWorker) {
        super(name);
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
        ConcurrentLinkedQueue<TrackedObject> trackedObjects = lidarTrackerWorker.analiseStampedDetectedObjects(stampedDetectedObject);
        TrackedObjectEvent trackedObjectEvent = new TrackedObjectEvent(trackedObjects);
        sendEvent(trackedObjectEvent);
        ((Event<Boolean>) msg).resolveFuture(true);
    }
}

package bgu.spl.mics;

import java.util.concurrent.ConcurrentLinkedQueue;

public class FusionSlamService extends MicroService{
    private FusionSlam fusionSlam;

    public FusionSlamService(String name)
    {
        super(name);
        fusionSlam = FusionSlam.getInstance();
    }

    public void initialize()
    {
        subscribeBroadcast(TickBroadcast.class,new TickCallback(this));
        subscribeBroadcast(TerminatedBroadcast.class,new TerminatedCallback());
        subscribeBroadcast(CrashedBroadcast.class,new CrashedCallback(this));

        subscribeEvent(TrackedObjectEvent.class,new TrackedObjectCallback(this));
        subscribeEvent(PoseEvent.class, new PoseCallback(this));
    }

    @Override
    public void TrackedObjectMessage(Message msg) {
        ConcurrentLinkedQueue<TrackedObject> trackedObjects = ((TrackedObjectEvent)msg).getTrackedObjectsList();
        fusionSlam.normalizeTrackedObjects(trackedObjects);
        ((TrackedObjectEvent) msg).resolveFuture(true);

    }

    @Override
    public void PoseMessage(Message msg) {
        Pose pose = ((PoseEvent)msg).getPose();
        fusionSlam.addPose(pose);
        ((PoseEvent) msg).resolveFuture(true);
    }
}

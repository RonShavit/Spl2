package bgu.spl.mics;

import java.util.concurrent.ConcurrentLinkedQueue;

public class FusionSlamService extends MicroService{
    private FusionSlam fusionSlam;

    public FusionSlamService(FusionSlam fusionSlam)
    {
        super("FutionSlamService");
        this.fusionSlam = fusionSlam;
    }

    public void initialize()
    {

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

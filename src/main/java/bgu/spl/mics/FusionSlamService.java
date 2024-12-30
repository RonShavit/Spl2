package bgu.spl.mics;

public class FusionSlamService extends MicroService{

    public FusionSlamService(String name)
    {
        super(name);
    }

    public void initialize()
    {
        // TODO : subscribe to tick, terminated, crashed broadcasts and Tracked, pose events
        subscribeBroadcast(TickBroadcast.class,new TickCallback(this));
        subscribeBroadcast(TerminatedBroadcast.class,new TerminatedCallback());
        subscribeBroadcast(CrashedBroadcast.class,new CrashedCallback(this));

        subscribeEvent(TrackedObjectEvent.class,new TrackedObjectCallback(this));
        subscribeEvent(PoseEvent.class, new PoseCallback(this));
    }

    public void processTrackedObjects()
    {
        // TODO : implement
    }



}

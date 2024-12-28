package bgu.spl.mics;

public class FusionSlamService extends MicroService{

    public FusionSlamService(String name)
    {
        super(name);
    }

    public void initialize()
    {
        // TODO : subscribe to tick, terminated, crashed broadcasts and Tracked, pose events
        subscribeBroadcast(TickBroadcast.class,);
        subscribeBroadcast(TerminatedBroadcast.class,);
        subscribeBroadcast(CrashedBroadcast.class,);

        subscribeEvent(TrackedObjectEvent.class,);
        subscribeEvent(PoseEvent.class, );
    }
}

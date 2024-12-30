package bgu.spl.mics;

public class LiDarWorkerService extends MicroService{
    public LiDarWorkerService(String name)
    {
        super(name);
    }

    public void initialize()
    {
        // TODO : subscribe to tick, terminated, crash broadcast and detect event

        subscribeBroadcast(TickBroadcast.class,new TickCallback(this));
        subscribeBroadcast(TerminatedBroadcast.class,new TerminatedCallback());
        subscribeBroadcast(CrashedBroadcast.class,new CrashedCallback(this));

        subscribeEvent(DetectObjectEvent.class,new DetectedObjectCallback() );
    }
}

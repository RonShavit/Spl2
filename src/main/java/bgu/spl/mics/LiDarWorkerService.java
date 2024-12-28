package bgu.spl.mics;

public class LiDarWorkerService extends MicroService{
    public LiDarWorkerService(String name)
    {
        super(name);
    }

    public void initialize()
    {
        // TODO : subscribe to tick, terminated, crash broadcast and detect event

        subscribeBroadcast(TickBroadcast.class,);
        subscribeBroadcast(TerminatedBroadcast.class,);
        subscribeBroadcast(CrashedBroadcast.class,);

        subscribeEvent(DetectObjectEvent.class, );
    }
}

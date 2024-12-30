package bgu.spl.mics;

public class PoseService extends MicroService{
    public PoseService(String name)
    {
        super(name);
    }

    public void initialize()
    {
        // TODO : subscribe to Tick, Crashed broadcasts
        subscribeBroadcast(TickBroadcast.class,new TickCallback(this));
        subscribeBroadcast(CrashedBroadcast.class,new CrashedCallback(this));
    }
}

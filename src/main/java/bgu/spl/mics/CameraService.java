package bgu.spl.mics;

public class CameraService extends MicroService{

    public CameraService(String name)
    {
        super(name);
    }

    public void initialize()
    {
    // TODO : subscribe to tick, terminate and crash broadcasts
        subscribeBroadcast(TickBroadcast.class,new TickCallback(this));
        subscribeBroadcast(TerminatedBroadcast.class,new TerminatedCallback());
        subscribeBroadcast(CrashedBroadcast.class,new CrashedCallback(this));
    }
}

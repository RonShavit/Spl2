package bgu.spl.mics;

public class CameraService extends MicroService{

    public CameraService(String name)
    {
        super(name);
    }

    public void initialize()
    {
    // TODO : subscribe to tick, terminate and crash broadcasts
        subscribeBroadcast(TickBroadcast.class,);
        subscribeBroadcast(TerminatedBroadcast.class,);
        subscribeBroadcast(CrashedBroadcast.class,new CrashedCallback(this));
    }
}

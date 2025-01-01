package bgu.spl.mics;

public class PoseService extends MicroService{
    private GPSIMU gpsimu;
    public PoseService(String name)
    {
        super(name);
        gpsimu = new GPSIMU();
    }

    public void initialize()
    {
        subscribeBroadcast(TickBroadcast.class,new TickCallback(this));
        subscribeBroadcast(CrashedBroadcast.class,new CrashedCallback(this));
        subscribeBroadcast(TerminatedBroadcast.class,new TerminatedCallback());
    }

    @Override
    public void updateTick() {
        super.updateTick();
        gpsimu.increaseTick();
        Pose pose = gpsimu.getPoseInTick();
        PoseEvent poseEvent = new PoseEvent(pose);
        sendEvent(poseEvent);
    }
}

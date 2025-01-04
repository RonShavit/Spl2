package bgu.spl.mics.application.services;

import bgu.spl.mics.*;
import bgu.spl.mics.application.callbacks.CrashedCallback;
import bgu.spl.mics.application.callbacks.TerminatedCallback;
import bgu.spl.mics.application.callbacks.TickCallback;
import bgu.spl.mics.application.messages.CrashedBroadcast;
import bgu.spl.mics.application.messages.PoseEvent;
import bgu.spl.mics.application.messages.TerminatedBroadcast;
import bgu.spl.mics.application.messages.TickBroadcast;
import bgu.spl.mics.application.objects.GPSIMU;
import bgu.spl.mics.application.objects.Pose;
import bgu.spl.mics.application.objects.STATUS;

public class PoseService extends MicroService {
    private GPSIMU gpsimu;
    public PoseService(GPSIMU gpsimu)
    {
        super("PoseService");
        this.gpsimu = gpsimu;
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

    public void terminateGPSIMU()
    {
        this.gpsimu.setStatus(STATUS.DOWN);
    }
}

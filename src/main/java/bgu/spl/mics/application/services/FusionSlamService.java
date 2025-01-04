package bgu.spl.mics.application.services;

import bgu.spl.mics.*;
import bgu.spl.mics.application.callbacks.CrashedCallback;
import bgu.spl.mics.application.callbacks.PoseCallback;
import bgu.spl.mics.application.callbacks.TerminatedCallback;
import bgu.spl.mics.application.callbacks.TrackedObjectCallback;
import bgu.spl.mics.application.messages.CrashedBroadcast;
import bgu.spl.mics.application.messages.PoseEvent;
import bgu.spl.mics.application.messages.TerminatedBroadcast;
import bgu.spl.mics.application.messages.TrackedObjectEvent;
import bgu.spl.mics.application.objects.FusionSlam;
import bgu.spl.mics.application.objects.Pose;
import bgu.spl.mics.application.objects.TrackedObject;

import java.util.concurrent.ConcurrentLinkedQueue;

public class FusionSlamService extends MicroService {
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

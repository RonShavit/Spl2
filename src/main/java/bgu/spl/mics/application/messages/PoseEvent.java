package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;
import bgu.spl.mics.Future;
import bgu.spl.mics.application.objects.Pose;

public class PoseEvent implements Event<Boolean> {
    Future<Boolean> future;

    public Pose getPose() {
        return pose;
    }

    Pose pose;

    public PoseEvent(Pose pose)
    {
        future = new Future<>();
        this.pose = pose;
    }

    public void resolveFuture(Boolean b)
    {
        future.resolve(b);
    }

    public Future<Boolean> getFuture()
    {
        return future;
    }
}

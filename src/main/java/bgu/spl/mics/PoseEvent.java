package bgu.spl.mics;

public class PoseEvent implements Event<Boolean> {
    Future<Boolean> future;
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

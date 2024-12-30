package bgu.spl.mics;

public class DetectObjectEvent implements Event<Boolean>{
    private Future<Boolean> future;
    private StampedDetectedObject stampedDetectedObjects;



    public DetectObjectEvent(StampedDetectedObject stampedDetectedObjects)
    {
        this.stampedDetectedObjects = stampedDetectedObjects;
        future = new Future<>();
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

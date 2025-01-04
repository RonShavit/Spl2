package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;
import bgu.spl.mics.Future;
import bgu.spl.mics.application.objects.StampedDetectedObject;

/**
 * sent whenever an object has been detected
 */
public class DetectObjectEvent implements Event<Boolean> {
    private Future<Boolean> future;

    public StampedDetectedObject getStampedDetectedObjects() {
        return stampedDetectedObjects;
    }

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

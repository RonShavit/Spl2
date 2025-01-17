package bgu.spl.mics.application.messages;
import bgu.spl.mics.Event;
import bgu.spl.mics.Future;
import bgu.spl.mics.application.objects.TrackedObject;

import java.util.concurrent.ConcurrentLinkedQueue;

public class TrackedObjectEvent implements Event<Boolean> {
    public ConcurrentLinkedQueue<TrackedObject> getTrackedObjectsList() {
        return trackedObjectsList;
    }

    ConcurrentLinkedQueue<TrackedObject> trackedObjectsList;
    Future<Boolean> future;

    public TrackedObjectEvent()
    {
        this.trackedObjectsList = new ConcurrentLinkedQueue<>();
        future = new Future<>();
    }

    public TrackedObjectEvent(ConcurrentLinkedQueue trackedObjectsList)
    {
        this.trackedObjectsList =trackedObjectsList;
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

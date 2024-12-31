package bgu.spl.mics;
import java.util.concurrent.ConcurrentLinkedQueue;
public class TrackedObjectCallback implements Callback<TrackedObjectEvent>{
    FusionSlamService fusionSlamService;

    public TrackedObjectCallback(FusionSlamService fusionSlamService)
    {
        this.fusionSlamService = fusionSlamService;
    }

    @Override
    public void call(TrackedObjectEvent c) {
        call();
    }

    public void call()
    {
        fusionSlamService.processTrackedObjects();
    }
}

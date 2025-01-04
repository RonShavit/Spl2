package bgu.spl.mics.application.callbacks;
import bgu.spl.mics.Callback;
import bgu.spl.mics.application.messages.TrackedObjectEvent;
import bgu.spl.mics.application.services.FusionSlamService;

public class TrackedObjectCallback implements Callback<TrackedObjectEvent> {
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
        //
    }
}

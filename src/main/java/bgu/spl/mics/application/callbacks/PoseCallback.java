package bgu.spl.mics.application.callbacks;

import bgu.spl.mics.Callback;
import bgu.spl.mics.application.messages.PoseEvent;
import bgu.spl.mics.application.services.FusionSlamService;

public class PoseCallback implements Callback<PoseEvent> {
    private FusionSlamService fusionSlamService;

    public PoseCallback(FusionSlamService fusionSlamService)
    {
        this.fusionSlamService = fusionSlamService;
    }

    @Override
    public void call() {

    }

    public void call(PoseEvent c)
    {
        call();
    }
}

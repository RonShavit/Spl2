package bgu.spl.mics;

public class PoseCallback implements Callback<PoseEvent>{
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

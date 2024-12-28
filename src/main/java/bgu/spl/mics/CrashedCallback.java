package bgu.spl.mics;

public class CrashedCallback implements Callback<CrashedBroadcast>{
    MicroService m;
    public CrashedCallback(MicroService m)
    {
        this.m =m;
    };

    public void call (CrashedBroadcast c)
    {
        m.terminate();
    }
}
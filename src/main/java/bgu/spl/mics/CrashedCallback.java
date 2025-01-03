package bgu.spl.mics;

/**
 * called when an object crashes and stops all other services
 */
public class CrashedCallback implements Callback<CrashedBroadcast>{
    MicroService m;
    public CrashedCallback(MicroService m)
    {
        this.m =m;
    };

    public void call ()
    {
        m.terminate();
    }

    public void call (CrashedBroadcast c)
    {
        call();
    }
}

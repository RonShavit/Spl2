package bgu.spl.mics.application.callbacks;

import bgu.spl.mics.Callback;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.CrashedBroadcast;

/**
 * called when an object crashes and stops all other services
 */
public class CrashedCallback implements Callback<CrashedBroadcast> {
    MicroService m;
    public CrashedCallback(MicroService m)
    {
        this.m =m;
    };

    public void call ()
    {
        m.terminatMe();
    }

    public void call (CrashedBroadcast c)
    {
        call();
    }
}

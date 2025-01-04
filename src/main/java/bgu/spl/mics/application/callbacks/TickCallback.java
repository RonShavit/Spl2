package bgu.spl.mics.application.callbacks;

import bgu.spl.mics.Callback;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.TickBroadcast;

public class TickCallback implements Callback<TickBroadcast> {
    MicroService m;
    public TickCallback(MicroService m)
    {
        this.m =m;
    }


    @Override
    public void call(TickBroadcast c) {
        call();
    }
    public void call(){m.updateTick();}
}

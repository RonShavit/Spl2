package bgu.spl.mics;

public class TickCallback implements Callback<TickBroadcast>{
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

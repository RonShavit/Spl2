package bgu.spl.mics;

import java.util.concurrent.atomic.AtomicBoolean;

public class TimeService extends MicroService {
    int clockCounter;

    final int speed;
    final int duration;

    public TimeService(int speed, int duration)
    {
        super("TimeService");
        this.clockCounter = 0;
        this.speed = speed;
        this.duration = duration;
    }

    public void initialize()
    {
        AtomicBoolean allDone = new AtomicBoolean(false);
        subscribeBroadcast(CrashedBroadcast.class, new CrashedCallback(this));
        while (clockCounter*speed<duration &&!allDone.get())
        {
            try
            {
                Thread.sleep(speed);
                clockCounter++;
                TickBroadcast tickBroadcast = new TickBroadcast();
                sendBroadcast(tickBroadcast);
                if (TerminatedCounter.getInstance().getToTerminate()<=0)
                {
                    allDone.compareAndSet(false,true);
                }
            }
            catch (Exception E)
            {}
        }
        CrashedBroadcast crashedBroadcast = new CrashedBroadcast();
        sendBroadcast(crashedBroadcast);
        StatisticalFolder.getInstance().setSystemRuntime(clockCounter);
        System.out.println("stopped at "+ clockCounter);
        terminate();
    }
}

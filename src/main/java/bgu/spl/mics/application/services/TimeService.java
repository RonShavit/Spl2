package bgu.spl.mics.application.services;

import bgu.spl.mics.*;
import bgu.spl.mics.application.callbacks.CrashedCallback;
import bgu.spl.mics.application.messages.CrashedBroadcast;
import bgu.spl.mics.application.messages.TickBroadcast;
import bgu.spl.mics.application.objects.StatisticalFolder;

import java.io.IOException;
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
                StatisticalFolder.getInstance().increaseSystemRunTime();
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
        try
        {
            OutputFileManager.getInstance().writeStatistics();
        }
        catch (IOException e){}
        CrashedBroadcast crashedBroadcast = new CrashedBroadcast();
        sendBroadcast(crashedBroadcast);
        StatisticalFolder.getInstance().setSystemRuntime(clockCounter);

        terminate();
    }
}

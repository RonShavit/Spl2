package bgu.spl.mics;

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
        subscribeBroadcast(CrashedBroadcast.class, new CrashedCallback(this));
        while (clockCounter*speed<duration)
        {
            try
            {
                Thread.sleep(speed);
                clockCounter++;
                TickBroadcast tickBroadcast = new TickBroadcast();
                sendBroadcast(tickBroadcast);
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

package bgu.spl.mics;

public class TimeService extends MicroService {
    int clockCounter;
    final int speed;
    final int duration;

    public TimeService(String name,int speed, int duration)
    {
        super(name);
        this.clockCounter = 0;
        this.speed = speed;
        this.duration = duration;
    }

    public void initialize()
    {
    }
}

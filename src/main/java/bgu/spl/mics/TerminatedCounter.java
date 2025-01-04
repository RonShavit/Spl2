package bgu.spl.mics;

import java.util.concurrent.atomic.AtomicInteger;

public class TerminatedCounter {
    private AtomicInteger ToTerminate;
    private static TerminatedCounter terminatedCounterSingleton = null;
    private static Object lock = new Integer(0);

    private TerminatedCounter()
    {
        ToTerminate = new AtomicInteger(0);
    }

    public static TerminatedCounter getInstance()
    {
        if (terminatedCounterSingleton==null)
        {
            synchronized (lock)
            {
                if (terminatedCounterSingleton==null)
                {
                    terminatedCounterSingleton = new TerminatedCounter();
                }
            }
        }
        return terminatedCounterSingleton;
    }

    public void increaseRunning()
    {
        ToTerminate.compareAndSet(ToTerminate.intValue(),ToTerminate.intValue()+1);
    }
    public void decreaseRunning()
    {
        ToTerminate.compareAndSet(ToTerminate.intValue(),ToTerminate.intValue()-1);
    }

    public int getToTerminate() {
        return ToTerminate.intValue();
    }
}

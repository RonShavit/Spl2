package bgu.spl.mics.application.objects;

import java.util.concurrent.atomic.AtomicInteger;

public class StatisticalFolder {

    private AtomicInteger systemRuntime;
    private AtomicInteger numDetectedObjects;
    private AtomicInteger numTrackedObjects;
    private AtomicInteger numLandMarks;
    private static Object lock = new Integer(0);
    public static StatisticalFolder statisticalFolderSingleton = null;
    private StatisticalFolder()
    {
        systemRuntime = new AtomicInteger(0);
        numLandMarks= new AtomicInteger(0);
        numDetectedObjects = new AtomicInteger(0);
        numTrackedObjects = new AtomicInteger(0);


    }

    public static StatisticalFolder getInstance()
    {
        if (statisticalFolderSingleton == null)
        {
            synchronized (lock)
            {
                if (statisticalFolderSingleton==null)
                    statisticalFolderSingleton = new StatisticalFolder();
            }
        }
        return statisticalFolderSingleton;
    }

    public void setSystemRuntime(int runtime)
    {
        systemRuntime.compareAndSet(systemRuntime.intValue(),runtime);
    }

    public void increaseSystemRunTime()
    {
        systemRuntime.compareAndSet(systemRuntime.intValue(),systemRuntime.intValue()+1);
    }

    public void increaseDetectedObjects()
    {
        numDetectedObjects.compareAndSet(numDetectedObjects.intValue(),numDetectedObjects.intValue()+1);
    }

    public void increaseTrackedObjects()
    {
        numTrackedObjects.compareAndSet(numTrackedObjects.intValue(),numDetectedObjects.intValue()+1);
    }

    public void increaseLandMarks()
    {
        numLandMarks.compareAndSet(numLandMarks.intValue(),numLandMarks.intValue()+1);
    }

    public AtomicInteger getSystemRuntime() {
        return systemRuntime;
    }

    public AtomicInteger getNumDetectedObjects() {
        return numDetectedObjects;
    }

    public AtomicInteger getNumTrackedObjects() {
        return numTrackedObjects;
    }

    public AtomicInteger getNumLandMarks() {
        return numLandMarks;
    }

    @Override
    public String toString() {
        return "Stats:\nruntime: "+systemRuntime+"\nDetected: "+numDetectedObjects+"\nTracked: "+numTrackedObjects+"\nLandMarks: "+numLandMarks;
    }
}

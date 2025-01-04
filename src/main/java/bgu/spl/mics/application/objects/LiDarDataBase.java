package bgu.spl.mics.application.objects;

import  java.util.concurrent.ConcurrentLinkedQueue;

/**
 * A singleton representing a database of StampedCloudPoints
 */

public class LiDarDataBase {
    ConcurrentLinkedQueue<StampedCloudPoints> stampedCloudPointsQueue;
    static private LiDarDataBase dataBaseSingleton = null;
    static private Object lock = new Integer(0);
    private LiDarDataBase()
    {
        stampedCloudPointsQueue = new ConcurrentLinkedQueue<>();
    }

    /**
     returns the instance of the singelton (create it if it doesn't exist)
     */
    public static LiDarDataBase getInstance()
    {
        if (dataBaseSingleton==null)
        {
            synchronized (lock) {
                if (dataBaseSingleton == null)
                    dataBaseSingleton = new LiDarDataBase();
            }
        }
        return  dataBaseSingleton;
    }

    public void addStampedCloudPoints(StampedCloudPoints stampedCloudPoints)
    {
        stampedCloudPointsQueue.add(stampedCloudPoints);
    }



}

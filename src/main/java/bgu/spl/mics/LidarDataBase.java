package bgu.spl.mics;
import  java.util.concurrent.ConcurrentLinkedQueue;

/**
 * A singleton representing a database of StampedCloudPoints
 */

public class LidarDataBase {
    ConcurrentLinkedQueue<StampedCloudPoints> stampedCloudPointsQueue;
    static private LidarDataBase dataBaseSingleton = null;
    static private Object lock = new Integer(0);
    private LidarDataBase()
    {
        stampedCloudPointsQueue = new ConcurrentLinkedQueue<>();
    }

    /**
     returns the instance of the singelton (create it if it doesn't exist)
     */
    public static LidarDataBase getInstance()
    {
        if (dataBaseSingleton==null)
        {
            synchronized (lock) {
                if (dataBaseSingleton == null)
                    dataBaseSingleton = new LidarDataBase();
            }
        }
        return  dataBaseSingleton;
    }

    public void addStampedCloudPoints(StampedCloudPoints stampedCloudPoints)
    {
        stampedCloudPointsQueue.add(stampedCloudPoints);
    }



}

package bgu.spl.mics;
import  java.util.concurrent.ConcurrentLinkedQueue;
public class LidarDataBase {
    ConcurrentLinkedQueue<StampedCloudPoints> stampedCloudPointsQueue;
    static private LidarDataBase dataBaseSingleton = null;
    private LidarDataBase()
    {
        // TODO : Implement json
    }

    /**
     returns the instance of the singelton (create it if it doesn't exist)
     */
    public static LidarDataBase getInstance()
    {
        if (dataBaseSingleton==null)
        {
            synchronized (dataBaseSingleton) {
                if (dataBaseSingleton == null)
                    dataBaseSingleton = new LidarDataBase();
            }
        }
        return  dataBaseSingleton;
    }



}

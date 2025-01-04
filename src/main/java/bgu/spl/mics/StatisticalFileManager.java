package bgu.spl.mics;

import java.io.File;

public class StatisticalFileManager {
    private File statFile;
    private static StatisticalFileManager statisticalFileManagerSingleton = null;
    private static Object lock = new Integer((0));

    private StatisticalFileManager()
    {
        statFile = new File("Output_file.json");
    }

    public static StatisticalFileManager getInstance()
    {
        if (statisticalFileManagerSingleton==null)
        {
            synchronized (lock)
            {
                if (statisticalFileManagerSingleton == null)
                    statisticalFileManagerSingleton = new StatisticalFileManager();
            }
        }
        return statisticalFileManagerSingleton;
    }


}

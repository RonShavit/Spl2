package bgu.spl.mics;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class OutputFileManager {
    private String path;
    private static OutputFileManager statisticalFileManagerSingleton = null;
    private static Object lock = new Integer((0));
    private ConcurrentLinkedQueue<Camera> cams;
    private ConcurrentLinkedQueue<LiDarWorkerTracker> liDarWorkerTrackers;
    private AtomicBoolean isError;

    private OutputFileManager()
    {
        isError = new AtomicBoolean(false);
        this.path = "C:\\Users\\ronsh\\IdeaProjects\\spl2\\src\\main\\java\\bgu\\spl\\mics\\output_file.json";
    }

    public static OutputFileManager getInstance()
    {
        if (statisticalFileManagerSingleton==null)
        {
            synchronized (lock)
            {
                if (statisticalFileManagerSingleton == null)
                    statisticalFileManagerSingleton = new OutputFileManager();
            }
        }
        return statisticalFileManagerSingleton;
    }


    public void writeError(String errMsg, Camera faultyCam) throws IOException {
        isError.compareAndSet(false,true);
        ErrorJson errorJson = new ErrorJson(errMsg, "camera"+faultyCam.getId(),FusionSlam.getInstance().getPoses(), cams,liDarWorkerTrackers);
        Gson gson = new Gson();
        FileWriter fileWriter = new FileWriter(path);
        gson.toJson(errorJson,fileWriter);
        fileWriter.close();
    }

    public void writeError(String errMsg, LiDarWorkerTracker liDarWorkerTracker) throws IOException
    {
        isError.compareAndSet(false,true);
        ErrorJson errorJson = new ErrorJson(errMsg, "LiDarWorkerTracker"+liDarWorkerTracker.getId(),FusionSlam.getInstance().getPoses(), cams,liDarWorkerTrackers);
        Gson gson = new Gson();
        FileWriter fileWriter = new FileWriter(path);
        gson.toJson(errorJson,fileWriter);
        fileWriter.close();
    }

    public void writeStatistics() throws IOException
    {
        if (!isError.get())
        {
            ConcurrentLinkedQueue<LandMark> landMarkConcurrentLinkedQueue = FusionSlam.getInstance().getLandmarks();
            Stats stats = new Stats(StatisticalFolder.getInstance(),landMarkConcurrentLinkedQueue.toArray(new LandMark[landMarkConcurrentLinkedQueue.size()]));
            Gson gson = new Gson();
            FileWriter fileWriter = new FileWriter(path);
            gson.toJson(stats,fileWriter);


            fileWriter.close();


        }
    }



}

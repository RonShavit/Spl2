package bgu.spl.mics;

import java.util.concurrent.ConcurrentLinkedQueue;

public class ErrorJson {
    private String error;
    private String faultySensor;
    private Pose[] poses;
    private StatsWitoutLandmarks statistics;

    public ErrorJson(String error, String faultySensor, ConcurrentLinkedQueue<Pose> poses, ConcurrentLinkedQueue<Camera> cams, ConcurrentLinkedQueue<LiDarWorkerTracker> lidars)
    {
        this.error = error;
        this.faultySensor = faultySensor;
        this.poses = poses.toArray(new Pose[poses.size()]);
        this.statistics =new StatsWitoutLandmarks(StatisticalFolder.getInstance());
    }
}



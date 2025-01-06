package bgu.spl.mics;

import bgu.spl.mics.application.objects.*;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ErrorJson {
    private String error;
    private String faultySensor;
    private ConcurrentHashMap<String, DetectedObject[]> lastCameraFrame;
    private ConcurrentHashMap<String, TrackedObject[]> lastLiDarWorkerTrackersFrame;
    private Pose[] poses;
    private StatsWitoutLandmarks statistics;

    public ErrorJson(String error, String faultySensor, ConcurrentLinkedQueue<Pose> poses, ConcurrentLinkedQueue<Camera> cams, ConcurrentLinkedQueue<LiDarWorkerTracker> lidars)
    {
        this.error = error;
        this.faultySensor = faultySensor;
        lastCameraFrame = new ConcurrentHashMap<>();
        for (Camera cam: cams)
        {
            lastCameraFrame.put("camera"+cam.getId(),cam.getLastFrame().toArray(new DetectedObject[cam.getLastFrame().size()]));
        }
        lastLiDarWorkerTrackersFrame = new ConcurrentHashMap<>();
        for (LiDarWorkerTracker liDarWorkerTracker: lidars)
        {
            lastLiDarWorkerTrackersFrame.put("LiDarWorkerTracker"+liDarWorkerTracker.getId(), liDarWorkerTracker.getLastTrackedObjects().toArray(new TrackedObject[liDarWorkerTracker.getLastTrackedObjects().size()]));
        }
        this.poses = poses.toArray(new Pose[poses.size()]);
        this.statistics =new StatsWitoutLandmarks(StatisticalFolder.getInstance());
    }
}



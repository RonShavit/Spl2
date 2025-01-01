package bgu.spl.mics;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class FusionSlam {
    private static FusionSlam fusionSlamInstance = null;
    ConcurrentLinkedQueue<Landmark> landmarks;
    ConcurrentLinkedQueue<Pose> poses;
    private FusionSlam()
    {

    }

    public static FusionSlam getInstance()
    {
        if (fusionSlamInstance==null)
        {
            synchronized (fusionSlamInstance)
            {
                if (fusionSlamInstance == null)
                    fusionSlamInstance = new FusionSlam();
            }
        }
        return fusionSlamInstance;
    }

    public void normalizeTrackedObjects(ConcurrentLinkedQueue<TrackedObject> trackedObjects)
    {
        for(TrackedObject trackedObject:trackedObjects)
        {
            ConcurrentLinkedQueue<CloudPoint> coordinates = trackedObject.getCoordinates();
            ConcurrentLinkedQueue<CloudPoint> landmarkCoordinates = new ConcurrentLinkedQueue<>();
            Pose currentPose = null;
            for (Pose pose: poses)
            {
                if (pose.getTIME()==trackedObject.getTime())
                    currentPose = pose;
            }
            for (CloudPoint coordinate:coordinates)
            {

                CloudPoint landmarkCloudpoint = normalizeLocationOfPoint(coordinate,currentPose);
                landmarkCoordinates.add(landmarkCloudpoint);
            }
            int counter = 0;
            double sumX=0;
            double sumY=0;
            for (CloudPoint coordinate:landmarkCoordinates)
            {
                sumX+=coordinate.getX();
                sumY+=coordinate.getY();
                counter++;
            }
            CloudPoint newPoint = new CloudPoint(sumX/counter,sumY/counter);
            boolean isLandMarkExist = false;
            for (Landmark landmark: landmarks)
            {
                if (landmark.getId().compareTo(trackedObject.getId())==0)
                {
                    landmark.addCloudPoint(newPoint);
                    isLandMarkExist =true;
                }
            }
            if (!isLandMarkExist)
            {
                landmarks.add(new Landmark(trackedObject.getId(),trackedObject.getDescription()));
            }

        }
    }

    private CloudPoint normalizeLocationOfPoint(CloudPoint point,Pose pose)
    {
        double distance = Math.sqrt(Math.pow(point.getX(), 2)+Math.pow(point.getY(), 2));
        double angle = pose.getYaw();
        return new CloudPoint(pose.getX()+(Math.cos(angle)*distance),pose.getY()+(Math.sin(angle)*distance));
    }

    public void addPose(Pose pose)
    {
        poses.add(pose);
    }
}

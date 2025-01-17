package bgu.spl.mics.application.objects;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * represents a FusionSlam unit
 */
public class FusionSlam {
    private static FusionSlam fusionSlamInstance = null;
    private static Object lock = new Integer(0);
    ConcurrentLinkedQueue<LandMark> landmarks;
    ConcurrentLinkedQueue<Pose> poses;
    private FusionSlam()
    {
        poses = new ConcurrentLinkedQueue<>();
        landmarks = new ConcurrentLinkedQueue<>();
    }

    public static FusionSlam getInstance()
    {
        if (fusionSlamInstance==null)
        {
            synchronized (lock)
            {
                if (fusionSlamInstance == null)
                    fusionSlamInstance = new FusionSlam();
            }
        }
        return fusionSlamInstance;
    }

    /**
     * for each {@link TrackedObject} in {@code trackedObjects}: <p>
     *
     *  - Find its location in accordance to the charging station
     *  </p>
     *  <p>
     *
     *  - Either add it to the landmarks list {@code this.landmarks} or update its data there</p>
     *
     * @param trackedObjects a List of tracked objects
     */
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
            CloudPoint newPoint;
            if (counter!=0) {
                newPoint = new CloudPoint(sumX / counter, sumY / counter);
            }
            else
            {
                newPoint = new CloudPoint(0, 0);
            }
            boolean isLandMarkExist = false;

            for (LandMark landmark: landmarks)
            {
                if (landmark.getId().compareTo(trackedObject.getId())==0)
                {
                    landmark.addCloudPoint(newPoint);

                    isLandMarkExist =true;
                }
            }
            if (!isLandMarkExist)
            {
                LandMark newLandMark = new LandMark(trackedObject.getId(),trackedObject.getDescription());
                newLandMark.addCloudPoint(newPoint);
                landmarks.add(newLandMark);
                StatisticalFolder.getInstance().increaseLandMarks();
            }

        }
    }

    /**
     *
     * @param point a {@link CloudPoint} in accordance to the robot
     * @param pose the current {@link Pose} of the robot
     * @return The {@link CloudPoint} {@code point} in accordance to the charging station
     */
    public CloudPoint normalizeLocationOfPoint(CloudPoint point,Pose pose)
    {
        double distance = Math.sqrt(Math.pow(point.getX(), 2)+Math.pow(point.getY(), 2));
        double angle = pose.getYaw();
        return new CloudPoint(pose.getX()+(point.getX()*Math.cos(angle))+(point.getY()*Math.sin(angle)),pose.getY()-(point.getX()*Math.sin(angle))+(point.getY()*Math.cos(angle)));
    }

    public void addPose(Pose pose)
    {
        if (pose!=null)
            poses.add(pose);
    }

    public ConcurrentLinkedQueue<Pose> getPoses() {
        return poses;
    }

    public ConcurrentLinkedQueue<LandMark> getLandmarks() {
        return landmarks;
    }
}

package bgu.spl.mics;

public class StatsWitoutLandmarks {
    private int systemRuntime;
    private int numDetectedObjects;
    private int numTrackedObjects;
    private int numLandMarks;


    public StatsWitoutLandmarks(StatisticalFolder statisticalFolder)
    {
        systemRuntime = statisticalFolder.getSystemRuntime().intValue();
        numDetectedObjects = statisticalFolder.getNumDetectedObjects().intValue();
        numTrackedObjects = statisticalFolder.getNumTrackedObjects().intValue();
        numLandMarks =statisticalFolder.getNumLandMarks().intValue();

    }

    public StatsWitoutLandmarks(int systemRuntime, int numDetectedObjects, int numTrackedObjects, int numLandMarks) {
        this.systemRuntime = systemRuntime;
        this.numDetectedObjects = numDetectedObjects;
        this.numTrackedObjects = numTrackedObjects;
        this.numLandMarks = numLandMarks;
    }
}

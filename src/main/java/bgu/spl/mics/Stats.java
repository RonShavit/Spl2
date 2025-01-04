package bgu.spl.mics;

public class Stats{
    private int systemRuntime;
    private int numDetectedObjects;
    private int numTrackedObjects;
    private int numLandMarks;
    private LandMark[] landMarks;
    public Stats(StatisticalFolder statisticalFolder, LandMark[] landMarks)
    {
        systemRuntime = statisticalFolder.getSystemRuntime().intValue();
        numDetectedObjects = statisticalFolder.getNumDetectedObjects().intValue();
        numTrackedObjects = statisticalFolder.getNumTrackedObjects().intValue();
        numLandMarks =statisticalFolder.getNumLandMarks().intValue();
        this.landMarks = landMarks;

    }
}

package bgu.spl.mics.application.objects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FusionSlamNormalizationTest {


    @Test
    void TestNormalizationOfAPointThatNeedsItWithoutAngle()
    {
        FusionSlam fusionSlam = FusionSlam.getInstance();
        Pose testPose = new Pose(0,0,0,0);
        CloudPoint testPoint = new CloudPoint(0,1);
        CloudPoint normalizedTestPoint = fusionSlam.normalizeLocationOfPoint(testPoint,testPose);
        assertEquals(1,Math.round(normalizedTestPoint.getX())); // using rounding to account for sin/cos marginOfError
        assertEquals(0,Math.round(normalizedTestPoint.getY()));
    }
    @Test
    void TestNormalizationOfAPointThatNeedsItWithAngle()
    {
        FusionSlam fusionSlam = FusionSlam.getInstance();
        Pose testPose = new Pose(1,1,(float)((Math.PI)/4),0);
        CloudPoint testPoint = new CloudPoint(2,2);
        CloudPoint normalizedTestPoint = fusionSlam.normalizeLocationOfPoint(testPoint,testPose);
        assertEquals(1,Math.round(normalizedTestPoint.getX()-Math.sqrt(8))); // using rounding to account for sin/cos marginOfError
        assertEquals(1,Math.round(normalizedTestPoint.getY()));
    }

}
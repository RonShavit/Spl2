package bgu.spl.mics.application.objects;

import bgu.spl.mics.MessageBusImpl;
import bgu.spl.mics.application.messages.TickBroadcast;
import bgu.spl.mics.application.services.CameraService;
import bgu.spl.mics.application.services.TimeService;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class CameraTest {
    @Test
    void getAllDetectedObjectsAtTime1()
    {
        Camera testCam = new Camera(1,0,"C:/Users/ronsh/IdeaProjects/spl2/src/test/testInput/camera_data.json");
        CameraService cameraServiceTest = new CameraService(testCam);
        cameraServiceTest.initialize();
        StampedDetectedObject stampedDetectedObjectTest = testCam.getCameraData(1,cameraServiceTest);
        assertEquals(1,stampedDetectedObjectTest.getDetectedObjects().size());

    }

    /**
     *
     */
    @Test
    void getMultipleDetectedObjectsFromCameraAtTime3()
    {
        Camera testCam = new Camera(1,0,"C:/Users/ronsh/IdeaProjects/spl2/src/test/testInput/camera_data.json");
        CameraService cameraServiceTest = new CameraService(testCam);
        cameraServiceTest.initialize();
        StampedDetectedObject stampedDetectedObjectTest = testCam.getCameraData(3,cameraServiceTest);
        assertEquals(3,stampedDetectedObjectTest.getDetectedObjects().size());
    }


}
package bgu.spl.mics;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args)
    {
        //String path = args[0];
        String path = "C:/Users/ronsh/IdeaProjects/spl2/example_input_2/configuration_file.json"; // ya change that

        String directory = getDirectory(path);
        try (FileReader f = new FileReader(path)) {
            // get data from json
            JsonObject jsonObject = JsonParser.parseReader(f).getAsJsonObject();
            int speed = jsonObject.get("TickTime").getAsInt();
            int duration = jsonObject.get("Duration").getAsInt();
            String posePath = jsonObject.get("poseJsonFile").getAsString();
            posePath = getDirectory(path) + posePath.substring(1);

            //for cameras
            JsonObject cameraData = jsonObject.getAsJsonObject("Cameras");
            String cameraDataPath = cameraData.get("camera_datas_path").getAsString();
            cameraDataPath = directory+cameraDataPath.substring(1);

            List<Camera> cameraList = new ArrayList<>();
            JsonArray camerasConfig = cameraData.getAsJsonArray("CamerasConfigurations");
            for (JsonElement jsonElement: camerasConfig)
            {
                cameraList.add(new Camera(((JsonObject)jsonElement).get("id").getAsInt(),((JsonObject)jsonElement).get("frequency").getAsInt(),cameraDataPath));
            }
            List<JsonObject> camerasAsJson = new ArrayList<>();

            //for lidars
            JsonObject lidarData = jsonObject.getAsJsonObject("LiDarWorkers");
            String lidarDataPath = lidarData.get("lidars_data_path").getAsString();
            lidarDataPath = directory+lidarDataPath.substring(1);
            List<LiDarWorkerTracker> lidarList = new ArrayList<>();
            JsonArray lidarsConfig = lidarData.getAsJsonArray("LidarConfigurations");
            for (JsonElement jsonElement: lidarsConfig)
            {
                lidarList.add(new LiDarWorkerTracker(((JsonObject)jsonElement).get("id").getAsInt(),((JsonObject)jsonElement).get("frequency").getAsInt(),lidarDataPath));
            }
            List<JsonObject> lidarsAsJson = new ArrayList<>();


            //create MicroServices Threads
            TimeService timeService = new TimeService(speed,duration);
            Thread tickService = new Thread(timeService
            );

            for (Camera camera:cameraList)
            {
                CameraService cameraService = new CameraService(camera);
                Thread camServiceThread = new Thread(cameraService,"camera"+camera.getId());
                camServiceThread.start();
                TerminatedCounter.getInstance().increaseRunning();
            }

            for (LiDarWorkerTracker lidar: lidarList)
            {
                LiDarService liDarWorkerService = new LiDarService(lidar);
                Thread lidarServiceThread = new Thread(liDarWorkerService,"lidar" +lidar.getId());
                lidarServiceThread.start();
                TerminatedCounter.getInstance().increaseRunning();
            }

            PoseService poseService = new PoseService(new GPSIMU(posePath));
            Thread poseServiceThread = new Thread(poseService);
            poseServiceThread.start();
            TerminatedCounter.getInstance().increaseRunning();

            FusionSlamService fusionSlamService = new FusionSlamService(FusionSlam.getInstance());
            Thread fusionSlamThread = new Thread(fusionSlamService, "fu");
            fusionSlamThread.start();
            TerminatedCounter.getInstance().increaseRunning();



            //Thread.sleep(3000);
            //run threads
            tickService.start();

            tickService.join();
            System.out.println(StatisticalFolder.getInstance().toString());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static String getDirectory(String path)
    {
        String[] directory = path.split("/");
        String toReturn = "";
        for (int i=0;i<directory.length-2;i++)
        {
            toReturn+=directory[i]+"/";
        }
        toReturn+=directory[directory.length-2];
        return toReturn;
    }

}

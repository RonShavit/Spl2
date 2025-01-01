package bgu.spl.mics;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.swing.plaf.IconUIResource;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args)
    {
        //String path = args[0];
        String path = "C:\\Users\\ronsh\\IdeaProjects\\spl2\\example input\\configuration_file.json";
        try (FileReader f = new FileReader(path)) {
            // get data from json
            JsonObject jsonObject = JsonParser.parseReader(f).getAsJsonObject();
            int speed = jsonObject.get("TickTime").getAsInt();
            int duration = jsonObject.get("Duration").getAsInt();

            //for cameras
            JsonObject cameraData = jsonObject.getAsJsonObject("Cameras");
            String cameraDataPath = cameraData.get("camera_datas_path").getAsString();

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
            List<LidarTrackerWorker> lidarList = new ArrayList<>();
            JsonArray lidarsConfig = cameraData.getAsJsonArray("LidarConfigurations");
            for (JsonElement jsonElement: lidarsConfig)
            {
                lidarList.add(new LidarTrackerWorker(((JsonObject)jsonElement).get("id").getAsInt(),((JsonObject)jsonElement).get("frequency").getAsInt(),lidarDataPath));
            }
            List<JsonObject> lidarsAsJson = new ArrayList<>();


            //create MicroServices Threads
            TimeService timeService = new TimeService("TimeService",speed,duration);
            Thread tickService = new Thread(new Runnable() {
                @Override
                public void run() {
                    timeService.initialize();
                }
            });

            for (Camera camera:cameraList)
            {
                CameraService cameraService = new CameraService("camera"+camera.getId(),camera);
                Thread camServiceThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        cameraService.initialize();
                    }
                });
                camServiceThread.start();
            }




            //run threads
            tickService.start();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}

package com.cadenkoehl.cadenbot.util.data;

import com.cadenkoehl.cadenbot.CadenBot;
import com.cadenkoehl.cadenbot.util.Constants;
import com.cadenkoehl.cadenbot.util.ExceptionHandler;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Data {

    public static String getToken() {
        if(CadenBot.isTest) return Config.get().getString("test_token");
        else return Config.get().getString("token");
    }

    public static String getDataFolderPath() {
        if (CadenBot.isTest) return Config.get().getString("test_data_folder");
        else return Config.get().getString("data_folder");
    }

    public static File getDataFolder() {
        return new File(Data.getDataFolderPath());
    }

    public static File getSelfIconFile() {
        return new File(CadenBot.dataDirectory + "CadenBot.png");
    }

    public JSONObject getJSONObjectFromFile(String path) {
        File file = new File(path);
        String jsonString = "";
        try {
            if(file.createNewFile()) System.out.println("Created file: " + path);

            Scanner scan = new Scanner(file);
            while (scan.hasNextLine()) jsonString = jsonString + scan.nextLine();
        }
        catch (IOException e) {
            e.printStackTrace();
            ExceptionHandler.sendStackTrace(e);
        }
        if(jsonString.isEmpty()) {
            return new JSONObject("{}");
        }
        System.out.println(jsonString);
        return new JSONObject(jsonString);
    }

    public void saveDataToJSONFile(File file, JSONObject data) {
        try {
            FileWriter write = new FileWriter(file);
            write.write(data.toString());
            write.close();
        }
        catch (IOException ex) {
            ExceptionHandler.sendStackTrace(ex);
            ex.printStackTrace();
        }
    }
}
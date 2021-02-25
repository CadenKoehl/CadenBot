package com.cadenkoehl.cadenbot.util.data;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Config {

    //Gets and returns the config file
    public File getConfigFile() {
        return new File("config.json");
    }

    //Gets and returns the config file as a JSONObject
    public JSONObject getConfig() {
        Scanner scan;
        try {
            scan = new Scanner(this.getConfigFile());
        } catch (IOException ex) {
            ex.printStackTrace();
            return new JSONObject("");
        }
        String jsonString = "";
        while (scan.hasNextLine()) jsonString = jsonString + scan.nextLine();
        return new JSONObject(jsonString);
    }

    public String getString(String key) {
        return this.getConfig().getString(key);
    }

    public Object get(String key) {
        return this.getConfig().get(key);
    }

    public boolean getBoolean(String key) {
        return this.getConfig().getBoolean(key);
    }

    public static Config get() {
        return new Config();
    }
}

package com.cadenkoehl.cadenbot.util.data;

import com.cadenkoehl.cadenbot.CadenBot;
import com.cadenkoehl.cadenbot.util.Constants;

import java.io.File;

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
}
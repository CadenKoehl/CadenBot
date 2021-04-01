package com.cadenkoehl.cadenbot.util.data;

import com.cadenkoehl.cadenbot.CadenBot;
import com.cadenkoehl.cadenbot.util.ExceptionHandler;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Data {

    /**
     *
     * @param dirPath The directory of this file. Note that this will be suffixed with com.cadenkoehl.cadenbot.CadenBot.dataDirectory
     * @param fileName The name of the file
     * @param content What is to be written to the file
     */
    public static void writeToFile(String dirPath, String fileName, String content) {
        File dir = new File(CadenBot.dataDirectory + dirPath);
        File file = new File(dir, fileName);

        if(!dir.mkdirs()) System.err.println("[ERROR]: Directory " + dir.getPath() + " was not created successfully!");

        try {
            FileWriter write = new FileWriter(file);
            write.write(content);
            write.close();
        }
        catch (IOException ex) {
            ExceptionHandler.sendStackTrace(ex);
        }
    }

    public static void appendToFile(File dir, String key, String value) {
        File file = new File(dir, key + ".txt");

        if(!dir.mkdirs()) System.err.println("[ERROR]: Directory " + dir.getPath() + " was not created successfully!");

        try {
            FileWriter write = new FileWriter(file, true);
            write.write("\n" + value);
            write.close();
        }
        catch (IOException ex) {
            ExceptionHandler.sendStackTrace(ex);
        }
    }

    public static void appendToFile(String subDirName, String key, String... values) {
        File dir = new File(CadenBot.dataDirectory + subDirName + "/");
        File file = new File(dir, key + ".txt");

        if(!dir.mkdirs()) System.err.println("[ERROR]: Directory " + dir.getPath() + " was not created successfully!");

        try {
            FileWriter write = new FileWriter(file, true);
            for(String value : values) {
                write.write("\n" + value);
            }
            write.close();
        }
        catch (IOException ex) {
            ExceptionHandler.sendStackTrace(ex);
        }
    }

    /**
     * @param key Key
     * @param line Line you want to remove
     * @return true if the line was removed, false if it was not
     */
    public static boolean removeLineFromFile(File dir, String key, String line) {
        List<String> strings = getStringsFromFile(dir, key);

        if(strings == null) return false;

        File file = new File(dir, key + ".txt");

        boolean delete = file.delete();

        if(strings.size() == 1 && strings.get(0).equalsIgnoreCase(line)) return delete;

        boolean remove = strings.remove(line);

        for(String string : strings) {
            writeToFile("bad_words", key + ".txt", string + "\n");
        }
        return remove;
    }

    /**
     * @param subDirName Name of the subdirectory
     * @param key Key
     * @param line Line you want to remove
     * @return true if the line was removed, false if it was not
     */
    public static boolean removeLineFromFile(String subDirName, String key, String line) {
        File dir = new File(CadenBot.dataDirectory + subDirName + "/");
        return removeLineFromFile(dir, key, line);
    }

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

    public static JSONObject getJSONObjectFromFile(String path) {
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
        return new JSONObject(jsonString);
    }

    public static void saveDataToJSONFile(File file, JSONObject data) {
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

    public static void saveKeyAndValue(File dir, String key, String value) {

        if(dir.mkdirs()) System.out.println(dir.getPath() + " was successfully created!");

        File file = new File(dir, key + ".txt");

        try {
            FileWriter write = new FileWriter(file);
            write.write(value);
            write.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveKeyAndValue(String subDirName, String key, String value) {
        File dir = new File(CadenBot.dataDirectory + subDirName + "/");
        saveKeyAndValue(dir, key, value);
    }

    public static String getValueByKey(File dir, String key) {
        if(!dir.exists()) return null;

        File file = new File(dir, key + ".txt");

        try {
            Scanner scan = new Scanner(file);
            return scan.nextLine();
        } catch (FileNotFoundException e) {
            System.err.println(e + ": Returning null");
            return null;
        }
    }

    public static String getValueByKey(String subDirName, String key) {
        File dir = new File(CadenBot.dataDirectory + subDirName + "/");
        return getValueByKey(dir, key);
    }

    public static List<String> getStringsFromFile(File dir, String key) {

        List<String> stringsFromFile = new ArrayList<>();

        if(!dir.exists()) return null;

        File file = new File(dir, key + ".txt");

        try {
            Scanner scan = new Scanner(file);
            while(scan.hasNextLine()) {
                String string = scan.nextLine();
                if(string.isEmpty()) continue;
                stringsFromFile.add(string);
            }
        } catch (FileNotFoundException e) {
            return null;
        }
        return stringsFromFile;
    }

    public static List<String> getStringsFromFile(String subDirName, String key) {
        File dir = new File(CadenBot.dataDirectory + subDirName + "/");
        return getStringsFromFile(dir, key);
    }
}
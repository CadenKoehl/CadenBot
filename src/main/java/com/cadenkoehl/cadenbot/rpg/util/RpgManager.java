package com.cadenkoehl.cadenbot.rpg.util;

import com.cadenkoehl.cadenbot.CadenBot;
import net.dv8tion.jda.api.entities.User;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class RpgManager {
    //Gives the user a player, returns true if successful, false if the player already exists
    public boolean createPlayer(User user) {
        String userId = user.getId();
        File dir = new File(CadenBot.dataDirectory + "rpg/players/" + user.getId());
        if(dir.mkdirs()) {
            System.out.println("Successfully created RPG directory for " + user.getName() + "!");
        }
        File file = new File(dir,"profile.txt");
        if(file.exists()) {
            System.out.println(file.getPath() + " already exists!");
            return false;
        }
        try {
            FileWriter write = new FileWriter(file);
            write.write("");
            write.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
    private String createPlayerName(User user) {
        String name = user.getName();
        name = name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
        Random random = new Random();
        String[] suffixes = {
                "The Great",
                "The Powerful",
                "The Amazing",
                "The Defiant",
                "The Destroyer",
                "The VII",
                "The VI",
                "The IV",
                "The V",
                "The Mighty",
        };
        int suffix = random.nextInt(suffixes.length);
        return name + " " + suffixes[suffix];
    }
}
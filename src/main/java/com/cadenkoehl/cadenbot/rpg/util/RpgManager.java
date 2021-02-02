package com.cadenkoehl.cadenbot.rpg.util;

import com.cadenkoehl.cadenbot.CadenBot;
import net.dv8tion.jda.api.entities.User;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class RpgManager {
    //Gives the user a player
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
}
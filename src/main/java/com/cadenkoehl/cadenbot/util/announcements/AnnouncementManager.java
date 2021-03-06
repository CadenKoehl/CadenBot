package com.cadenkoehl.cadenbot.util.announcements;

import com.cadenkoehl.cadenbot.CadenBot;
import com.cadenkoehl.cadenbot.util.ExceptionHandler;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class AnnouncementManager {

    private AnnouncementManager(){}

    public static AnnouncementManager INSTANCE = new AnnouncementManager();

    public TextChannel getAnnouncementChannel(Guild guild) {
        File dir = new File(CadenBot.dataDirectory + "announcements/");
        if(dir.mkdirs()) System.out.println(dir.getPath() + " was created");

        File file = new File(dir, guild.getId() + ".txt");

        if(!file.exists()) return null;

        Scanner scan;

        try {
            scan = new Scanner(file);
        } catch (FileNotFoundException e) {
            ExceptionHandler.sendStackTrace(e);
            return null;
        }

        return guild.getTextChannelById(scan.nextLine());
    }

    public void setAnnouncementChannel(Guild guild, TextChannel channel) {
        File dir = new File(CadenBot.dataDirectory + "announcements/");
        if(dir.mkdirs()) System.out.println(dir.getPath() + " was created");

        File file = new File(dir, guild.getId() + ".txt");
        try {
            FileWriter write = new FileWriter(file);
            write.write(channel.getId());
            write.close();
        } catch (IOException e) {
            ExceptionHandler.sendStackTrace(e);
        }
    }
}

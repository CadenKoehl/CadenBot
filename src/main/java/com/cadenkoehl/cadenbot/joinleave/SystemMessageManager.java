package com.cadenkoehl.cadenbot.joinleave;

import com.cadenkoehl.cadenbot.CadenBot;
import net.dv8tion.jda.api.entities.Guild;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SystemMessageManager {
    public static String getWelcomeChannelId(Guild guild) {
        String channelId;
        try {
            File file = new File(CadenBot.dataDirectory + "joinleave/joinchannel/" + guild.getId() + ".txt");
            Scanner scan = new Scanner(file);
            channelId = scan.nextLine();
        } catch (FileNotFoundException e) {
            channelId = null;
        }
        return channelId;
    }
    public static String getGoodbyeChannelId(Guild guild) {
        String channelId;
        try {
            File file = new File(CadenBot.dataDirectory + "joinleave/leavechannel/" + guild.getId() + ".txt");
            Scanner scan = new Scanner(file);
            channelId = scan.nextLine();
        } catch (FileNotFoundException ex) {
            channelId = null;
        }
        return channelId;
    }
    public static String getWelcomeMessage(Guild guild) {
        String msg;
        try {
            File file = new File(CadenBot.dataDirectory + "joinleave/joinmessage/" + guild.getId() + ".txt");
            Scanner scan = new Scanner(file);
            msg = scan.nextLine();
        }
        catch (FileNotFoundException e) {
            msg = null;
            e.printStackTrace();
        }
        return msg;
    }
    public static String getGoodbyeMessage(Guild guild) {
        String msg;
        try {
            File file = new File(CadenBot.dataDirectory + "joinleave/leavemessage/" + guild.getId() + ".txt");
            Scanner scan = new Scanner(file);
            msg = scan.nextLine();
        }
        catch (FileNotFoundException e) {
            msg = null;
            e.printStackTrace();
        }
        return msg;
    }
}
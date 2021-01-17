package com.cadenkoehl.cadenbot.settings;

import com.cadenkoehl.cadenbot.CadenBot;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class SwearCensor extends ListenerAdapter {

    File censor = new File("swearcensor.txt");
    String id;

    public boolean hasSwearCensor() throws FileNotFoundException {
        boolean hasSwearCensor = false;
        Scanner scan = new Scanner(censor);
        while(scan.hasNextLine())
            if(scan.nextLine().equalsIgnoreCase(id))
                hasSwearCensor = true;
        return hasSwearCensor;
    }

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        id = event.getGuild().getId();
        if(args[0].equalsIgnoreCase(CadenBot.prefix + "swearcensor")) {
            try {
                if(hasSwearCensor()) {
                    event.getChannel().sendMessage("**Swear-censor is already enabled**").queue();
                }
                if(!hasSwearCensor()) {
                    FileWriter write = new FileWriter(censor, true);
                    write.append("\n" + id);
                    write.close();
                    event.getChannel().sendMessage("**Swear-censor is now enabled!**").queue();
                    System.out.println(this.hasSwearCensor());
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

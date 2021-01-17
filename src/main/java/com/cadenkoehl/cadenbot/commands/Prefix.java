package com.cadenkoehl.cadenbot.commands;

import com.cadenkoehl.cadenbot.CadenBot;
import com.cadenkoehl.cadenbot.Constants;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Prefix extends ListenerAdapter {
    private static String id;
    private static String prefix = "-";
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        id = event.getGuild().getId();
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        if(args[0].equalsIgnoreCase(getPrefix() + "prefix") || args[0].equalsIgnoreCase("CadenBot-prefix")) {
            if(event.getMember().hasPermission(Permission.MANAGE_SERVER)) {
                String prefix = Arrays.stream(args).skip(1).collect(Collectors.joining(" "));
                id = event.getGuild().getId();

                if(prefix.equalsIgnoreCase("") || prefix.contains(" ")) {
                    if(prefix.contains(" ")) {
                        event.getChannel().sendMessage("Prefix cannot have spaces!").queue();
                    }
                    if(prefix.equalsIgnoreCase("")) {
                        event.getChannel().sendMessage("The current prefix for this server is `" + getPrefix() + "`").queue();
                    }
                }
                else {

                    File file = new File(CadenBot.dataDirectory + "prefix/" + id + ".txt");
                    try {
                        if(!file.exists()) {
                            file.createNewFile();
                        }
                        FileWriter write = new FileWriter(file);
                        write.write(prefix);
                        write.close();
                        event.getChannel().sendMessage("My prefix on this server was changed to `" + prefix + "`").queue();
                    }
                    catch (IOException e) {
                        event.getChannel().sendMessage("A fatal error has occurred! Please contact Caden#7777 if this issue persists!").queue();
                        e.printStackTrace();
                    }
                }
            }
            if(!event.getMember().hasPermission(Permission.MANAGE_SERVER)) {
                event.getChannel().sendMessage("You must have the `manage_server` permission to use this command!").queue();
            }
        }
    }
    private static String getPrefix() {
        File file;
        try {
            file = new File(CadenBot.dataDirectory + "prefix/" + id + ".txt");
            Scanner scan = new Scanner(file);
            prefix = scan.nextLine();
        }
        catch (FileNotFoundException ex) {
            prefix = "-";
        }
        catch (IOException ex) {
            ex.printStackTrace();
            CadenBot.jda.getTextChannelById(Constants.CADENBOTBUGSCHANNEL).sendMessage(CadenBot.jda.getUserById(Constants.CadenID).getAsMention() + " help! There is a huge bug in my code!! Someone tried to run a command, and this happened: " +
                    ex).queue();
        }
        return prefix;
    }
}

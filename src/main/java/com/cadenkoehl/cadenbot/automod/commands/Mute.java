package com.cadenkoehl.cadenbot.automod.commands;

import com.cadenkoehl.cadenbot.CadenBot;
import com.cadenkoehl.cadenbot.Constants;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.channel.text.TextChannelCreateEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Mute extends ListenerAdapter {
    private static String id;
    private static String prefix = "-";
    //sets up the muted role for legacy servers
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        id = event.getGuild().getId();
        TextChannel channel = event.getChannel();
        Guild guild = event.getGuild();
        Member member = event.getMember();
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        if(args[0].equalsIgnoreCase(getPrefix() + "setupMuteFeature")) {
            if(member.hasPermission(Permission.ADMINISTRATOR)) {
                try {
                    channel.sendMessage("Setting up Mute Feature!").queue();
                    guild.createRole().setName("Muted").complete();

                    List channels = event.getGuild().getTextChannels();
                    int numberOfChannels = channels.size();
                    int index = 0;
                    Role muted = event.getGuild().getRolesByName("Muted", true).get(0);

                    while(index < numberOfChannels) {
                        guild.getTextChannels().get(index).createPermissionOverride(muted).setDeny(Permission.MESSAGE_WRITE).queue();
                        System.out.println("Created a permission override for channel " + index);
                        index++;
                    }
                    channel.sendMessage("Done!").queueAfter(5, TimeUnit.SECONDS);
                }
                catch (Exception e) {
                    channel.sendMessage("An error has occurred! This is usually because you are doing this command twice, or you already have a role called \"Muted\"! If not, and the issue persists, join my support server! (do -help)").queue();
                    System.out.println("Exception was caught! "+ e.toString());
                }
                channel.sendMessage("Process Finished!").queueAfter(2, TimeUnit.SECONDS);
            }
            if(!member.hasPermission(Permission.ADMINISTRATOR)) {
                channel.sendMessage("You must have the Administrator permission to use this command!").queue();
            }
        }
    }

    public void onTextChannelCreate(TextChannelCreateEvent event) {
        Role muted = event.getGuild().getRolesByName("Muted", true).get(0);
        event.getChannel().createPermissionOverride(muted).setDeny(Permission.MESSAGE_WRITE).queue();
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

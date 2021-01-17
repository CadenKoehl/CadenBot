package com.cadenkoehl.cadenbot.automod.commands;

import com.cadenkoehl.cadenbot.CadenBot;
import com.cadenkoehl.cadenbot.Constants;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Ban extends ListenerAdapter {
    private static String id;
    private static String prefix = "-";
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        id = event.getGuild().getId();
	    String[] args = event.getMessage().getContentRaw().split("\\s+");
	    if(args[0].equalsIgnoreCase(getPrefix() + "ban")) {
	        try {
                if(!event.getMember().getUser().isBot()) {
                    if(event.getMember().hasPermission(Permission.BAN_MEMBERS)) {
                        Member member = event.getMessage().getMentionedMembers().get(0);
                        String reason = Arrays.stream(args).skip(2).collect(Collectors.joining(" "));
                        event.getChannel().sendMessage(member.getUser().getAsTag() + " was banned! Reason: " + reason).queue();
                        member.ban(0, reason).queue();
                    }
                    if(!event.getMember().hasPermission(Permission.BAN_MEMBERS)) {
                        event.getChannel().sendMessage("You can't use that!").queue();
                    }
                }
            } catch(Exception e) {
	            event.getChannel().sendMessage("Something went wrong!\n**Correct Usage:** `-ban` `<@user>` `[reason]`").queue();
                System.out.println("An Exception was caught: " + e.toString());
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

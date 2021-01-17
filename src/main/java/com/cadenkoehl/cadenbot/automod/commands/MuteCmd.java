package com.cadenkoehl.cadenbot.automod.commands;

import com.cadenkoehl.cadenbot.CadenBot;
import com.cadenkoehl.cadenbot.Constants;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class MuteCmd extends ListenerAdapter {
    private static String id;
    private static String prefix = "-";
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        id = event.getGuild().getId();
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if(args[0].equalsIgnoreCase(getPrefix() + "mute")) {
            Member mod = event.getMember();
            if(mod.hasPermission(Permission.BAN_MEMBERS)) {
                try {
                    Member member = event.getMessage().getMentionedMembers().get(0);
                    Role muted = event.getGuild().getRolesByName("Muted", true).get(0);
                    if(!member.getRoles().contains(muted)) {
                        String reason = Arrays.stream(args).skip(2).collect(Collectors.joining(" "));
                        event.getGuild().addRoleToMember(member, muted).queue();
                        event.getChannel().sendMessage(member.getAsMention() + " was muted! Reason: " + reason).queue();
                    }
                    if(member.getRoles().contains(muted)) {
                        event.getChannel().sendMessage(member.getAsMention() + " is already muted!").queue();
                    }
                }
                catch (Exception e) {
                    event.getChannel().sendMessage("An error has occurred! This is usually because you typed the command wrong, or you haven't set up the mute feature yet.\n**Correct usage:** " +
                            "`-mute` `<@member>` `<reason>`\n**If you haven't set up the mute feature yet**, use `-setUpMuteFeature`").queue();
                    System.out.println(e.toString());
                }
            }
            if(!mod.hasPermission(Permission.BAN_MEMBERS)) {
                event.getChannel().sendMessage("You can't use that!").queue();
            }
        }
        if(args[0].equalsIgnoreCase(CadenBot.prefix + "unmute")) {
            Member mod = event.getMember();
            if(mod.hasPermission(Permission.BAN_MEMBERS)) {
                try {
                    Member member = event.getMessage().getMentionedMembers().get(0);
                    Role muted = event.getGuild().getRolesByName("Muted", true).get(0);
                    if(member.getRoles().contains(muted)) {
                        event.getGuild().removeRoleFromMember(member, muted).queue();
                        event.getChannel().sendMessage(member.getAsMention() + " was unmuted!").queue();
                    }
                    if(!member.getRoles().contains(muted)) {
                        event.getChannel().sendMessage(member.getAsMention() + " is not muted!").queue();
                    }
                }
                catch (Exception e) {
                    event.getChannel().sendMessage("An error has occurred! This is usually because you typed the command wrong, or you haven't set up the mute feature yet. \n**Correct usage:** " +
                            "`-unmute` `<@member>`\n**If you haven't set up the mute feature yet**, use `-setUpMuteFeature`").queue();
                    System.out.println(e.toString());
                }
            }
            if(!mod.hasPermission(Permission.BAN_MEMBERS)) {
                event.getChannel().sendMessage("You can't use that!").queue();
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
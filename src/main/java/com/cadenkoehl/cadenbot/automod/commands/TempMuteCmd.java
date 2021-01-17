package com.cadenkoehl.cadenbot.automod.commands;

import com.cadenkoehl.cadenbot.CadenBot;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class TempMuteCmd extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if(args[0].equalsIgnoreCase(CadenBot.prefix + "tempmute")) {
            Member mod = event.getMember();
            if(mod.hasPermission(Permission.BAN_MEMBERS)) {
                try {
                    Member member = event.getMessage().getMentionedMembers().get(0);
                    Role muted = event.getGuild().getRolesByName("Muted", true).get(0);
                    if(!member.getRoles().contains(muted)) {
                        String lengthStringRaw = Arrays.stream(args).skip(2).collect(Collectors.joining(" "));
                        String[] lengthString = lengthStringRaw.split(" ");
                        String reason = Arrays.stream(args).skip(3).collect(Collectors.joining(" "));
                        int length = Integer.parseInt(lengthString[0]);
                        event.getGuild().addRoleToMember(member, muted).queue();
                        event.getChannel().sendMessage(member.getAsMention() + " was muted for " + length + " minutes! Reason: " + reason).queue();
                        event.getGuild().removeRoleFromMember(member, muted).queueAfter(length, TimeUnit.MINUTES);
                        event.getChannel().sendMessage(member.getAsMention() + " is now unmuted.").queueAfter(length, TimeUnit.MINUTES);
                    }
                    if(member.getRoles().contains(muted)) {
                        event.getChannel().sendMessage(member.getAsMention() + " is already muted!").queue();
                    }
                }
                catch (Exception e) {
                    event.getChannel().sendMessage("An error has occurred! This is usually because you typed the command wrong, or you haven't set up the mute feature yet.\n**Correct usage:** " +
                            "`-mute` `<@member>` `<minutes>` `<reason>`\n**If you haven't set up the mute feature yet**, use `-setUpMuteFeature`").queue();
                    System.out.println(e.toString());
                }
            }
            if(!mod.hasPermission(Permission.BAN_MEMBERS)) {
                event.getChannel().sendMessage("You can't use that!").queue();
            }
        }
    }
}

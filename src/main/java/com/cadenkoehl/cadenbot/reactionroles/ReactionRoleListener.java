package com.cadenkoehl.cadenbot.reactionroles;

import com.cadenkoehl.cadenbot.CadenBot;
import com.cadenkoehl.cadenbot.util.Constants;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ReactionRoleListener extends ListenerAdapter {
    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) {
        if(!event.getUser().isBot()) {
            String messageId = event.getMessageId();
            Member member = event.getMember();
            File file = new File(CadenBot.dataDirectory + "reactionroles/" + messageId + ".txt");
            if(file.exists()) {
                try {
                    if(event.getReactionEmote().isEmote()) {
                        Scanner scan = new Scanner(file);
                        String contents = scan.nextLine();
                        String[] roleId = contents.split("\\s+");
                        Role role = event.getGuild().getRoleById(roleId[0]);
                        if(role == null) return;
                        if(member.getRoles().contains(role)) return;
                        String emoteId = Arrays.stream(roleId).skip(1).collect(Collectors.joining(" "));
                        if(emoteId.equalsIgnoreCase(event.getReactionEmote().getEmote().getId())) {
                            event.getGuild().addRoleToMember(member, role).queue();
                            member.getUser().openPrivateChannel().queue(channel -> {
                                channel.sendMessage(":white_check_mark: **Success!** Gave you the **" + role.getName() + "** role on **" + event.getGuild().getName() + "**!").queue();
                            });
                        }
                    }
                    if(event.getReactionEmote().isEmoji()) {
                        Scanner scan = new Scanner(file);
                        String contents = scan.nextLine();
                        String[] roleId = contents.split("\\s+");
                        Role role = event.getGuild().getRoleById(roleId[0]);
                        if(role == null) return;
                        if(member.getRoles().contains(role)) return;
                        String emoteId = Arrays.stream(roleId).skip(1).collect(Collectors.joining(" "));
                        if(emoteId.equalsIgnoreCase(event.getReactionEmote().getEmoji())) {
                            event.getGuild().addRoleToMember(member, role).queue();
                            member.getUser().openPrivateChannel().queue(channel -> {
                                channel.sendMessage(":white_check_mark: **Success!** Gave you the **" + role.getName() + "** role on **" + event.getGuild().getName() + "**!").queue();
                            });
                        }
                    }
                }
                catch (FileNotFoundException exception) {
                    exception.printStackTrace();
                }
            }
        }
    }
    public void onGuildMessageReactionRemove(GuildMessageReactionRemoveEvent event) {
        if(!event.getUser().isBot()) {
            String messageId = event.getMessageId();
            Member member = event.getMember();
            File file = new File(CadenBot.dataDirectory + "reactionroles/" + messageId + ".txt");
            if(file.exists()) {
                try {
                    if(event.getReactionEmote().isEmote()) {
                        Scanner scan = new Scanner(file);
                        String contents = scan.nextLine();
                        String[] roleId = contents.split("\\s+");
                        Role role = event.getGuild().getRoleById(roleId[0]);
                        if(role == null) return;
                        if(!member.getRoles().contains(role)) return;
                        String emoteId = Arrays.stream(roleId).skip(1).collect(Collectors.joining(" "));
                        if(emoteId.equalsIgnoreCase(event.getReactionEmote().getEmote().getId())) {
                            event.getGuild().removeRoleFromMember(member, role).queue();
                            member.getUser().openPrivateChannel().queue(channel -> {
                                channel.sendMessage(":white_check_mark: **Success!** Removed the **" + role.getName() + "** role from you on **" + event.getGuild().getName() + "**!").queue();
                            });
                        }
                    }
                    if(event.getReactionEmote().isEmoji()) {
                        Scanner scan = new Scanner(file);
                        String contents = scan.nextLine();
                        String[] roleId = contents.split("\\s+");
                        String emoteId = Arrays.stream(roleId).skip(1).collect(Collectors.joining(" "));
                        Role role = event.getGuild().getRoleById(roleId[0]);
                        if(role == null) return;
                        if(!member.getRoles().contains(role)) return;
                        if(emoteId.equalsIgnoreCase(event.getReactionEmote().getEmoji())) {
                            event.getGuild().removeRoleFromMember(member, role).queue();
                            member.getUser().openPrivateChannel().queue(channel -> {
                                channel.sendMessage(":white_check_mark: **Success!** Removed the **" + role.getName() + "** role from you on **" + event.getGuild().getName() + "**!").queue();
                            });
                        }
                    }
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
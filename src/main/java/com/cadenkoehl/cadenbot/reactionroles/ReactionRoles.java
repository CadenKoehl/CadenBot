package com.cadenkoehl.cadenbot.reactionroles;

import com.cadenkoehl.cadenbot.CadenBot;
import com.cadenkoehl.cadenbot.Constants;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveAllEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.api.exceptions.HierarchyException;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.MalformedParameterizedTypeException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ReactionRoles extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        String guildId = event.getGuild().getId();
        String prefix = Constants.getPrefix(guildId);
        if(args[0].equalsIgnoreCase(prefix + "reactionrole") || args[0].equalsIgnoreCase(prefix + "rr")) {
            if(!event.getAuthor().isBot()) {
                if(event.getMember().hasPermission(Permission.MANAGE_ROLES)) {
                    try {
                        TextChannel channel = event.getMessage().getMentionedChannels().get(0);
                        Role role = event.getMessage().getMentionedRoles().get(0);
                        String content = Arrays.stream(args).skip(4).collect(Collectors.joining(" "));

                        if(event.getMessage().getEmotes().size() != 0) {
                            Emote emote = event.getMessage().getEmotes().get(0);
                            channel.sendMessage(content).queue((message -> {
                                try {
                                    message.addReaction(emote).queue();
                                }
                                catch (IllegalArgumentException ex) {
                                    message.delete().queue();
                                    event.getChannel().sendMessage("Error: The emoji must either be a default Discord emoji, or a custom emoji from this server! If you need help, please join the support server! (type " + prefix + "help)").queue();
                                }

                                File file = new File(CadenBot.dataDirectory + "reactionroles/" + message.getId() + ".txt");
                                if(!file.exists()) {
                                    try {
                                        file.createNewFile();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                FileWriter write;
                                try {
                                    write = new FileWriter(file);
                                    write.write(role.getId() + " " + emote.getId());
                                    write.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }));
                        }
                        if(event.getMessage().getEmotes().size() == 0) {
                            String emote = args[3];
                            channel.sendMessage(content).queue((message -> {
                                message.addReaction(emote).queue();

                                File file = new File(CadenBot.dataDirectory + "reactionroles/" + message.getId() + ".txt");
                                if(!file.exists()) {
                                    try {
                                        file.createNewFile();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                FileWriter write;
                                try {
                                    write = new FileWriter(file);
                                    write.write(role.getId() + " " + emote);
                                    write.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }));
                        }

                        event.getMessage().reply("**Success!** Your reaction role was saved in " + channel.getAsMention()).mentionRepliedUser(false).queue();
                    }
                    catch (IndexOutOfBoundsException ex) {
                        event.getMessage().reply("**Incomplete command!**\nUsage: `" + prefix + "reactionrole` `<#channel>` `<@role>` `<emote>` `[message-content]`").mentionRepliedUser(false).queue();
                    }
                    catch (IllegalArgumentException ex) {
                        event.getMessage().reply("You must supply message content, as I cannot send an empty message!").mentionRepliedUser(false).queue();
                    }
                }
                if(!event.getMember().hasPermission(Permission.MANAGE_ROLES)) {
                 event.getChannel().sendMessage("You must have the `manage_roles` permission to use that command!").queue();
                }
            }
        }
    }
    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) {
        String messageId = event.getMessageId();
        Member member = event.getMember();
        File file = new File(CadenBot.dataDirectory + "reactionroles/" + messageId + ".txt");
        if(file.exists()) {
            try {
                if(event.getReactionEmote().isEmote()) {
                    Scanner scan = new Scanner(file);
                    String contents = scan.nextLine();
                    String[] roleId = contents.split("\\s+");
                    String emoteId = Arrays.stream(roleId).skip(1).collect(Collectors.joining(" "));
                    if(emoteId.equalsIgnoreCase(event.getReactionEmote().getEmote().getId())) {
                        event.getGuild().addRoleToMember(member, event.getGuild().getRoleById(roleId[0])).queue();
                    }
                }
                if(event.getReactionEmote().isEmoji()) {
                    Scanner scan = new Scanner(file);
                    String contents = scan.nextLine();
                    String[] roleId = contents.split("\\s+");
                    String emoteId = Arrays.stream(roleId).skip(1).collect(Collectors.joining(" "));
                    if(emoteId.equalsIgnoreCase(event.getReactionEmote().getEmoji())) {
                        event.getGuild().addRoleToMember(member, event.getGuild().getRoleById(roleId[0])).queue();
                    }
                }
            }
            catch (FileNotFoundException exception) {
                exception.printStackTrace();
            }
        }
    }
    public void onGuildMessageReactionRemove(GuildMessageReactionRemoveEvent event) {
        String messageId = event.getMessageId();
        Member member = event.getMember();
        File file = new File(CadenBot.dataDirectory + "reactionroles/" + messageId + ".txt");
        if(file.exists()) {
            try {
                if(event.getReactionEmote().isEmote()) {
                    Scanner scan = new Scanner(file);
                    String contents = scan.nextLine();
                    String[] roleId = contents.split("\\s+");
                    String emoteId = Arrays.stream(roleId).skip(1).collect(Collectors.joining(" "));
                    if(emoteId.equalsIgnoreCase(event.getReactionEmote().getEmote().getId())) {
                        event.getGuild().removeRoleFromMember(member, event.getGuild().getRoleById(roleId[0])).queue();
                    }
                }
                if(event.getReactionEmote().isEmoji()) {
                    Scanner scan = new Scanner(file);
                    String contents = scan.nextLine();
                    String[] roleId = contents.split("\\s+");
                    String emoteId = Arrays.stream(roleId).skip(1).collect(Collectors.joining(" "));
                    if(emoteId.equalsIgnoreCase(event.getReactionEmote().getEmoji())) {
                        event.getGuild().removeRoleFromMember(member, event.getGuild().getRoleById(roleId[0])).queue();
                    }
                }
            } catch (FileNotFoundException exception) {
                exception.printStackTrace();
            }
        }
    }
}
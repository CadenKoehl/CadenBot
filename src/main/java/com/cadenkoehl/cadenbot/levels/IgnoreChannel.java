package com.cadenkoehl.cadenbot.levels;

import com.cadenkoehl.cadenbot.CadenBot;
import com.cadenkoehl.cadenbot.util.Constants;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.io.IOException;

public class IgnoreChannel extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        if(args[0].equalsIgnoreCase(Constants.getPrefix(event.getGuild().getId()) + "ignorechannel")) {
            if(!event.getAuthor().isBot()) {
                if(event.getMember().hasPermission(Permission.MANAGE_SERVER)) {
                    try {
                        String channelId = event.getMessage().getMentionedChannels().get(0).getId();
                        File file = new File(CadenBot.dataDirectory + "levels/ignored_channels/" + channelId + ".txt");
                        if(file.exists()) {
                            event.getChannel().sendMessage(event.getGuild().getTextChannelById(channelId).getAsMention() + " is already ignored").queue();
                        }
                        if(!file.exists()) {
                            file.createNewFile();
                            event.getChannel().sendMessage("Members will no longer gain xp from talking in " + event.getGuild().getTextChannelById(channelId).getAsMention()).queue();
                        }
                    }
                    catch (IOException e) {
                        event.getChannel().sendMessage(Constants.ERROR_MESSAGE).queue();
                    }
                    catch (IndexOutOfBoundsException ex) {
                        event.getChannel().sendMessage("Please specify a channel to ignore!").queue();
                    }
                }
                if(!event.getMember().hasPermission(Permission.MANAGE_SERVER)) {
                    event.getChannel().sendMessage("You must have the `manage_server` permission to use that command!").queue();
                }
            }
        }
        if(args[0].equalsIgnoreCase(Constants.getPrefix(event.getGuild().getId()) + "unignorechannel")) {
            if(!event.getAuthor().isBot()) {
                if(event.getMember().hasPermission(Permission.MANAGE_SERVER)) {
                    try {
                        String channelId = event.getMessage().getMentionedChannels().get(0).getId();
                        File file = new File(CadenBot.dataDirectory + "levels/ignored_channels/" + channelId + ".txt");
                        if(!file.exists()) {
                            event.getChannel().sendMessage(event.getGuild().getTextChannelById(channelId).getAsMention() + " is not ignored").queue();
                        }
                        if(file.exists()) {
                            file.delete();
                            event.getChannel().sendMessage("Members will now be able to gain xp from talking in " + event.getGuild().getTextChannelById(channelId).getAsMention()).queue();
                        }
                    }
                    catch (IndexOutOfBoundsException ex) {
                        event.getChannel().sendMessage("Please specify a channel!").queue();
                    }
                }
                if(!event.getMember().hasPermission(Permission.MANAGE_SERVER)) {
                    event.getChannel().sendMessage("You must have the `manage_server` permission to use that command!").queue();
                }
            }
        }
        if(args[0].equalsIgnoreCase(Constants.getPrefix(event.getGuild().getId()) + "ignoredchannels")) {
            if(!event.getAuthor().isBot()) {
                EmbedBuilder embed = new EmbedBuilder();
                embed.setAuthor(event.getGuild().getName(), null, event.getGuild().getIconUrl());
                embed.setTitle("List of ignored channels:");
                embed.setColor((int) Math.round(Math.random() * 999999));
                embed.setDescription("Users cannot earn xp in these channels");
                for(int i = 0; i < event.getGuild().getTextChannels().size(); i++) {
                    String channelId = event.getGuild().getTextChannels().get(i).getId();
                    File file = new File(CadenBot.dataDirectory + "levels/ignored_channels/" + channelId + ".txt");
                    if(file.exists()) {
                        embed.addField("", event.getGuild().getTextChannels().get(i).getAsMention(), false);
                    }
                }
                event.getChannel().sendMessage(embed.build()).queue();
            }
        }
    }
}

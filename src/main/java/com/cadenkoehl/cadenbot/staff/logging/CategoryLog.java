package com.cadenkoehl.cadenbot.staff.logging;

import com.cadenkoehl.cadenbot.CadenBot;
import com.cadenkoehl.cadenbot.util.EmbedColor;
import com.cadenkoehl.cadenbot.staff.commands.LogChannel;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.channel.category.CategoryCreateEvent;
import net.dv8tion.jda.api.events.channel.category.CategoryDeleteEvent;
import net.dv8tion.jda.api.events.channel.category.update.CategoryUpdateNameEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;

public class CategoryLog extends ListenerAdapter {
    private String channelId;
    public void onCategoryCreate(CategoryCreateEvent event) {
        channelId = LogChannel.getLogChannelId(event.getGuild());
        String name = event.getCategory().getName();
        EmbedBuilder embed = new EmbedBuilder();
        embed.setDescription("Category Created: " + name);
        embed.setColor(EmbedColor.GREEN);
        if (channelId != null) {
            TextChannel channel = event.getJDA().getTextChannelById(channelId);
            if(channel == null) {
                File file = new File(CadenBot.dataDirectory + "logging/" + channelId + ".txt");
                if(file.exists()) {
                    file.delete();
                }
                return;
            }
            channel.sendMessage(embed.build()).queue();
        }
    }
    public void onCategoryDelete(CategoryDeleteEvent event) {
        channelId = LogChannel.getLogChannelId(event.getGuild());
        String name = event.getCategory().getName();
        EmbedBuilder embed = new EmbedBuilder();
        embed.setDescription("Category Deleted: " + name);
        embed.setColor(EmbedColor.RED);
        if (channelId != null) {
            TextChannel channel = event.getJDA().getTextChannelById(channelId);
            if(channel == null) {
                File file = new File(CadenBot.dataDirectory + "logging/" + channelId + ".txt");
                if(file.exists()) {
                    file.delete();
                }
                return;
            }
            channel.sendMessage(embed.build()).queue();
        }
    }

    public void onCategoryUpdateName(CategoryUpdateNameEvent event) {
        String newName = event.getNewName();
        String oldName = event.getOldName();
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Category Name Updated");
        embed.setDescription("**" + oldName + "** ---> **" + newName + "**");
        embed.setColor(EmbedColor.DARK_BLUE);
        channelId = LogChannel.getLogChannelId(event.getGuild());
        if (channelId != null) {
            TextChannel channel = event.getJDA().getTextChannelById(channelId);
            if(channel == null) {
                File file = new File(CadenBot.dataDirectory + "logging/" + channelId + ".txt");
                if(file.exists()) {
                    file.delete();
                }
                return;
            }
            channel.sendMessage(embed.build()).queue();
        }
    }
}
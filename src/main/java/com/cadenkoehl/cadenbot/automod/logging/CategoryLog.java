package com.cadenkoehl.cadenbot.automod.logging;

import com.cadenkoehl.cadenbot.EmbedColor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.channel.category.CategoryCreateEvent;
import net.dv8tion.jda.api.events.channel.category.CategoryDeleteEvent;
import net.dv8tion.jda.api.events.channel.category.update.CategoryUpdateNameEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CategoryLog extends ListenerAdapter {

    public void onCategoryCreate(CategoryCreateEvent event) {
        String name = event.getCategory().getName();
        EmbedBuilder embed = new EmbedBuilder();
        embed.setDescription("Category Created: " + name);
        embed.setColor(EmbedColor.GREEN);
        event.getGuild().getTextChannelsByName("mod-logs", true).get(0).sendMessage(embed.build()).queue();
    }

    public void onCategoryDelete(CategoryDeleteEvent event) {
        String name = event.getCategory().getName();
        EmbedBuilder embed = new EmbedBuilder();
        embed.setDescription("Category Deleted: " + name);
        embed.setColor(EmbedColor.RED);
        event.getGuild().getTextChannelsByName("mod-logs", true).get(0).sendMessage(embed.build()).queue();
    }

    public void onCategoryUpdateName(CategoryUpdateNameEvent event) {
        String newName = event.getNewName();
        String oldName = event.getOldName();
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Category Name Updated");
        embed.setDescription("**" + oldName + "** ---> **" + newName + "**");
        embed.setColor(EmbedColor.DARK_BLUE);
        event.getGuild().getTextChannelsByName("mod-logs", true).get(0).sendMessage(embed.build()).queue();
    }
}

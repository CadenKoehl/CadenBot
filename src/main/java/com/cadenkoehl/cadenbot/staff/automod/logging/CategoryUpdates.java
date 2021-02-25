package com.cadenkoehl.cadenbot.staff.automod.logging;

import com.cadenkoehl.cadenbot.util.EmbedColor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.channel.category.CategoryCreateEvent;
import net.dv8tion.jda.api.events.channel.category.CategoryDeleteEvent;
import net.dv8tion.jda.api.events.channel.category.update.CategoryUpdateNameEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class CategoryUpdates extends ListenerAdapter {

    @Override
    public void onCategoryCreate(@NotNull CategoryCreateEvent event) {
        Category category = event.getCategory();
        EmbedBuilder embed = new EmbedBuilder();
        embed.setAuthor("Category Created", null, event.getGuild().getIconUrl());
        embed.setColor(EmbedColor.GREEN);
        embed.addField("Name:", category.getName(), false);
        embed.addField("ID:", category.getId(), false);
        AuditLogger.log(embed.build(), event.getGuild());
    }

    @Override
    public void onCategoryDelete(@NotNull CategoryDeleteEvent event) {
        Category category = event.getCategory();
        EmbedBuilder embed = new EmbedBuilder();
        embed.setAuthor("Category Deleted", null, event.getGuild().getIconUrl());
        embed.setColor(EmbedColor.RED);
        embed.addField("Name:", category.getName(), false);
        embed.addField("ID:", category.getId(), false);
        AuditLogger.log(embed.build(), event.getGuild());
    }

    @Override
    public void onCategoryUpdateName(@NotNull CategoryUpdateNameEvent event) {
        Guild guild = event.getGuild();
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(EmbedColor.BLUE);
        embed.setAuthor("Category Name Updated", null, guild.getIconUrl());
        embed.addField("New Name:", event.getNewName(), false);
        embed.addField("Old Name:", event.getOldName(), false);
        AuditLogger.log(embed.build(), guild);
    }
}

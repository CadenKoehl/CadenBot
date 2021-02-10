package com.cadenkoehl.cadenbot.staff.logging;

import com.cadenkoehl.cadenbot.util.EmbedColor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.channel.text.TextChannelCreateEvent;
import net.dv8tion.jda.api.events.channel.text.TextChannelDeleteEvent;
import net.dv8tion.jda.api.events.channel.text.update.*;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.junit.internal.ExactComparisonCriteria;

import java.util.Random;

public class TextChannelUpdates extends ListenerAdapter {
    @Override
    public void onTextChannelCreate(@NotNull TextChannelCreateEvent event) {
        TextChannel channel = event.getChannel();
        Guild guild = event.getGuild();
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(EmbedColor.GREEN);
        if(channel.isNews()) {
            embed.setAuthor("\uD83D\uDCE3 Announcement Channel Created", null, guild.getIconUrl());
        }
        else {
            embed.setAuthor("️Text Channel Created", null, guild.getIconUrl());
        }
        embed.addField("Channel:", channel.getAsMention(), false);
        Category category = channel.getParent();
        String id = channel.getId();
        String categoryName = "[none]";
        if(category != null) {
            categoryName = category.getName();
        }
        embed.addField("Category:", categoryName, false);
        embed.addField("ID:", id, false);
        Logger.log(embed.build(), guild);
    }

    @Override
    public void onTextChannelDelete(@NotNull TextChannelDeleteEvent event) {
        TextChannel channel = event.getChannel();
        Guild guild = event.getGuild();
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(EmbedColor.RED);
        if(channel.isNews()) {
            embed.setAuthor("\uD83D\uDCE3 Announcement Channel Deleted", null, guild.getIconUrl());
        }
        else {
            embed.setAuthor("️Text Channel Deleted", null, guild.getIconUrl());
        }
        embed.addField("Channel:", channel.getName(), false);
        Category category = channel.getParent();
        String id = channel.getId();
        String categoryName = "[none]";
        if(category != null) {
            categoryName = category.getName();
        }
        embed.addField("Category:", categoryName, false);
        embed.addField("ID:", id, false);
        Logger.log(embed.build(), guild);
    }

    @Override
    public void onTextChannelUpdateName(@NotNull TextChannelUpdateNameEvent event) {
        TextChannel channel = event.getChannel();
        Guild guild = event.getGuild();
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(EmbedColor.BLUE);
        embed.setAuthor("Text Channel Name Updated", null, guild.getIconUrl());
        embed.setDescription("**Channel:** " + channel.getAsMention());
        embed.addField("Old Name:", event.getOldName(), true);
        embed.addField("New Name:", event.getNewName(), true);
        Logger.log(embed.build(), guild);
    }

    @Override
    public void onTextChannelUpdateTopic(@NotNull TextChannelUpdateTopicEvent event) {
        String newTopic = event.getNewTopic();
        String oldTopic = event.getOldTopic();
        EmbedBuilder embed = new EmbedBuilder();
        if (newTopic == null) {
            newTopic = "[no topic]";
        } else if (oldTopic == null) {
            oldTopic = "[no topic]";
        }
        embed.setAuthor("Channel Topic Updated", null, event.getGuild().getIconUrl());
        embed.addField("Channel:", event.getChannel().getAsMention(), false);
        embed.addField("New Topic:", newTopic, false);
        embed.addField("Old Topic:", oldTopic, false);
        embed.setColor(EmbedColor.GREEN);
        Logger.log(embed.build(), event.getGuild());
    }

    @Override
    public void onTextChannelUpdateNSFW(@NotNull TextChannelUpdateNSFWEvent event) {
        boolean nsfw = event.getChannel().isNSFW();
        EmbedBuilder embed = new EmbedBuilder();
        embed.setAuthor("Text Channel Settings Updated", null, event.getGuild().getIconUrl());
        embed.addField("Channel:", event.getChannel().getAsMention(), true);
        if (nsfw) {
            embed.addField("Setting:", "Turned on NSFW \uD83D\uDE0F", true);
            embed.setColor(EmbedColor.GREEN);
        } else {
            embed.addField("Setting:", "Turned off NSFW", true);
            embed.setColor(EmbedColor.RED);
        }
        Logger.log(embed.build(), event.getGuild());
    }

    @Override
    public void onTextChannelUpdateNews(@NotNull TextChannelUpdateNewsEvent event) {
        boolean isNews = event.getChannel().isNews();
        EmbedBuilder embed = new EmbedBuilder();
        embed.setAuthor("Text Channel Settings Updated", null, event.getGuild().getIconUrl());
        embed.addField("Channel:", event.getChannel().getAsMention(), true);
        if (isNews) {
            embed.addField("Setting:", ":white_check_mark: Made " + event.getChannel().getName() + " an announcement channel", true);
            embed.setColor(EmbedColor.GREEN);
        } else {
            embed.addField("Setting:", ":no_entry: Made " + event.getChannel().getName() + " not an announcement channel", true);
            embed.setColor(EmbedColor.RED);
        }
        Logger.log(embed.build(), event.getGuild());
    }

    @Override
    public void onTextChannelUpdateParent(@NotNull TextChannelUpdateParentEvent event) {
        Category newCat = event.getNewParent();
        Category oldCat = event.getOldParent();
        Guild guild = event.getGuild();
        String newCatName = "[none]";
        String oldCatName = "[none]";

        if(newCat != null) {
            newCatName = newCat.getName();
        }

        if(oldCat != null) {
            oldCatName = oldCat.getName();
        }

        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(EmbedColor.BLUE);
        embed.setAuthor("Text Channel Category Updated", null, guild.getIconUrl());
        embed.addField("Channel", event.getChannel().getAsMention(), false);
        embed.addField("New Category:", newCatName, false);
        embed.addField("Old Category:", oldCatName, false);
        Logger.log(embed.build(), guild);
    }
}
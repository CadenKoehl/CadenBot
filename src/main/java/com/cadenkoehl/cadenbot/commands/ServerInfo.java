package com.cadenkoehl.cadenbot.commands;

import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandCategory;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandEvent;
import com.cadenkoehl.cadenbot.util.exceptions.IncorrectUsageException;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

import java.time.format.TextStyle;
import java.util.Locale;

public class ServerInfo extends Command {

    @Override
    public void execute(CommandEvent event) throws IncorrectUsageException {
        Member owner = event.getGuild().getOwner();
        String gName = event.getGuild().getName();
        TextChannel sysChannel = event.getGuild().getSystemChannel();
        int dayCreated = event.getGuild().getTimeCreated().getDayOfMonth();
        int yearCreated = event.getGuild().getTimeCreated().getYear();
        String monthCreated = event.getGuild().getTimeCreated().getMonth().getDisplayName(TextStyle.FULL,  Locale.US);
        String dayOfWeekCreated = event.getGuild().getTimeCreated().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.US);
        int memberCount = event.getGuild().getMemberCount();
        int boostCount = event.getGuild().getBoostCount();
        int boostTier = event.getGuild().getBoostTier().getKey();
        int txtChannelCount = event.getGuild().getTextChannels().size();
        int vcCount = event.getGuild().getVoiceChannels().size();

        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("**Server Info**:\n**--------------**");

        if(owner != null) {
            embed.appendDescription("\n**Owner**: *" + owner.getUser().getAsTag() + "*");
        }
        embed.appendDescription("\n**Members**: *" + memberCount + "*");
        embed.appendDescription("\n**Text Channels**: *" + txtChannelCount + "*");
        embed.appendDescription("\n**Voice Channels**: *" + vcCount + "*");
        embed.appendDescription("\n**Created**: *" + dayOfWeekCreated + ", " + monthCreated + " " + dayCreated + ", " + yearCreated + "*");
        embed.appendDescription("\n**Custom Prefix**: " + event.getPrefix());
        if(sysChannel != null) {
            embed.appendDescription("\n**System Channel**: *#" + sysChannel.getName() + "*");
        }
        if(boostCount != 0) {
            embed.appendDescription("\n**Boosts**: *" + boostCount + "*\n" + "**Boost Tier**: *" + boostTier + "*");
        }
        embed.setColor((int) Math.round(Math.random() * 999999));
        embed.setAuthor(gName, null, event.getGuild().getIconUrl());
        event.getChannel().sendMessage(embed.build()).queue();
    }

    @Override
    public String getName() {
        return "serverinfo";
    }

    @Override
    public String getDescription() {
        return "Info about the current server!";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.COMMAND;
    }

    @Override
    public Permission getRequiredPermission() {
        return null;
    }

    @Override
    public String getUsage(String prefix) {
        return "serverinfo`";
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }
}

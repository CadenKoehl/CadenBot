package com.cadenkoehl.cadenbot.commands;

import com.cadenkoehl.cadenbot.CadenBot;
import com.cadenkoehl.cadenbot.Constants;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Scanner;

public class ServerInfo extends ListenerAdapter {
    private static String id;
    private static String prefix = "-";
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        id = event.getGuild().getId();
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        if(args[0].equalsIgnoreCase(getPrefix() + "serverinfo")) {
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
                embed.addField("**Owner**: *" + owner.getUser().getAsTag() + "*", "", false);
            }
            embed.addField("**Members**: *" + memberCount + "*", "", false);
            embed.addField("**Text Channels**: *" + txtChannelCount + "*", "", false);
            embed.addField("**Voice Channels**: *" + vcCount + "*", "", false);
            embed.addField("**Created**: *" + dayOfWeekCreated + ", " + monthCreated + " " + dayCreated + ", " + yearCreated + "*", "", false);
            embed.addField("**Custom Prefix**: " + getPrefix() + "", "", false);
            if(sysChannel != null) {
                embed.addField("**System Channel**: *#" + sysChannel.getName() + "*", "", false);
            }
            if(boostCount != 0) {
                embed.addField("**Boosts**: *" + boostCount + "*", "**Boost Tier**: *" + boostTier + "*", false);
            }
            embed.setColor((int) Math.round(Math.random() * 999999));
            embed.setAuthor(gName, null, event.getGuild().getIconUrl());
            event.getChannel().sendMessage(embed.build()).queue();
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

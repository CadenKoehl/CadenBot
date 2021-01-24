package com.cadenkoehl.cadenbot.staff.logging;

import com.cadenkoehl.cadenbot.CadenBot;
import com.cadenkoehl.cadenbot.staff.commands.LogChannel;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.time.format.TextStyle;
import java.util.Locale;

public class JoinLeaveLog extends ListenerAdapter {
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        Member member = event.getMember();
        User user = member.getUser();
        String name = user.getName();
        String dayOfWeekCreated = user.getTimeCreated().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.US);
        String monthCreated = user.getTimeCreated().getMonth().getDisplayName(TextStyle.FULL, Locale.US);
        int dayCreated = user.getTimeCreated().getDayOfMonth();
        int yearCreated = user.getTimeCreated().getYear();
        String id = user.getId();
        String avatarURL = user.getEffectiveAvatarUrl();

        EmbedBuilder embed = new EmbedBuilder();
        embed.setAuthor(member.getUser().getAsTag() + " joined the server", null, member.getUser().getEffectiveAvatarUrl());
        if(user.isBot()) {
            embed.setTitle(name + " **[BOT]**'s user info:\n------------------");
        }
        if(!user.isBot()) {
            embed.setTitle(name + "'s user info:\n------------------");
        }
        embed.addField("**Account Created**: *" + dayOfWeekCreated + ", " + monthCreated + " " + dayCreated + ", " + yearCreated + "*","", false);
        embed.addField("**User ID**: *" + id + "*","", false);
        embed.addField("**Avatar URL**: ","[Click Here](" + avatarURL + " \"Click Here\")", false);
        embed.setColor((int) Math.round(Math.random() * 999999));
        String channelId;
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

    public void onGuildMemberRemove(GuildMemberRemoveEvent event) {
        User user = event.getUser();
        String name = user.getName();
        String dayOfWeekCreated = user.getTimeCreated().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.US);
        String monthCreated = user.getTimeCreated().getMonth().getDisplayName(TextStyle.FULL, Locale.US);
        int dayCreated = user.getTimeCreated().getDayOfMonth();
        int yearCreated = user.getTimeCreated().getYear();
        String id = user.getId();
        String avatarURL = user.getEffectiveAvatarUrl();

        EmbedBuilder embed = new EmbedBuilder();
        embed.setAuthor(user.getAsTag() + " left the server", null, user.getEffectiveAvatarUrl());
        if(user.isBot()) {
            embed.setTitle(name + " **[BOT]**'s user info:\n------------------");
        }
        if(!user.isBot()) {
            embed.setTitle(name + "'s user info:\n------------------");
        }
        embed.addField("**Account Created**: *" + dayOfWeekCreated + ", " + monthCreated + " " + dayCreated + ", " + yearCreated + "*","", false);
        embed.addField("**User ID**: *" + id + "*","", false);
        embed.addField("**Avatar URL**: ","[Click Here](" + avatarURL + " \"Click Here\")", false);
        embed.setColor((int) Math.round(Math.random() * 999999));
        String channelId;
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

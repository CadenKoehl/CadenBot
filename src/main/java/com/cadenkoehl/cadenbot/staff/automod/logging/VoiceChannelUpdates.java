package com.cadenkoehl.cadenbot.staff.automod.logging;

import com.cadenkoehl.cadenbot.util.EmbedColor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.channel.voice.VoiceChannelCreateEvent;
import net.dv8tion.jda.api.events.channel.voice.VoiceChannelDeleteEvent;
import net.dv8tion.jda.api.events.channel.voice.update.VoiceChannelUpdateNameEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceStreamEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class VoiceChannelUpdates extends ListenerAdapter {
    @Override
    public void onVoiceChannelCreate(@NotNull VoiceChannelCreateEvent event) {
        EmbedBuilder embed = new EmbedBuilder();
        Guild guild = event.getGuild();
        VoiceChannel channel = event.getChannel();
        embed.setAuthor("Voice Channel Created", null, guild.getIconUrl());
        embed.setColor(EmbedColor.GREEN);
        embed.addField("Channel:", channel.getName(), false);
        Category category = channel.getParent();
        String id = channel.getId();
        String categoryName = "[none]";
        if(category != null) {
            categoryName = category.getName();
        }
        embed.addField("Category:", categoryName, false);
        embed.addField("ID:", id, false);
        AuditLogger.log(embed.build(), guild);
    }

    @Override
    public void onVoiceChannelDelete(@NotNull VoiceChannelDeleteEvent event) {
        EmbedBuilder embed = new EmbedBuilder();
        Guild guild = event.getGuild();
        VoiceChannel channel = event.getChannel();
        embed.setAuthor("Voice Channel Deleted", null, guild.getIconUrl());
        embed.setColor(EmbedColor.RED);
        embed.addField("Channel:", channel.getName(), false);
        Category category = channel.getParent();
        String id = channel.getId();
        String categoryName = "[none]";
        if(category != null) {
            categoryName = category.getName();
        }
        embed.addField("Category:", categoryName, false);
        embed.addField("ID:", id, false);
        AuditLogger.log(embed.build(), guild);
    }

    @Override
    public void onVoiceChannelUpdateName(@NotNull VoiceChannelUpdateNameEvent event) {
       VoiceChannel channel = event.getChannel();
        Guild guild = event.getGuild();
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(EmbedColor.BLUE);
        embed.setAuthor("Voice Channel Name Updated", null, guild.getIconUrl());
        embed.setDescription("**Channel:** " + channel.getName());
        embed.addField("Old Name:", event.getOldName(), true);
        embed.addField("New Name:", event.getNewName(), true);
        AuditLogger.log(embed.build(), guild);
    }

    @Override
    public void onGuildVoiceJoin(@NotNull GuildVoiceJoinEvent event) {
        Member member = event.getMember();
        User user = member.getUser();
        Guild guild = event.getGuild();
        EmbedBuilder embed = new EmbedBuilder();
        embed.setAuthor(user.getAsTag() + " joined a voice channel", null, user.getEffectiveAvatarUrl());
        embed.setColor(EmbedColor.GREEN);
        embed.setDescription("**Channel:** " + event.getChannelJoined().getName());
        AuditLogger.log(embed.build(), guild);
    }

    @Override
    public void onGuildVoiceLeave(@NotNull GuildVoiceLeaveEvent event) {
        Member member = event.getMember();
        User user = member.getUser();
        Guild guild = event.getGuild();
        EmbedBuilder embed = new EmbedBuilder();
        embed.setAuthor(user.getAsTag() + " left a voice channel", null, user.getEffectiveAvatarUrl());
        embed.setColor(EmbedColor.RED);
        embed.setDescription("**Channel:** " + event.getChannelLeft().getName());
        AuditLogger.log(embed.build(), guild);
    }

    @Override
    public void onGuildVoiceMove(@NotNull GuildVoiceMoveEvent event) {
        VoiceChannel newChannel = event.getChannelJoined();
        VoiceChannel oldChannel = event.getChannelLeft();
        Member member = event.getMember();
        User user = member.getUser();
        EmbedBuilder embed = new EmbedBuilder();
        embed.setAuthor(user.getAsTag() + " switched voice channels", null, user.getEffectiveAvatarUrl());
        embed.setDescription("Left **" + oldChannel.getName() + "** and joined **" + newChannel.getName() + "**");
        embed.setColor(EmbedColor.GREEN);
        AuditLogger.log(embed.build(), event.getGuild());
    }

    @Override
    public void onGuildVoiceStream(@NotNull GuildVoiceStreamEvent event) {
        boolean isStreaming = event.getVoiceState().isStream();
        Member member = event.getMember();
        User user = member.getUser();
        VoiceChannel channel = event.getVoiceState().getChannel();
        EmbedBuilder embed = new EmbedBuilder();
        if(isStreaming) {
            embed.setColor(EmbedColor.GREEN);
            embed.setAuthor(user.getAsTag() + " started screen sharing", null, user.getEffectiveAvatarUrl());
        }
        if(!isStreaming) {
            embed.setColor(EmbedColor.RED);
            embed.setAuthor(user.getAsTag() + " ended their screen share", null, user.getEffectiveAvatarUrl());
        }
        if(channel == null) {
            return;
        }
        embed.addField("Channel:", channel.getName(), false);
        AuditLogger.log(embed.build(), event.getGuild());
    }
}
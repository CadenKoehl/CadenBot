package com.cadenkoehl.cadenbot.music;

import com.cadenkoehl.cadenbot.music.lavaplayer.MusicManager;
import com.cadenkoehl.cadenbot.music.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;
import org.jetbrains.annotations.NotNull;

import java.util.Timer;
import java.util.TimerTask;

public class TimeOut extends ListenerAdapter {

    private static final int TIMEOUT_SECONDS = 120;

    @Override
    public void onGuildVoiceLeave(@NotNull GuildVoiceLeaveEvent event) {
        Member selfMember = event.getGuild().getSelfMember();
        GuildVoiceState selfVoiceState = selfMember.getVoiceState();
        VoiceChannel channelLeft = event.getChannelLeft();
        channelLeft.getMembers();
        if(channelLeft.getMembers().size() != 1) {
            return;
        }
        if(!selfVoiceState.inVoiceChannel()) {
            return;
        }
        if(selfVoiceState.getChannel().equals(channelLeft)) {
            timeOut(event.getGuild());
        }
    }

    @Override
    public void onGuildVoiceMove(@NotNull GuildVoiceMoveEvent event) {
        Member selfMember = event.getGuild().getSelfMember();
        GuildVoiceState selfVoiceState = selfMember.getVoiceState();
        VoiceChannel channelLeft = event.getChannelLeft();
        channelLeft.getMembers();
        if(channelLeft.getMembers().size() != 1) {
            return;
        }
        if(!selfVoiceState.inVoiceChannel()) {
            return;
        }
        if(selfVoiceState.getChannel().equals(channelLeft)) {
            timeOut(event.getGuild());
        }
    }
    public void timeOut(Guild guild) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                MusicManager musicManager = PlayerManager.getInstance().getMusicManager(guild);
                musicManager.scheduler.player.stopTrack();
                musicManager.scheduler.player.setPaused(false);
                musicManager.scheduler.repeating = false;
                musicManager.scheduler.queue.clear();
                AudioManager audioManager = guild.getAudioManager();
                VoiceChannel voiceChannel = audioManager.getConnectedChannel();
                if (voiceChannel == null) return;

                if(voiceChannel.getMembers().size() > 1) return;

                audioManager.closeAudioConnection();

                TextChannel channel = MusicManager.musicTextChannel.get(guild);
                MusicManager.musicTextChannel.remove(guild);

                if(channel != null) {
                    channel.sendMessage("Left **" + voiceChannel.getName() + "** due to inactivity").queue();
                }
            }
        }, TIMEOUT_SECONDS * 1000);
    }
}

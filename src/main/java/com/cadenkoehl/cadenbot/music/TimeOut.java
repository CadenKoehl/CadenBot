package com.cadenkoehl.cadenbot.music;

import com.cadenkoehl.cadenbot.music.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;
import org.jetbrains.annotations.NotNull;

public class TimeOut extends ListenerAdapter {
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
            PlayerManager.getInstance().getMusicManager(event.getGuild()).scheduler.player.stopTrack();
            PlayerManager.getInstance().getMusicManager(event.getGuild()).scheduler.queue.clear();
            AudioManager audioManager = event.getGuild().getAudioManager();
            audioManager.closeAudioConnection();
        }
    }
}

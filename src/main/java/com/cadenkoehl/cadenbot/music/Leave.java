package com.cadenkoehl.cadenbot.music;

import com.cadenkoehl.cadenbot.util.Constants;
import com.cadenkoehl.cadenbot.music.lavaplayer.MusicManager;
import com.cadenkoehl.cadenbot.music.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;


public class Leave extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        String prefix = Constants.getPrefix(event.getGuild().getId());
        if(args[0].equalsIgnoreCase(prefix + "leave") || args[0].equalsIgnoreCase(prefix + "dc") || args[0].equalsIgnoreCase(prefix + "disconnect")) {
            Member bot = event.getGuild().getSelfMember();
            Member member = event.getMember();
            GuildVoiceState selfVoiceState = bot.getVoiceState();
            if(selfVoiceState.inVoiceChannel()) {
                VoiceChannel botChannel = selfVoiceState.getChannel();
                if(member.getVoiceState().inVoiceChannel()) {
                    VoiceChannel memberChannel = member.getVoiceState().getChannel();
                    if(botChannel == memberChannel) {
                        MusicManager musicManager = PlayerManager.getInstance().getMusicManager(event.getGuild());
                        if(musicManager.scheduler.player.getPlayingTrack() != null) {
                            musicManager.scheduler.repeating = false;
                            musicManager.scheduler.player.stopTrack();
                            musicManager.scheduler.queue.clear();
                        }
                        AudioManager audioManager = event.getGuild().getAudioManager();
                        audioManager.closeAudioConnection();
                        event.getChannel().sendMessage(":white_check_mark: Left **" + botChannel.getName() + "**").queue();
                    }
                    if(botChannel != memberChannel) {
                        event.getChannel().sendMessage(":x: You must be in the same voice channel as me to use this command!").queue();
                    }
                }
                if(!member.getVoiceState().inVoiceChannel()) {
                    event.getChannel().sendMessage(":x: You must be in a voice channel to use this command!").queue();
                }
            }
            if(!bot.getVoiceState().inVoiceChannel()) {
                event.getChannel().sendMessage(":x: I am not in a voice channel!").queue();
            }
        }
    }
}

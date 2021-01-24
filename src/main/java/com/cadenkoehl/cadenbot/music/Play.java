package com.cadenkoehl.cadenbot.music;

import com.cadenkoehl.cadenbot.Constants;
import com.cadenkoehl.cadenbot.music.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Play extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        String prefix = Constants.getPrefix(event.getGuild().getId());
        if(args[0].equalsIgnoreCase(prefix + "play") || args[0].equalsIgnoreCase(prefix + "p")) {
            if(event.getAuthor().isBot()) { return; }

            if(args.length < 2) {
                event.getChannel().sendMessage(":x: Please specify a song to play!").queue();
                return;
            }
            Member selfMember = event.getGuild().getSelfMember();
            GuildVoiceState selfVoiceState = selfMember.getVoiceState();
            Member member = event.getMember();
            GuildVoiceState memberVoiceState = member.getVoiceState();

            if(!memberVoiceState.inVoiceChannel()) {
                event.getChannel().sendMessage(":x: You must be in a voice channel to use this command!").queue();
                return;
            }
            if(!selfVoiceState.inVoiceChannel()) {
                VoiceChannel memberChannel = memberVoiceState.getChannel();
                AudioManager audioManager = event.getGuild().getAudioManager();
                audioManager.openAudioConnection(memberChannel);
                event.getChannel().sendMessage("🎶 Joining **" + memberChannel.getName() + "**...").queue();
            }
            String url = Arrays.stream(args).skip(1).collect(Collectors.joining(" "));
            event.getChannel().sendMessage("🔎 Searching...").queue();
            if(!isUrl(url)) {
                url = "ytsearch:" + url;
            }
            PlayerManager.getInstance().loadAndPlay(event.getChannel(), url);
        }
    }
    private boolean isUrl(String url) {
        try {
            new URI(url);
            return true;
        }
        catch (URISyntaxException ex) {
            return false;
        }
    }
}
















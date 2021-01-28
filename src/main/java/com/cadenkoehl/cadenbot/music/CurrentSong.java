package com.cadenkoehl.cadenbot.music;

import com.cadenkoehl.cadenbot.util.Constants;
import com.cadenkoehl.cadenbot.music.lavaplayer.MusicManager;
import com.cadenkoehl.cadenbot.music.lavaplayer.PlayerManager;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class CurrentSong extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        String prefix = Constants.getPrefix(event.getGuild().getId());
        if(args[0].equalsIgnoreCase(prefix + "currentsong")
                || args[0].equalsIgnoreCase(prefix + "nowplaying")
                || args[0].equalsIgnoreCase(prefix + "playing")
                || args[0].equalsIgnoreCase(prefix + "np")) {


            if(event.getAuthor().isBot()) { return; }

            Member selfMember = event.getGuild().getSelfMember();
            Member member = event.getMember();
            GuildVoiceState memberVoiceState = member.getVoiceState();
            GuildVoiceState selfVoiceState = selfMember.getVoiceState();

            if(!memberVoiceState.inVoiceChannel()) {
                event.getChannel().sendMessage(":x: You must be in a voice channel to use this command!").queue();
                return;
            }
            VoiceChannel memberChannel = memberVoiceState.getChannel();

            if(!selfVoiceState.inVoiceChannel()) {
                event.getChannel().sendMessage(":x: I am not in a voice channel!").queue();
                return;
            }
            VoiceChannel selfChannel = selfVoiceState.getChannel();

            if(!selfChannel.equals(memberChannel)) {
               event.getChannel().sendMessage(":x: I am currently in a different channel!").queue();
               return;
            }

            MusicManager musicManager = PlayerManager.getInstance().getMusicManager(event.getGuild());
            if (musicManager.scheduler.player.getPlayingTrack() == null) {
                event.getChannel().sendMessage(":x: I am not playing anything!").queue();
                return;
            }

            AudioTrack track = musicManager.scheduler.player.getPlayingTrack();
            String title = track.getInfo().title;
            String author = track.getInfo().author;
            String url = track.getInfo().uri;
            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor((int) Math.round(Math.random() * 999999));
            embed.setAuthor("YouTube", null, "https://logos-world.net/wp-content/uploads/2020/04/YouTube-Emblem.png");
            embed.setTitle("----- " + title + " -----");
            embed.setDescription(
                    "**Channel**: `" + author + "`\n" +
                    "**URL**: " + url + "\n");
            event.getChannel().sendMessage(embed.build()).queue();
        }
    }
}

package com.cadenkoehl.cadenbot.music;

import com.cadenkoehl.cadenbot.util.Constants;
import com.cadenkoehl.cadenbot.music.lavaplayer.MusicManager;
import com.cadenkoehl.cadenbot.music.lavaplayer.PlayerManager;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class Queue extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        String prefix = Constants.getPrefix(event.getGuild().getId());
        if(args[0].equalsIgnoreCase(prefix + "queue")) {
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

            BlockingQueue<AudioTrack> queue = musicManager.scheduler.queue;
            if(queue.isEmpty()) {
                event.getChannel().sendMessage(":x: There is nothing in the queue!").queue();
                return;
            }

            final int trackCount = Math.min(queue.size(), 20);
            final List<AudioTrack> trackList = new ArrayList<>(queue);
            EmbedBuilder embed = new EmbedBuilder();
            embed.setAuthor("🎶 " + event.getGuild().getName() + " 🎶", null, event.getGuild().getIconUrl());
            embed.setTitle("----- Current Queue -----");
            embed.setColor((int) Math.round(Math.random() * 999999));
            StringBuilder songs = new StringBuilder();

            for(int i = 0; i < trackCount; i++) {
                AudioTrack track = trackList.get(i);
                AudioTrackInfo info = track.getInfo();
                String title = info.title;
                String author = info.author;
                songs.append((i + 1) + ". *" + title + "* - `" + author + "`\n");
            }
            embed.setDescription(songs);
            event.getChannel().sendMessage(embed.build()).queue();
        }
    }
}

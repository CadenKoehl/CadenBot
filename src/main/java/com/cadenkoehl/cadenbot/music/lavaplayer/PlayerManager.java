package com.cadenkoehl.cadenbot.music.lavaplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerManager {
    private static PlayerManager INSTANCE;

    private final Map<Long, MusicManager> musicManagers;
    private final AudioPlayerManager audioPlayerManager;

    public PlayerManager() {
        this.musicManagers = new HashMap<>();
        this.audioPlayerManager = new DefaultAudioPlayerManager();

        AudioSourceManagers.registerRemoteSources(this.audioPlayerManager);
        AudioSourceManagers.registerLocalSource(this.audioPlayerManager);
    }

    public MusicManager getMusicManager(Guild guild) {
        return this.musicManagers.computeIfAbsent(guild.getIdLong(), (guildId) -> {
            final MusicManager MusicManager = new MusicManager(this.audioPlayerManager);

            guild.getAudioManager().setSendingHandler(MusicManager.getSendHandler());

            return MusicManager;
        });
    }

    public void loadAndPlay(TextChannel channel, String trackUrl) {
        final MusicManager musicManager = this.getMusicManager(channel.getGuild());

        this.audioPlayerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                musicManager.scheduler.queue(track);
                String title = track.getInfo().title;
                String author = track.getInfo().author;

                channel.sendMessage("🎶 Added `" + title + "` by `" + author + "` to the queue! 🎶").queue();
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                List<AudioTrack> tracks = playlist.getTracks();
                AudioTrack track = tracks.get(0);
                String title = track.getInfo().title;
                String author = track.getInfo().author;
                channel.sendMessage("🎶 Added `" + title + "` by `" + author + "` to the queue! 🎶").queue();
                musicManager.scheduler.queue(track);
            }

            @Override
            public void noMatches() {
                channel.sendMessage(":x: No matches were found for your search").queue();
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                channel.sendMessage(":x: Invalid link or URL!").queue();
            }
        });
    }

    public static PlayerManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PlayerManager();
        }
        return INSTANCE;
    }
}

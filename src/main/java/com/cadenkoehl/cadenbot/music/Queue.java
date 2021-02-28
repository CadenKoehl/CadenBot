package com.cadenkoehl.cadenbot.music;

import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandCategory;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandEvent;
import com.cadenkoehl.cadenbot.music.lavaplayer.MusicManager;
import com.cadenkoehl.cadenbot.music.lavaplayer.PlayerManager;
import com.cadenkoehl.cadenbot.util.exceptions.IncorrectUsageException;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class Queue extends Command {
    @Override
    public void execute(CommandEvent event) throws IncorrectUsageException {

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

    @Override
    public String getName() {
        return "queue";
    }

    @Override
    public String getDescription() {
        return "View the current queue!";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.MUSIC;
    }

    @Override
    public Permission getRequiredPermission() {
        return null;
    }

    @Override
    public String getUsage(String prefix) {
        return "queue`";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"q"};
    }
}

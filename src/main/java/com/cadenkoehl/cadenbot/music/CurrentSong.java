package com.cadenkoehl.cadenbot.music;

import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandCategory;
import com.cadenkoehl.cadenbot.util.Constants;
import com.cadenkoehl.cadenbot.music.lavaplayer.MusicManager;
import com.cadenkoehl.cadenbot.music.lavaplayer.PlayerManager;
import com.cadenkoehl.cadenbot.util.exceptions.IncorrectUsageException;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class CurrentSong extends Command {

    @Override
    public void execute(GuildMessageReceivedEvent event) throws IncorrectUsageException {

        MusicManager musicManager = PlayerManager.getInstance().getMusicManager(event.getGuild());
        if (musicManager.scheduler.player.getPlayingTrack() == null) {
            event.getChannel().sendMessage(":x: I am not playing anything!").queue();
            return;
        }

        AudioTrack track = musicManager.scheduler.player.getPlayingTrack();
        String title = track.getInfo().title;
        String author = track.getInfo().author;
        String url = track.getInfo().uri;
        String videoURL = url.replace("https://www.youtube.com/watch?v=", "");
        String thumbnailImage = "http://i3.ytimg.com/vi/" + videoURL +  "/maxresdefault.jpg";
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor((int) Math.round(Math.random() * 999999));
        embed.setAuthor("YouTube", null, "https://logos-world.net/wp-content/uploads/2020/04/YouTube-Emblem.png");
        embed.setThumbnail(thumbnailImage);
        embed.setTitle("----- " + title + " -----");
        embed.setDescription(
                "**Channel**: `" + author + "`\n" +
                        "**URL**: " + url + "\n");
        event.getChannel().sendMessage(embed.build()).queue();
    }

    @Override
    public String getName() {
        return "np";
    }

    @Override
    public String getDescription() {
        return "Details of the current song!";
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
        return "np`";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"currentsong", "nowplaying"};
    }
}

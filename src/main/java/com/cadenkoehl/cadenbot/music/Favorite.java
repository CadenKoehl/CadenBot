package com.cadenkoehl.cadenbot.music;

import com.cadenkoehl.cadenbot.CadenBot;
import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandCategory;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandEvent;
import com.cadenkoehl.cadenbot.music.lavaplayer.PlayerManager;
import com.cadenkoehl.cadenbot.util.Constants;
import com.cadenkoehl.cadenbot.util.EmbedColor;
import com.cadenkoehl.cadenbot.util.exceptions.IncorrectUsageException;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Favorite extends Command {

    @Override
    public void execute(CommandEvent event) throws IncorrectUsageException {

        String[] args = event.getArgs();
        String prefix = event.getPrefix();

        if(args.length == 1) {
            event.getChannel().sendMessage(":x: **Incomplete Command!** Type `" + prefix + "help` `music`").queue();
            return;
        }

        if(args[1].equalsIgnoreCase("add")) {
            if(args.length == 2) {
                event.getChannel().sendMessage(":x: **Incomplete Command!** Type `" + prefix + "help` `favorite add`").queue();
                return;
            }

            if(!isUrl(args[2])) {
                event.getChannel().sendMessage(":x: You must specify a YouTube video link to add as a favorite!").queue();
                return;
            }

            if(!args[2].contains("https://www.youtube.com/watch")) {
                event.getChannel().sendMessage(":x: You must specify a YouTube video link to add as a favorite!").queue();
                return;
            }

            File dir = new File(CadenBot.dataDirectory + "music/favorites/" + event.getAuthor().getId());
            if(dir.mkdirs()) {
                System.out.println("Successfully created directory " + dir.getPath());
            }
            File file = new File(dir, args[2].replace("/", "\\@"));
            if(file.exists()) {
                event.getChannel().sendMessage(":x: You already favorited that song!").queue();
                return;
            }

            if(dir.list().length == 20) {
                event.getChannel().sendMessage(":x: You have reached your limit of songs that you can favorite!").queue();
                return;
            }

            try {
                if(file.createNewFile()) {
                    event.getChannel().sendMessage("Song was successfully added to your favorites!").queue();
                }
                else {
                    event.getChannel().sendMessage(Constants.ERROR_MESSAGE).queue();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
                event.getChannel().sendMessage(Constants.ERROR_MESSAGE).queue();
            }
        }
        else if(args[1].equalsIgnoreCase("remove")) {
            if(args.length == 2) {
                event.getChannel().sendMessage(":x: **Incomplete Command!** Type `" + prefix + "help` `favorite remove`").queue();
                return;
            }

            if(!isUrl(args[2])) {
                event.getChannel().sendMessage(":x: You must specify a YouTube video link to remove from your favorites!").queue();
                return;
            }

            if(!args[2].contains("https://www.youtube.com/watch")) {
                event.getChannel().sendMessage(":x: You must specify a YouTube video link to remove from your favorites!").queue();
                return;
            }

            File dir = new File(CadenBot.dataDirectory + "music/favorites/" + event.getAuthor().getId());
            if(dir.mkdirs()) {
                System.out.println("Successfully created directory " + dir.getPath());
            }
            File file = new File(dir, args[2].replace("/", "\\@"));
            if(!file.exists()) {
                event.getChannel().sendMessage(":x: That song is not a favorite!").queue();
                return;
            }

            if(file.delete()) {
                event.getChannel().sendMessage("Song was successfully removed from your favorites!").queue();
            }
            else {
                event.getChannel().sendMessage(Constants.ERROR_MESSAGE).queue();
            }
        }
        else if(args[1].equalsIgnoreCase("play") || args[1].equalsIgnoreCase("shuffle")) {
            File dir = new File(CadenBot.dataDirectory + "music/favorites/" + event.getAuthor().getId());
            if(dir.mkdirs()) {
                System.out.println(dir.getPath() + " was successfully created");
            }
            List<String> songs = Arrays.asList(dir.list());
            Collections.shuffle(songs);
            if(songs.size() == 0) {
                event.getChannel().sendMessage(":x: You haven't added any favorites yet! Type `" + prefix + "help` `music`").queue();
                return;
            }
            if(songs.size() == 1 && songs.get(0).equalsIgnoreCase(".DS_Store")) {
                event.getChannel().sendMessage(":x: You haven't added any favorites yet! Type `" + prefix + "help` `music`").queue();
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

            for(String song : songs) {
                if(song.equalsIgnoreCase(".DS_Store")) continue;
                PlayerManager.getInstance().loadAndPlay(event.getChannel(), song.replace("\\@", "/"));
            }
        }
        else if(args[1].equalsIgnoreCase("list")) {
            File dir = new File(CadenBot.dataDirectory + "music/favorites/" + event.getAuthor().getId());
            if(dir.mkdirs()) {
                System.out.println(dir.getPath() + " was successfully created");
            }
            String[] songs = dir.list();
            if(songs.length == 0) {
                event.getChannel().sendMessage(":x: You haven't added any favorites yet! Type `" + prefix + "help` `music`").queue();
                return;
            }
            if(songs.length == 1 && songs[0].equalsIgnoreCase(".DS_Store")) {
                event.getChannel().sendMessage(":x: You haven't added any favorites yet! Type `" + prefix + "help` `music`").queue();
                return;
            }
            EmbedBuilder embed = new EmbedBuilder();
            embed.setAuthor("🎵 " + event.getAuthor().getName() + "'s Favorite Songs 🎵", null, event.getAuthor().getEffectiveAvatarUrl());
            for(String song : songs) {
                if(song.equalsIgnoreCase(".DS_Store")) continue;
                embed.appendDescription("\n" + song.replace("\\@", "/"));
            }
            embed.setColor(EmbedColor.random());
            event.getChannel().sendMessage(embed.build()).queue();
        }
        else {
            event.getChannel().sendMessage(":x: **Incomplete Command!** Type `" + prefix + "help` `music`").queue();
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

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public CommandCategory getCategory() {
        return null;
    }

    @Override
    public Permission getRequiredPermission() {
        return null;
    }

    @Override
    public String getUsage(String prefix) {
        return null;
    }

    @Override
    public String[] getAliases() {
        return new String[]{"favorite", "fav", "favorites"};
    }
}

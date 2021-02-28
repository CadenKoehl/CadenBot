package com.cadenkoehl.cadenbot.music;

import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandCategory;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandEvent;
import com.cadenkoehl.cadenbot.music.lavaplayer.PlayerManager;
import com.cadenkoehl.cadenbot.util.exceptions.IncorrectUsageException;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Play extends Command {

    @Override
    public void execute(CommandEvent event) throws IncorrectUsageException {
        String[] args = event.getArgs();
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
        return "play";
    }

    @Override
    public String getDescription() {
        return "Play a song!";
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
        return "play` `[song name or youtube link]`";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"p"};
    }
}
















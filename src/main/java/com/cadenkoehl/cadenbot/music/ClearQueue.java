package com.cadenkoehl.cadenbot.music;

import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandCategory;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandEvent;
import com.cadenkoehl.cadenbot.music.lavaplayer.MusicManager;
import com.cadenkoehl.cadenbot.music.lavaplayer.PlayerManager;
import com.cadenkoehl.cadenbot.util.exceptions.IncorrectUsageException;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;

public class ClearQueue extends Command {

    @Override
    public void execute(CommandEvent event) throws IncorrectUsageException {
        Member member = event.getMember();
        Member selfMember = event.getGuild().getSelfMember();
        GuildVoiceState selfVoiceState = selfMember.getVoiceState();
        GuildVoiceState memberVoiceState = member.getVoiceState();

        if(!memberVoiceState.inVoiceChannel()) {
            event.getChannel().sendMessage(":x: You must be in a voice channel to use this command!").queue();
            return;
        }

        if(!selfVoiceState.inVoiceChannel()) {
            event.getChannel().sendMessage(":x: I am not playing anything!").queue();
            return;
        }
        if(!selfVoiceState.getChannel().equals(memberVoiceState.getChannel())) {
            event.getChannel().sendMessage(":x: I am currently in a different channel!").queue();
            return;
        }
        MusicManager musicManager = PlayerManager.getInstance().getMusicManager(event.getGuild());

        if(musicManager.scheduler.player.getPlayingTrack() == null) {
            event.getChannel().sendMessage(":x: I am not playing anything!").queue();
            return;
        }

        musicManager.scheduler.player.stopTrack();
        musicManager.scheduler.queue.clear();
        event.getChannel().sendMessage(":white_check_mark: The music has stopped and the queue has been cleared!").queue();
    }

    @Override
    public String getName() {
        return "clearqueue";
    }

    @Override
    public String getDescription() {
        return "Stop the music and clear the queue!";
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
        return "clearqueue`";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"clearq"};
    }
}






































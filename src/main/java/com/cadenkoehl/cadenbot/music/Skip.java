package com.cadenkoehl.cadenbot.music;

import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandCategory;
import com.cadenkoehl.cadenbot.util.Constants;
import com.cadenkoehl.cadenbot.music.lavaplayer.MusicManager;
import com.cadenkoehl.cadenbot.music.lavaplayer.PlayerManager;
import com.cadenkoehl.cadenbot.util.exceptions.IncorrectUsageException;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class Skip extends Command {

    @Override
    public void execute(GuildMessageReceivedEvent event) throws IncorrectUsageException {
        Member member = event.getMember();
        Member selfMember = event.getGuild().getSelfMember();
        GuildVoiceState selfVoiceState = selfMember.getVoiceState();
        GuildVoiceState memberVoiceState = member.getVoiceState();

        if(!memberVoiceState.inVoiceChannel()) {
            event.getChannel().sendMessage(":x: You must be in a voice channel to use this command!").queue();
            return;
        }

        if(!selfVoiceState.inVoiceChannel()) {
            event.getChannel().sendMessage(":x: I am not in a voice channel!").queue();
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
        musicManager.scheduler.nextTrack();
        event.getChannel().sendMessage(":white_check_mark: Skipped!").queue();
    }

    @Override
    public String getName() {
        return "skip";
    }

    @Override
    public String getDescription() {
        return "Skip to the next song!";
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
        return "skip`";
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }
}

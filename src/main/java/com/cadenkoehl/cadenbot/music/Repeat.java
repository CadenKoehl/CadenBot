package com.cadenkoehl.cadenbot.music;

import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandCategory;
import com.cadenkoehl.cadenbot.music.lavaplayer.MusicManager;
import com.cadenkoehl.cadenbot.music.lavaplayer.PlayerManager;
import com.cadenkoehl.cadenbot.util.Constants;
import com.cadenkoehl.cadenbot.util.exceptions.IncorrectUsageException;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class Repeat extends Command {

    @Override
    public void execute(GuildMessageReceivedEvent event) throws IncorrectUsageException {
        Member member = event.getMember();
        Guild guild = event.getGuild();
        Member selfMember = guild.getSelfMember();
        String prefix = this.getPrefix(event);
        GuildVoiceState memberVoiceState = member.getVoiceState();
        GuildVoiceState selfVoiceState = selfMember.getVoiceState();
        if(!memberVoiceState.inVoiceChannel()) {
            event.getChannel().sendMessage(":x: You must be in a voice channel to use this command!").queue();
            return;
        }
        if(!selfVoiceState.inVoiceChannel()) {
            event.getChannel().sendMessage(":x: I must be in a voice channel to use this command! (use `" + prefix + "join`)").queue();
            return;
        }
        if(!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
            event.getChannel().sendMessage(":x: We must be in the same voice channel!").queue();
            return;
        }
        MusicManager musicManager = PlayerManager.getInstance().getMusicManager(guild);
        boolean newRepeating = !musicManager.scheduler.repeating;
        musicManager.scheduler.repeating = newRepeating;
        if(newRepeating) {
            event.getChannel().sendMessage(":white_check_mark: I will now repeat the current song!").queue();
        }

        if(!newRepeating) {
            event.getChannel().sendMessage(":white_check_mark: Repeating was turned off!").queue();
        }
    }

    @Override
    public String getName() {
        return "repeat";
    }

    @Override
    public String getDescription() {
        return "Turn repeating on and off!";
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
        return "repeat`";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"loop"};
    }
}
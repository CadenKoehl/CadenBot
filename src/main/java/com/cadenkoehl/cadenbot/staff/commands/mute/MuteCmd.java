package com.cadenkoehl.cadenbot.staff.commands.mute;

import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandCategory;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandEvent;
import com.cadenkoehl.cadenbot.util.exceptions.IncorrectUsageException;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MuteCmd extends Command {

    @Override
    public void execute(CommandEvent event) throws IncorrectUsageException {
        String[] args = event.getArgs();
        Member mod = event.getMember();
        List<Member> mentionedMembers = event.getMessage().getMentionedMembers();
        if(mentionedMembers.size() == 0) {
            event.getChannel().sendMessage(":x: Please specify a member by @ing them!").queue();
            return;
        }
        String reason = Arrays.stream(args).skip(2).collect(Collectors.joining(" "));
        if(reason.isEmpty()) {
            reason = "Unspecified";
        }
        MuteManager.mute(mentionedMembers.get(0), event.getChannel(), reason, mod);
    }

    @Override
    public String getName() {
        return "mute";
    }

    @Override
    public String getDescription() {
        return "Mutes a member!";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.STAFF;
    }

    @Override
    public Permission getRequiredPermission() {
        return Permission.VOICE_MUTE_OTHERS;
    }

    @Override
    public String getUsage(String prefix) {
        return "mute` `<@member>`";
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }
}



















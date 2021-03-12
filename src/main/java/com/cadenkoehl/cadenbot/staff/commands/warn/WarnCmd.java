package com.cadenkoehl.cadenbot.staff.commands.warn;

import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandCategory;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandEvent;
import com.cadenkoehl.cadenbot.util.exceptions.IncorrectUsageException;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class WarnCmd extends Command {

    @Override
    public void execute(CommandEvent event) throws IncorrectUsageException {
        String[] args = event.getArgs();
        String prefix = event.getPrefix();
        Member mod = event.getMember();
        if(args.length == 1) {
            event.getChannel().sendMessage("**Incomplete Command!**\nUsage: `" + prefix + "warn` `@user` `[reason]`").queue();
            return;
        }
        List<Member> mentionedMembers = event.getMessage().getMentionedMembers();
        if(mentionedMembers.size() == 0) {
            event.getChannel().sendMessage("**Incomplete Command!**\nUsage: `" + prefix + "warn` `@user` `[reason]`").queue();
            return;
        }
        String reason = Arrays.stream(args).skip(2).collect(Collectors.joining(" "));
        if(reason.isEmpty()) {
            reason = "Unspecified";
        }
        Member member = mentionedMembers.get(0);
        if(member.hasPermission(Permission.BAN_MEMBERS)) {
            event.getChannel().sendMessage(":x: You can't warn a moderator!").queue();
            return;
        }
        WarningManager manager = new WarningManager();
        manager.warn(member, event.getChannel(), reason, mod);
    }

    @Override
    public String getName() {
        return "warn";
    }

    @Override
    public String getDescription() {
        return "Warn a member!";
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
        return "warn` `<@member>`";
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }
}

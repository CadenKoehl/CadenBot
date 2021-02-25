package com.cadenkoehl.cadenbot.staff.commands.warn;

import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandCategory;
import com.cadenkoehl.cadenbot.util.Constants;
import com.cadenkoehl.cadenbot.util.exceptions.IncorrectUsageException;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class WarnCmd extends Command {

    @Override
    public void execute(GuildMessageReceivedEvent event) throws IncorrectUsageException {
        String[] args = this.getArgs(event);
        String prefix = this.getPrefix(event);
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
        return new String[0];
    }
}

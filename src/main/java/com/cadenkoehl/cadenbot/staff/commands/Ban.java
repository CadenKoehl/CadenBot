package com.cadenkoehl.cadenbot.staff.commands;

import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandCategory;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandEvent;
import com.cadenkoehl.cadenbot.staff.automod.logging.AuditLogger;
import com.cadenkoehl.cadenbot.util.EmbedColor;
import com.cadenkoehl.cadenbot.util.exceptions.IncorrectUsageException;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.exceptions.HierarchyException;
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Ban extends Command {
    @Override
    public void execute(CommandEvent event) throws IncorrectUsageException {
        String[] args = event.getArgs();
        String prefix = event.getPrefix();
        List<Member> mentionedMembers = event.getMessage().getMentionedMembers();
        if(mentionedMembers.size() == 0) {
            event.getChannel().sendMessage(":x: Please specify a member to ban!\n**Usage:** `" + prefix + "ban` `<@user>` `[reason]`").queue();
            return;
        }
        String reason = Arrays.stream(args).skip(2).collect(Collectors.joining(" "));
        Member member = mentionedMembers.get(0);

        if(member.hasPermission(Permission.BAN_MEMBERS)) {
            event.getChannel().sendMessage(":x: You can't ban a moderator!").queue();
            return;
        }

        String userTag = member.getUser().getAsTag();
        EmbedBuilder embed = new EmbedBuilder();
        if(reason.isEmpty()) {
            reason = "Unspecified";
        }
        embed.setDescription("Reason: " + reason);
        embed.setAuthor(userTag + " was banned!", null, member.getUser().getEffectiveAvatarUrl());
        embed.setColor(EmbedColor.RED);
        try {
            member.ban(0, reason).queue();
            event.getChannel().sendMessage(embed.build()).queue();
            AuditLogger.log(embed.build(), event.getGuild());
        }
        catch (HierarchyException ex) {
            event.getChannel().sendMessage(":x: You can't ban a moderator!").queue();
        }
        catch (InsufficientPermissionException ex) {
            event.getChannel().sendMessage(":x: I cannot perform this action due to lack of permission! Please give me the `ban_members` permission!").queue();
        }
    }

    @Override
    public String getName() {
        return "ban";
    }

    @Override
    public String getDescription() {
        return "Bans a member!";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.STAFF;
    }

    @Override
    public Permission getRequiredPermission() {
        return Permission.BAN_MEMBERS;
    }

    @Override
    public String getUsage(String prefix) {
        return "ban` `<@member>` `[reason]`";
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }
}

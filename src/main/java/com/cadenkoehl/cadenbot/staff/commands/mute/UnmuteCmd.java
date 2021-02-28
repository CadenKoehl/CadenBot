package com.cadenkoehl.cadenbot.staff.commands.mute;

import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandCategory;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandEvent;
import com.cadenkoehl.cadenbot.staff.automod.logging.AuditLogger;
import com.cadenkoehl.cadenbot.util.EmbedColor;
import com.cadenkoehl.cadenbot.util.exceptions.IncorrectUsageException;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.exceptions.HierarchyException;
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException;

public class UnmuteCmd extends Command {

    @Override
    public void execute(CommandEvent event) throws IncorrectUsageException {
        if(event.getMessage().getMentionedMembers().size() == 0) {
            event.getChannel().sendMessage(":x: Please specify a member by @ing them!").queue();
            return;
        }
        Member member = event.getMessage().getMentionedMembers().get(0);
        if(event.getGuild().getRolesByName("Muted", true).size() == 0) {
            event.getChannel().sendMessage(":x: Member is not muted!").queue();
            return;
        }
        Role muted = event.getGuild().getRolesByName("Muted", false).get(0);
        if (!member.getRoles().contains(muted)) {
            event.getChannel().sendMessage(":x: Member is not muted!").queue();
            return;
        }
        try {
            event.getGuild().removeRoleFromMember(member, muted).queue();
            String name = member.getUser().getAsTag();
            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(EmbedColor.GREEN);
            embed.setAuthor(name + " was unmuted!", null, member.getUser().getEffectiveAvatarUrl());
            event.getChannel().sendMessage(embed.build()).queue();
            AuditLogger.log(embed.build(), event.getGuild());
        }
        catch (InsufficientPermissionException ex) {
            event.getChannel().sendMessage(":x: You have not granted me the `manage_roles` permission, so I am unable to perform this action!").queue();
        }
        catch (HierarchyException ex) {
            event.getChannel().sendMessage(":x: You can't mute a moderator!").queue();
        }
    }

    @Override
    public String getName() {
        return "unmute";
    }

    @Override
    public String getDescription() {
        return "Unmute a member!";
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
        return "unmute` `<@member>`";
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }
}

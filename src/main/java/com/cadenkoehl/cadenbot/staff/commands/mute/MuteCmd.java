package com.cadenkoehl.cadenbot.staff.commands.mute;

import com.cadenkoehl.cadenbot.staff.commands.mute.MuteManager;
import com.cadenkoehl.cadenbot.staff.logging.Logger;
import com.cadenkoehl.cadenbot.util.Constants;
import com.cadenkoehl.cadenbot.util.EmbedColor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.HierarchyException;
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MuteCmd extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        String prefix = Constants.getPrefix(event.getGuild().getId());
        if(args[0].equalsIgnoreCase(prefix + "mute")) {
            if(event.isWebhookMessage()) {
                return;
            }
            Member mod = event.getMember();
            if(mod.getUser().isBot()) {
                return;
            }
            if(!mod.hasPermission(Permission.BAN_MEMBERS)) {
                event.getChannel().sendMessage(":x: You can't use that!").queue();
                return;
            }
            List<Member> mentionedMembers = event.getMessage().getMentionedMembers();
            if(mentionedMembers.size() == 0) {
                event.getChannel().sendMessage(":x: Please specify a member by @ing them!").queue();
                return;
            }
            String reason = Arrays.stream(args).skip(2).collect(Collectors.joining(" "));
            if(reason.isEmpty()) {
                reason = "Unspecified";
            }
            MuteManager.mute(mentionedMembers.get(0), event.getChannel(), reason);
        }
        if(args[0].equalsIgnoreCase(prefix + "unmute")) {
            if(event.isWebhookMessage()) {
                return;
            }
            Member mod = event.getMember();
            if(mod.getUser().isBot()) {
                return;
            }
            if(!mod.hasPermission(Permission.BAN_MEMBERS)) {
                event.getChannel().sendMessage(":x: You can't use that!").queue();
                return;
            }
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
                Logger.log(embed.build(), event.getGuild());
            }
            catch (InsufficientPermissionException ex) {
                event.getChannel().sendMessage(":x: You have not granted me the `manage_roles` permission, so I am unable to perform this action!").queue();
            }
            catch (HierarchyException ex) {
                event.getChannel().sendMessage(":x: You can't mute a moderator!").queue();
            }
        }
    }
}



















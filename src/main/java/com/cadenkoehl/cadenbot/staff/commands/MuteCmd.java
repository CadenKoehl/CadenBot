package com.cadenkoehl.cadenbot.staff.commands;

import com.cadenkoehl.cadenbot.util.Constants;
import com.cadenkoehl.cadenbot.util.EmbedColor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
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
            if(event.getGuild().getRolesByName("Muted", false).size() == 0) {
                event.getGuild().createRole().setName("Muted").setColor(Color.DARK_GRAY).complete();
                Mute.createPermissionOverrides(event.getGuild());
            }
            Member member = event.getMessage().getMentionedMembers().get(0);
            if(member.getRoles().contains(event.getGuild().getRolesByName("Muted", false).get(0))) {
                event.getChannel().sendMessage(":x: Member is already muted!").queue();
                return;
            }
            if(member.hasPermission(Permission.BAN_MEMBERS)) {
                event.getChannel().sendMessage(":x: You can't mute a moderator!").queue();
                return;
            }
            String reason = Arrays.stream(args).skip(2).collect(Collectors.joining(" "));
            List<Role> mutedRoles = event.getGuild().getRolesByName("Muted", false);
            event.getGuild().addRoleToMember(member, mutedRoles.get(0)).queue();
            String name = member.getUser().getAsTag();
            EmbedBuilder embed = new EmbedBuilder();
            embed.setAuthor(name + " was muted!", null, member.getUser().getEffectiveAvatarUrl());
            if(reason.isEmpty()) {
                reason = "Unspecified";
            }
            embed.setDescription("Reason: " + reason);

            embed.setColor(EmbedColor.RED);
            event.getChannel().sendMessage(embed.build()).queue();
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
            event.getGuild().removeRoleFromMember(member, muted).queue();
            String name = member.getUser().getAsTag();
            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(EmbedColor.GREEN);
            embed.setAuthor(name + " was unmuted!", null, member.getUser().getEffectiveAvatarUrl());
            event.getChannel().sendMessage(embed.build()).queue();
        }
    }
}



















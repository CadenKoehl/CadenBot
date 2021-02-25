package com.cadenkoehl.cadenbot.staff.commands.mute;

import com.cadenkoehl.cadenbot.staff.automod.logging.AuditLogger;
import com.cadenkoehl.cadenbot.util.EmbedColor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.channel.text.TextChannelCreateEvent;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.exceptions.HierarchyException;
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MuteManager extends ListenerAdapter {
    public void onGuildJoin(GuildJoinEvent event) {
        Guild guild = event.getGuild();
        List<Role> mutedRoles = guild.getRolesByName("Muted", true);
        if (mutedRoles.size() == 0) {
            guild.createRole().setName("Muted").setColor(Color.DARK_GRAY).complete();
            createPermissionOverrides(guild);
        }
        if(mutedRoles.size() > 1) {
            for (Role mutedRole : mutedRoles) {
                mutedRole.delete().queue();
            }
        }
    }
    public void onTextChannelCreate(TextChannelCreateEvent event) {
        if(event.getGuild().getRolesByName("Muted", true).size() == 0) {
            return;
        }
        Role muted = event.getGuild().getRolesByName("Muted", true).get(0);
        event.getChannel().createPermissionOverride(muted).setDeny(Permission.MESSAGE_WRITE).queue();
    }
    public static void createPermissionOverrides(Guild guild) {
        List<TextChannel> channels = guild.getTextChannels();
        for(TextChannel channel : channels) {
            Role muted = guild.getRolesByName("Muted", true).get(0);
            channel.createPermissionOverride(muted).setDeny(Permission.MESSAGE_WRITE).queue();
        }
    }
    public static void mute(Member member, TextChannel channel, String reason, Member moderator) {
        try {
            if(member.getGuild().getRolesByName("Muted", false).size() == 0) {
                member.getGuild().createRole().setName("Muted").setColor(Color.DARK_GRAY).complete();
                MuteManager.createPermissionOverrides(member.getGuild());
            }
            if(member.getRoles().contains(member.getGuild().getRolesByName("Muted", false).get(0))) {
                channel.sendMessage(":x: I cannot mute **" + member.getEffectiveName() + "** because they are already muted!").queue();
                return;
            }
            if(member.hasPermission(Permission.BAN_MEMBERS)) {
                channel.sendMessage(":x: You can't mute a moderator!").queue();
                return;
            }
            List<Role> mutedRoles = member.getGuild().getRolesByName("Muted", false);
            member.getGuild().addRoleToMember(member, mutedRoles.get(0)).queue();
            String name = member.getUser().getAsTag();
            EmbedBuilder embed = new EmbedBuilder();
            embed.setAuthor(name + " was muted!", null, member.getUser().getEffectiveAvatarUrl());
            embed.setDescription("Reason: " + reason);
            embed.setFooter("Moderator: " + moderator.getUser().getAsTag(), moderator.getUser().getEffectiveAvatarUrl());

            embed.setColor(EmbedColor.RED);
            channel.sendMessage(embed.build()).queue();
            AuditLogger.log(embed.build(), channel.getGuild());
        }
        catch (InsufficientPermissionException ex) {
            channel.sendMessage(":x: You have not granted me the `manage_roles` permission so I am unable to perform this action.").queue();
        }
        catch (HierarchyException ex) {
            channel.sendMessage(":x: You can't mute a moderator!").queue();
        }
    }

    public static void tempMute(Member member, TextChannel channel, String reason, int time, TimeUnit unit, Member moderator) {
        try {
            if(member.getGuild().getRolesByName("Muted", false).size() == 0) {
                member.getGuild().createRole().setName("Muted").setColor(Color.DARK_GRAY).complete();
                MuteManager.createPermissionOverrides(member.getGuild());
            }
            if(member.getRoles().contains(member.getGuild().getRolesByName("Muted", false).get(0))) {
                channel.sendMessage(":x: I cannot mute **" + member.getEffectiveName() + "** because they are already muted!").queue();
                return;
            }
            if(member.hasPermission(Permission.BAN_MEMBERS)) {
                channel.sendMessage(":x: You can't mute a moderator!").queue();
                return;
            }
            List<Role> mutedRoles = member.getGuild().getRolesByName("Muted", false);
            member.getGuild().addRoleToMember(member, mutedRoles.get(0)).queue();
            String name = member.getUser().getAsTag();
            EmbedBuilder mute = new EmbedBuilder();
            mute.setAuthor(name + " was muted for " + time + " " + unit.name().toLowerCase() + "!", null, member.getUser().getEffectiveAvatarUrl());
            mute.setDescription("Reason: " + reason);
            mute.setFooter("Moderator: " + moderator.getUser().getAsTag(), moderator.getUser().getEffectiveAvatarUrl());

            mute.setColor(EmbedColor.RED);
            channel.sendMessage(mute.build()).queue();
            AuditLogger.log(mute.build(), channel.getGuild());

            EmbedBuilder unmute = new EmbedBuilder();
            unmute.setAuthor(name + " was unmuted!", null, member.getUser().getEffectiveAvatarUrl());
            unmute.setColor(EmbedColor.GREEN);

            member.getGuild().removeRoleFromMember(member, getMuteRole(member.getGuild())).queueAfter(time, unit);
            channel.sendMessage(unmute.build()).queueAfter(time, unit);
            AuditLogger.logAfter(unmute.build(), channel.getGuild(), time, unit);

        }
        catch (InsufficientPermissionException ex) {
            channel.sendMessage(":x: You have not granted me the `manage_roles` permission so I am unable to perform this action.").queue();
        }
        catch (HierarchyException ex) {
            channel.sendMessage(":x: You can't mute a moderator!").queue();
        }
    }

    public static Role getMuteRole(Guild guild) {
        List<Role> roles = guild.getRolesByName("Muted", false);
        if(roles.size() == 0) {
            guild.createRole().setName("Muted").setColor(Color.DARK_GRAY).complete();
            MuteManager.createPermissionOverrides(guild);
        }
        return roles.get(0);
    }
}
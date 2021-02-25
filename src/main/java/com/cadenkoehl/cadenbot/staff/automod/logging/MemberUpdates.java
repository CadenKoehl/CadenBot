package com.cadenkoehl.cadenbot.staff.automod.logging;

import com.cadenkoehl.cadenbot.util.EmbedColor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleAddEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleRemoveEvent;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateNicknameEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class MemberUpdates extends ListenerAdapter {
    @Override
    public void onGuildMemberRoleAdd(@NotNull GuildMemberRoleAddEvent event) {
        User user = event.getUser();
        EmbedBuilder embed = new EmbedBuilder();
        List<Role> roles = event.getRoles();
        embed.setColor(EmbedColor.GREEN);
        embed.setAuthor(user.getAsTag() + " roles have changed", null, user.getEffectiveAvatarUrl());
        String roleNames = "";
        for(Role role : roles) {
            roleNames = roleNames + "\n" + role.getAsMention();
        }
        embed.addField("✅ Added Roles:", roleNames, false);
        AuditLogger.log(embed.build(), event.getGuild());
    }

    @Override
    public void onGuildMemberRoleRemove(@NotNull GuildMemberRoleRemoveEvent event) {
        User user = event.getUser();
        EmbedBuilder embed = new EmbedBuilder();
        List<Role> roles = event.getRoles();
        embed.setColor(EmbedColor.RED);
        embed.setAuthor(user.getAsTag() + " roles have changed", null, user.getEffectiveAvatarUrl());
        String roleNames = "";
        for(Role role : roles) {
            roleNames = roleNames + "\n" + role.getAsMention();
        }
        embed.addField("⛔ Removed Roles:", roleNames, false);
        AuditLogger.log(embed.build(), event.getGuild());
    }

    @Override
    public void onGuildMemberUpdateNickname(@NotNull GuildMemberUpdateNicknameEvent event) {
        String newNickname = event.getNewNickname();
        String oldNickname = event.getOldNickname();
        User user = event.getUser();
        EmbedBuilder embed = new EmbedBuilder();
        String header;
        if(newNickname == null) {
            header = user.getAsTag() + " nickname has been reset";
        }
        else if(oldNickname == null) {
            header = user.getAsTag() + " was given a nickname";
            embed.setTitle("Nickname: " + newNickname);
        }
        else {
            header = user.getAsTag() + " nickname has changed";
            embed.addField("Old Nickname:", oldNickname, true);
            embed.addField("New Nickname:", newNickname, true);
        }
        embed.setColor(EmbedColor.GREEN);
        embed.setAuthor(header, null, user.getEffectiveAvatarUrl());
        AuditLogger.log(embed.build(), event.getGuild());
    }

    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        User user = event.getUser();
        String name = user.getName();
        String dayOfWeekCreated = user.getTimeCreated().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.US);
        String monthCreated = user.getTimeCreated().getMonth().getDisplayName(TextStyle.FULL, Locale.US);
        int dayCreated = user.getTimeCreated().getDayOfMonth();
        int yearCreated = user.getTimeCreated().getYear();
        String id = user.getId();
        String avatarURL = user.getEffectiveAvatarUrl();

        EmbedBuilder embed = new EmbedBuilder();
        embed.setAuthor(user.getAsTag() + " joined the server", null, user.getEffectiveAvatarUrl());
        embed.setTitle(name + "'s Profile");
        embed.addField("**Account Created**: *" + dayOfWeekCreated + ", " + monthCreated + " " + dayCreated + ", " + yearCreated + "*","", false);
        embed.addField("**User ID**: *" + id + "*","", false);
        embed.addField("**Avatar URL**: ","[Click Here](" + avatarURL + " \"Click Here\")", false);
        embed.setFooter(event.getGuild().getName(), event.getGuild().getIconUrl());
        embed.setColor(EmbedColor.GREEN);
        AuditLogger.log(embed.build(), event.getGuild());
    }

    @Override
    public void onGuildMemberRemove(@NotNull GuildMemberRemoveEvent event) {
        User user = event.getUser();
        String name = user.getName();
        String dayOfWeekCreated = user.getTimeCreated().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.US);
        String monthCreated = user.getTimeCreated().getMonth().getDisplayName(TextStyle.FULL, Locale.US);
        int dayCreated = user.getTimeCreated().getDayOfMonth();
        int yearCreated = user.getTimeCreated().getYear();
        String id = user.getId();
        String avatarURL = user.getEffectiveAvatarUrl();

        EmbedBuilder embed = new EmbedBuilder();
        embed.setAuthor(user.getAsTag() + " left the server", null, user.getEffectiveAvatarUrl());
        embed.setTitle(name + "'s Profile");
        embed.addField("**Account Created**: *" + dayOfWeekCreated + ", " + monthCreated + " " + dayCreated + ", " + yearCreated + "*","", false);
        embed.addField("**User ID**: *" + id + "*","", false);
        embed.addField("**Avatar URL**: ","[Click Here](" + avatarURL + " \"Click Here\")", false);
        embed.setFooter(event.getGuild().getName(), event.getGuild().getIconUrl());
        embed.setColor(EmbedColor.RED);
        AuditLogger.log(embed.build(), event.getGuild());
    }
}

























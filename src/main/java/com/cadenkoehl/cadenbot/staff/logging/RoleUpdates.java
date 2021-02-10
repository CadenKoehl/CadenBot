package com.cadenkoehl.cadenbot.staff.logging;

import com.cadenkoehl.cadenbot.util.EmbedColor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.role.RoleCreateEvent;
import net.dv8tion.jda.api.events.role.RoleDeleteEvent;
import net.dv8tion.jda.api.events.role.update.*;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.EnumSet;

public class RoleUpdates extends ListenerAdapter {
    @Override
    public void onRoleCreate(@NotNull RoleCreateEvent event) {
        String roleMention = event.getRole().getAsMention();
        String roleName = event.getRole().getName();
        Guild guild = event.getGuild();
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(EmbedColor.GREEN);
        embed.setAuthor("✅ Role Created:", null, guild.getIconUrl());
        embed.setDescription(roleMention + " (" + roleName + ")");
        EnumSet<Permission> permissionSet = event.getRole().getPermissions();
        String permsString = "";
        for (Permission perm : permissionSet) {
            permsString = permsString + "\n" + perm.getName();
        }
        embed.addField("Permissions:", permsString, false);
        Logger.log(embed.build(), guild);
    }

    @Override
    public void onRoleDelete(@NotNull RoleDeleteEvent event) {
        String roleName = event.getRole().getName();
        Guild guild = event.getGuild();
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(EmbedColor.RED);
        embed.setAuthor("⛔ Role Deleted: " + roleName, null, guild.getIconUrl());
        EnumSet<Permission> permissionSet = event.getRole().getPermissions();
        String permsString = "";
        for (Permission perm : permissionSet) {
            permsString = permsString + "\n" + perm.getName();
        }
        embed.addField("Permissions:", permsString, false);
        Logger.log(embed.build(), guild);
    }

    @Override
    public void onRoleUpdateName(@NotNull RoleUpdateNameEvent event) {
        EmbedBuilder embed = new EmbedBuilder();
        Role role = event.getRole();
        Guild guild = event.getGuild();
        embed.setColor(role.getColor());
        embed.setAuthor("Role Name Updated", null, guild.getIconUrl());
        embed.setDescription("**Role:** " + role.getAsMention());
        embed.appendDescription("\nNew Name: " + event.getNewName());
        embed.appendDescription("\nOld Name: " + event.getOldName());
        Logger.log(embed.build(), guild);
    }

    @Override
    public void onRoleUpdateColor(@NotNull RoleUpdateColorEvent event) {
        int newColor = event.getNewColorRaw();
        int oldColor = event.getOldColorRaw();
        EmbedBuilder embed = new EmbedBuilder();
        Role role = event.getRole();
        Guild guild = event.getGuild();
        embed.setColor(newColor);
        embed.setAuthor("Role Color Updated", null, guild.getIconUrl());
        embed.setDescription("**Role:** " + role.getAsMention());
        embed.appendDescription("\nNew Color: " + newColor);
        embed.appendDescription("\nOld Color: " + oldColor);
        Logger.log(embed.build(), guild);
    }

    @Override
    public void onRoleUpdatePermissions(@NotNull RoleUpdatePermissionsEvent event) {
        EnumSet<Permission> newPerms = event.getNewPermissions();
        EnumSet<Permission> oldPerms = event.getOldPermissions();
        Role role = event.getRole();
        Color color = role.getColor();
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(color);
        embed.setAuthor("Role Permissions Updated",null, event.getGuild().getIconUrl());
        embed.setDescription("**Role:** " + role.getAsMention());
        String grantedPerms = "";
        String deniedPerms = "";

        for(Permission newPerm : newPerms) {
            if(!oldPerms.contains(newPerm)) {
                grantedPerms = grantedPerms + "\n" + newPerm.getName();
            }
        }

        for(Permission oldPerm : oldPerms) {
            if(!newPerms.contains(oldPerm)) {
                deniedPerms = deniedPerms + "\n" + oldPerm.getName();
            }
        }

        if(!grantedPerms.isEmpty()) {
            embed.addField("✅ Granted Permissions:", grantedPerms, true);
        }

        if(!deniedPerms.isEmpty()) {
            embed.addField("⛔ Denied Permissions:", deniedPerms, true);
        }

        Logger.log(embed.build(), event.getGuild());
    }

    @Override
    public void onRoleUpdateHoisted(@NotNull RoleUpdateHoistedEvent event) {
        if(event.getNewValue() == event.getOldValue()) {
            return;
        }
        Role role = event.getRole();
        EmbedBuilder embed = new EmbedBuilder();
        embed.setAuthor("Role Settings Updated",null, event.getGuild().getIconUrl());
        embed.setDescription("**Role: **" + role.getAsMention());

        boolean isNowHoisted = event.getNewValue();

        if(isNowHoisted) {
            embed.addField("Setting:", "✅ Turned on **Display role members separately from online members**", true);
            embed.setColor(EmbedColor.GREEN);
        }
        if(!isNowHoisted) {
            embed.addField("Setting:", "⛔ Turned off **Display role members separately from online members**", true);
            embed.setColor(EmbedColor.RED);
        }
        Logger.log(embed.build(), event.getGuild());
    }

    @Override
    public void onRoleUpdateMentionable(@NotNull RoleUpdateMentionableEvent event) {
        if(event.getNewValue() == event.getOldValue()) {
            return;
        }
        Role role = event.getRole();
        EmbedBuilder embed = new EmbedBuilder();
        embed.setAuthor("Role Settings Updated",null, event.getGuild().getIconUrl());
        embed.setDescription("**Role: **" + role.getAsMention());

        boolean isNowMentionable = event.getNewValue();

        if(isNowMentionable) {
            embed.setColor(EmbedColor.GREEN);
            embed.addField("Setting:", "✅ Turned on **Allow anyone to @mention this role**", true);
        }
        if(!isNowMentionable) {
            embed.setColor(EmbedColor.RED);
            embed.addField("Setting:", "⛔ Turned off **Allow anyone to @mention this role**", true);
        }
        Logger.log(embed.build(), event.getGuild());
    }
}
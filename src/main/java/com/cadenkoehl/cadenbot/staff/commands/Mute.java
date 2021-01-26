package com.cadenkoehl.cadenbot.staff.commands;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.channel.text.TextChannelCreateEvent;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.List;

public class Mute extends ListenerAdapter {
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
}
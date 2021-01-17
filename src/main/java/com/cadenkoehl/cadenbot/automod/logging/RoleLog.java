package com.cadenkoehl.cadenbot.automod.logging;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleAddEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleRemoveEvent;
import net.dv8tion.jda.api.events.role.RoleCreateEvent;
import net.dv8tion.jda.api.events.role.RoleDeleteEvent;
import net.dv8tion.jda.api.events.role.update.RoleUpdateNameEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class RoleLog extends ListenerAdapter {
	
	public void onGuildMemberRoleAdd(GuildMemberRoleAddEvent event) {
		String usertag = event.getUser().getAsTag();
		String roles = event.getRoles().get(0).getAsMention();

		EmbedBuilder embed = new EmbedBuilder();
		embed.setTitle(usertag + " roles have changed");
		embed.setDescription("Added roles: " + roles);
		embed.setColor(0x50bb5f);
		event.getGuild().getTextChannelsByName("mod-logs", true).get(0).sendMessage(embed.build()).queue();
		
	}
	
	public void onGuildMemberRoleRemove(GuildMemberRoleRemoveEvent event) {
		String usertag = event.getUser().getAsTag();
		String rolemention = event.getRoles().get(0).getAsMention();

		EmbedBuilder embed = new EmbedBuilder();
		embed.setTitle(usertag + " roles have changed");
		embed.setDescription("Removed roles: " + rolemention);
		embed.setColor(0xb83838);
		event.getGuild().getTextChannelsByName("mod-logs", true).get(0).sendMessage(embed.build()).queue();
		
	}
	
	public void onRoleCreate(RoleCreateEvent event) {
		String rolemention = event.getRole().getAsMention();
		String rolename = event.getRole().getName();

		EmbedBuilder embed = new EmbedBuilder();
		embed.setTitle("Role Created:");
		embed.setDescription("Name: " + rolemention + " (" + rolename + ")" + "\n-------\nPermissions: Default Permissions\n-------");
		embed.setColor(0x50bb5f);
		event.getGuild().getTextChannelsByName("mod-logs", true).get(0).sendMessage(embed.build()).queue();
		
	}
	public void onRoleDelete(RoleDeleteEvent event) {
		String rolename = event.getRole().getName();

		EmbedBuilder embed = new EmbedBuilder();
		embed.setTitle("Role Deleted: " + rolename);
		embed.setColor(0xb83838);
		event.getGuild().getTextChannelsByName("mod-logs", true).get(0).sendMessage(embed.build()).queue();
		
	}
	public void onRoleUpdateName(RoleUpdateNameEvent event) {
		String rolename = event.getRole().getName();
		String rolemention = event.getRole().getAsMention();
		
		EmbedBuilder embed = new EmbedBuilder();
		embed.setTitle("Role Name Updated");
		embed.setDescription("**Role:** " + rolemention + "\n **New Name**: " + rolename);
		embed.setColor(event.getRole().getColorRaw());
		event.getGuild().getTextChannelsByName("mod-logs", true).get(0).sendMessage(embed.build()).queue();
		
	}

}

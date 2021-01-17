package com.cadenkoehl.cadenbot.automod.logging;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class VcChat extends ListenerAdapter {
	public void onGuildVoiceJoin(GuildVoiceJoinEvent event) {
		System.out.println("Event was fired!");
		Role role = event.getGuild().getRolesByName("In A Voice Channel", true).get(0);
		Member member = event.getMember();
		
		event.getGuild().addRoleToMember(member, role).queue();
	}
	
	public void onGuildVoiceLeave(GuildVoiceLeaveEvent event) {
		System.out.println("Event was fired!");
		Role role = event.getGuild().getRolesByName("In A Voice Channel", true).get(0);
		Member member = event.getMember();
		
		event.getGuild().removeRoleFromMember(member, role).queue();
	}
}
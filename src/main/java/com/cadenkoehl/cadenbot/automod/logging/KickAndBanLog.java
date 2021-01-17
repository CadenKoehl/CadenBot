package com.cadenkoehl.cadenbot.automod.logging;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.guild.GuildBanEvent;
import net.dv8tion.jda.api.events.guild.GuildUnbanEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class KickAndBanLog extends ListenerAdapter {
	
	public void onGuildBan(GuildBanEvent event) {
		
		String user = event.getUser().getAsTag();
		
		EmbedBuilder embed = new EmbedBuilder();
		embed.setDescription(user + " was banned");
		embed.setColor(0xb83838);
		event.getGuild().getTextChannelsByName("mod-logs", true).get(0).sendMessage(embed.build()).queue();
		
	}
	
	public void onGuildUnban(GuildUnbanEvent event) {
		
		String user = event.getUser().getAsTag();
		
		EmbedBuilder embed = new EmbedBuilder();
		embed.setDescription(user + " was unbanned");
		embed.setColor(0x50bb5f);
		event.getGuild().getTextChannelsByName("mod-logs", true).get(0).sendMessage(embed.build()).queue();
		
	}
}

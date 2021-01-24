package com.cadenkoehl.cadenbot.staff.logging;

import com.cadenkoehl.cadenbot.CadenBot;
import com.cadenkoehl.cadenbot.staff.commands.LogChannel;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.GuildBanEvent;
import net.dv8tion.jda.api.events.guild.GuildUnbanEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;

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
		String channelId;
		channelId = LogChannel.getLogChannelId(event.getGuild());
		if (channelId != null) {
			TextChannel channel = event.getJDA().getTextChannelById(channelId);
			if(channel == null) {
				File file = new File(CadenBot.dataDirectory + "logging/" + channelId + ".txt");
				if(file.exists()) {
					file.delete();
				}
				return;
			}
			channel.sendMessage(embed.build()).queue();
		}
	}
}
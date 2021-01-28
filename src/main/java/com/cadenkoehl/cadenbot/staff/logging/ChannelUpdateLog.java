package com.cadenkoehl.cadenbot.staff.logging;

import com.cadenkoehl.cadenbot.CadenBot;
import com.cadenkoehl.cadenbot.util.EmbedColor;

import com.cadenkoehl.cadenbot.staff.commands.LogChannel;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.channel.text.TextChannelCreateEvent;
import net.dv8tion.jda.api.events.channel.text.TextChannelDeleteEvent;
import net.dv8tion.jda.api.events.channel.text.update.TextChannelUpdateNameEvent;
import net.dv8tion.jda.api.events.channel.voice.VoiceChannelCreateEvent;
import net.dv8tion.jda.api.events.channel.voice.VoiceChannelDeleteEvent;
import net.dv8tion.jda.api.events.channel.voice.update.VoiceChannelUpdateNameEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;

public class ChannelUpdateLog extends ListenerAdapter {
	
	public void onTextChannelCreate(TextChannelCreateEvent event) {
		String channelName = event.getChannel().getName();

		EmbedBuilder embed = new EmbedBuilder();
		embed.setDescription("Text Channel Created: " + channelName);
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
	
	public void onTextChannelDelete(TextChannelDeleteEvent event) {
		String channelName = event.getChannel().getName();

		
		EmbedBuilder embed = new EmbedBuilder();
		embed.setColor(0xb83838);
		embed.setDescription("Text Channel Deleted: " + channelName);
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
	public void onVoiceChannelCreate(VoiceChannelCreateEvent event) {
		String channelName = event.getChannel().getName();
		
		EmbedBuilder embed = new EmbedBuilder();
		embed.setDescription("Voice Channel Created: " + channelName);
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
	
	public void onVoiceChannelDelete(VoiceChannelDeleteEvent event) {
		String channelName = event.getChannel().getName();
		
		EmbedBuilder embed = new EmbedBuilder();
		embed.setColor(0xb83838);
		embed.setDescription("Voice Channel Deleted: " + channelName);
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
	
	public void onVoiceChannelUpdateName(VoiceChannelUpdateNameEvent event) {
		String oldName = event.getOldName();
		String newName = event.getNewName();

		EmbedBuilder embed = new EmbedBuilder();
		embed.setTitle("Voice Channel Name Updated");
		embed.setDescription("**" + oldName + "** ---> **" + newName + "**");
		embed.setColor(EmbedColor.DARK_BLUE);
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
	
	public void onTextChannelUpdateName(TextChannelUpdateNameEvent event) {
		String oldName = event.getOldName();
		String newName = event.getNewName();
		String channelName = event.getChannel().getAsMention();
		
		EmbedBuilder embed = new EmbedBuilder();
		embed.setTitle("Text Channel Name Updated");
		embed.setDescription("**" + oldName + "** ---> **" + newName + "**");
		embed.addField("Channel: ", channelName, false);
		embed.setColor(EmbedColor.DARK_BLUE);
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

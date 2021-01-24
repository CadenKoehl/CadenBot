package com.cadenkoehl.cadenbot.staff.logging;

import com.cadenkoehl.cadenbot.CadenBot;
import com.cadenkoehl.cadenbot.EmbedColor;
import com.cadenkoehl.cadenbot.staff.commands.LogChannel;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.emote.EmoteAddedEvent;
import net.dv8tion.jda.api.events.emote.EmoteRemovedEvent;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateNicknameEvent;
import net.dv8tion.jda.api.events.guild.update.GuildUpdateNameEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;

public class OtherLogs extends ListenerAdapter {
	
	public void onGuildUpdateName(GuildUpdateNameEvent event) {
	
		String oldName = event.getOldName();
		String newName = event.getNewValue();
		
		EmbedBuilder embed = new EmbedBuilder();
		embed.setTitle("Server Name Updated");
		embed.setDescription("Old Name: " + "**" + oldName + "**" + "\nNew Name: " + "**" + newName + "**");
		embed.setColor(0xf4471);
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
	public void onGuildMemberUpdateNickname(GuildMemberUpdateNicknameEvent event) {
		String oldNick = event.getOldNickname();
		String newNick = event.getNewNickname();
		String user = event.getUser().getAsTag();
		String name = event.getUser().getName();
		
		if(event.getNewNickname() == null) {
			EmbedBuilder embed = new EmbedBuilder();
			embed.setTitle(user + "'s nickname has been reset");
			embed.setColor(0xf4471);
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
		
		else if(event.getOldNickname() == null) {
			EmbedBuilder embed = new EmbedBuilder();
			embed.setTitle(user + "'s nickname has changed");
			embed.setDescription("**" + name + "**" + " ---> " + "**" + newNick + "**");
			embed.setColor(0xf4471);
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
		else {
			EmbedBuilder embed = new EmbedBuilder();
			embed.setTitle(user + "'s nickname has changed");
			embed.setDescription("**" + oldNick + "**" + " ---> " + "**" + newNick + "**");
			embed.setColor(0xf4471);
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
	public void onEmoteAdded(EmoteAddedEvent event) {
		String emote = event.getEmote().getAsMention();
		String name = event.getEmote().getName();
		String id = event.getEmote().getId();
		EmbedBuilder embed = new EmbedBuilder();
		embed.setTitle("New Emoji Added:  " + emote);
		embed.addField("Name: `" + name + "`", "", false);
		embed.addField("ID: `" + id + "`", "", false);
		embed.setColor(EmbedColor.GREEN);
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
	public void onEmoteRemoved(EmoteRemovedEvent event) {
		String emote = event.getEmote().getAsMention();
		String name = event.getEmote().getName();
		String id = event.getEmote().getId();
		EmbedBuilder embed = new EmbedBuilder();
		embed.setTitle("Emoji Removed:  " + emote);
		embed.addField("Name: `" + name + "`", "", false);
		embed.addField("ID: `" + id + "`", "", false);
		embed.setColor(EmbedColor.RED);
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

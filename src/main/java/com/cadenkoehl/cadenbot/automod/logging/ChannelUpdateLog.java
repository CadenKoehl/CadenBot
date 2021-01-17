package com.cadenkoehl.cadenbot.automod.logging;

import com.cadenkoehl.cadenbot.EmbedColor;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.channel.text.TextChannelCreateEvent;
import net.dv8tion.jda.api.events.channel.text.TextChannelDeleteEvent;
import net.dv8tion.jda.api.events.channel.text.update.TextChannelUpdateNameEvent;
import net.dv8tion.jda.api.events.channel.voice.VoiceChannelCreateEvent;
import net.dv8tion.jda.api.events.channel.voice.VoiceChannelDeleteEvent;
import net.dv8tion.jda.api.events.channel.voice.update.VoiceChannelUpdateNameEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ChannelUpdateLog extends ListenerAdapter {
	
	public void onTextChannelCreate(TextChannelCreateEvent event) {
		String channel = event.getChannel().getName();
		
		EmbedBuilder embed = new EmbedBuilder();
		embed.setDescription("Text Channel Created: " + channel);
		embed.setColor(0x50bb5f);
		event.getGuild().getTextChannelsByName("mod-logs", true).get(0).sendMessage(embed.build()).queue();
		
	}
	
	public void onTextChannelDelete(TextChannelDeleteEvent event) {
		String channel = event.getChannel().getName();
		
		EmbedBuilder embed = new EmbedBuilder();
		embed.setColor(0xb83838);
		embed.setDescription("Text Channel Deleted: " + channel);
		event.getGuild().getTextChannelsByName("mod-logs", true).get(0).sendMessage(embed.build()).queue();
		
	}
	public void onVoiceChannelCreate(VoiceChannelCreateEvent event) {
		String channel = event.getChannel().getName();
		
		EmbedBuilder embed = new EmbedBuilder();
		embed.setDescription("Voice Channel Created: " + channel);
		embed.setColor(0x50bb5f);
		event.getGuild().getTextChannelsByName("mod-logs", true).get(0).sendMessage(embed.build()).queue();
		
	}
	
	public void onVoiceChannelDelete(VoiceChannelDeleteEvent event) {
		String channel = event.getChannel().getName();
		
		EmbedBuilder embed = new EmbedBuilder();
		embed.setColor(0xb83838);
		embed.setDescription("Voice Channel Deleted: " + channel);
		event.getGuild().getTextChannelsByName("mod-logs", true).get(0).sendMessage(embed.build()).queue();
		
	}
	
	public void onVoiceChannelUpdateName(VoiceChannelUpdateNameEvent event) {
		String oldName = event.getOldName();
		String newName = event.getNewName();
		
		EmbedBuilder embed = new EmbedBuilder();
		embed.setTitle("Voice Channel Name Updated");
		embed.setDescription("**" + oldName + "** ---> **" + newName + "**");
		embed.setColor(EmbedColor.DARK_BLUE);
		event.getGuild().getTextChannelsByName("mod-logs", true).get(0).sendMessage(embed.build()).queue();
	}
	
	public void onTextChannelUpdateName(TextChannelUpdateNameEvent event) {
		String oldName = event.getOldName();
		String newName = event.getNewName();
		String channel = event.getChannel().getAsMention();
		
		EmbedBuilder embed = new EmbedBuilder();
		embed.setTitle("Text Channel Name Updated");
		embed.setDescription("**" + oldName + "** ---> **" + newName + "**");
		embed.addField("Channel: ", channel, false);
		embed.setColor(EmbedColor.DARK_BLUE);
		event.getGuild().getTextChannelsByName("mod-logs", true).get(0).sendMessage(embed.build()).queue();
	}
}

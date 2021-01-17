package com.cadenkoehl.cadenbot.joinleave;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Random;

public class JoinMsgs extends ListenerAdapter{
	
	public void onGuildMemberJoin(GuildMemberJoinEvent event) {
		
		if(event.getGuild().getSystemChannel() != null) {
			TextChannel channel = event.getGuild().getSystemChannel();
			Member member = event.getMember();
			String tag = member.getUser().getAsTag();
			Guild guild = event.getGuild();

			String[] headings = {
					tag + " joined the server!",
					tag + " just showed up!",
					tag + " slid into the server!",
					tag + " hopped onto the server!",
					"A wild " + tag + " appeared!"
			};

			String[] messages = {
					"Make sure to read the rules 😊",
					"Hope you enjoy your stay 😊",
					"Did you bring pizza? 🍕",
					"We hope you brought pizza! 🍕",
					"Glad you're here 😊",
					"Nice to meet you!!! 😅",
					"Don't be shy, say hi! 😅"};
			Random random = new Random();
			int heading = random.nextInt(headings.length);
			int message = random.nextInt(messages.length);

			EmbedBuilder embed = new EmbedBuilder();
			embed.setAuthor(headings[heading], null, member.getUser().getEffectiveAvatarUrl());
			embed.setDescription(messages[message]);
			embed.setColor((int) Math.round(Math.random() * 999999));
			channel.sendMessage(embed.build()).queue();
    	
		}
		else {
			TextChannel defaultChannel = event.getGuild().getDefaultChannel();
			defaultChannel.sendMessage("Heyo! It seems you don't have a system channel, so I am unable to send welcome msgs when someone joins/leaves the server! To enable the welcome msgs, go to `Server Settings`, then `Overview`, then choose a system channel!").queue();
		}
	}
}
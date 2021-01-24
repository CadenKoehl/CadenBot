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
		Guild guild = event.getGuild();
		TextChannel channel = null;
		String channelId = SystemMessageManager.getWelcomeChannelId(guild);
		if(channelId == null) {
			channel = event.getGuild().getSystemChannel();
		}
		if(channelId != null) {
			channel = event.getGuild().getTextChannelById(channelId);
		}
		Member member = event.getMember();
		String tag = member.getUser().getAsTag();
		String guildName = event.getGuild().getName();

		String[] headings = {
				tag + " joined the server!",
				tag + " just showed up!",
				tag + " slid into the server!",
				tag + " hopped onto the server!",
				"A wild " + tag + " appeared!",
				"A wild " + tag + " has stumbled upon the land of " + guildName
		};

		String[] messages = {
				"Make sure to read the rules 😊",
				"Hope you enjoy your stay 😊",
				"Did you bring pizza? 🍕",
				"We hope you brought pizza! 🍕",
				"Glad you're here 😊",
				"Nice to meet you!!! 😅",
				"Ayyoo hold my beer!! 🍺 😅"};
		Random random = new Random();
		int heading = random.nextInt(headings.length);
		int message = random.nextInt(messages.length);

		EmbedBuilder embed = new EmbedBuilder();
		embed.setAuthor(headings[heading], null, member.getUser().getEffectiveAvatarUrl());
		embed.setDescription(messages[message]);
		embed.setColor((int) Math.round(Math.random() * 999999));
		channel.sendMessage(embed.build()).queue();
	}
}
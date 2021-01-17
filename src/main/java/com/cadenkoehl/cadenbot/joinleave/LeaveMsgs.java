package com.cadenkoehl.cadenbot.joinleave;

import java.util.Random;

import com.cadenkoehl.cadenbot.EmbedColor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class LeaveMsgs extends ListenerAdapter{
	public void onGuildMemberRemove(GuildMemberRemoveEvent event) {

		try {
			User user = event.getUser();
			String tag = user.getAsTag();
			String[] embedMsgs = {
					tag + " has left us 😢",
					tag + " left the server 😢"
			};
			String[] messages = {
					"Goodbye old friend, hope to see you again sometime",
					"They are gone, but not forgotten.",
					"Come back soon!"
			};
			Random random = new Random();
			int embedMsg = random.nextInt(embedMsgs.length);
			int message = random.nextInt(messages.length);
			TextChannel channel = event.getGuild().getSystemChannel();
			EmbedBuilder embed = new EmbedBuilder();
			embed.setColor(EmbedColor.RED);
			embed.setAuthor(embedMsgs[embedMsg], null, user.getEffectiveAvatarUrl());
			embed.setDescription(messages[message]);
			channel.sendMessage(embed.build()).queue();

		}
		catch (Exception ex) {
			System.out.println("Exception was caught: " + ex);
		}
	}
}

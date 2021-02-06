package com.cadenkoehl.cadenbot.help;

import java.util.concurrent.TimeUnit;

import com.cadenkoehl.cadenbot.util.Constants;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
public class HelpCommands extends ListenerAdapter {
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String[] args = event.getMessage().getContentRaw().split(" ");
		String id = event.getGuild().getId();
		String prefix = Constants.getPrefix(id);
		if (args[0].equalsIgnoreCase(prefix + "help")) {
			if(args.length < 2) {
				return;
			}
			if(!args[1].equalsIgnoreCase("commands")) {
				return;
			}
			event.getChannel().sendTyping().delay(500L, TimeUnit.MILLISECONDS).complete();
			
			EmbedBuilder help = new EmbedBuilder();
			help.setTitle("Here's a list of my commands!");
			help.setDescription("**" +
					prefix + "info:**  Basic info about me! \n**" +
					prefix + "prefix `[new prefix]` (or `CadenBot-prefix`):** Change my prefix! \n**" +
					prefix + "serverinfo:** Info about the current server!\n**" +
					prefix + "profile `<@user>`:** Show a user's profile!\n**" +
					prefix + "invite:**  Invites me to your server! \n**" +
					prefix + "vote [msg]:**  Turns your message into a poll!\n**" +
					prefix + "fakemsg** `@<user>` `msg`**:**  Creates a fake msg sent by a user of your choice!");
			help.setColor((int) Math.round(Math.random() * 999999));
			
			event.getChannel().sendMessage(help.build()).queue();
			
			System.out.println((String.format("==========\n[COMMAND] %s ran command \"%s\", on server \"%s\"", event.getAuthor().getAsTag(), event.getMessage().getContentDisplay(), event.getGuild().getName())));
		}
	}
}



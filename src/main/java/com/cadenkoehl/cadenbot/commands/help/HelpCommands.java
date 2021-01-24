package com.cadenkoehl.cadenbot.commands.help;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import com.cadenkoehl.cadenbot.CadenBot;

import com.cadenkoehl.cadenbot.Constants;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
public class HelpCommands extends ListenerAdapter {

	private static String id;
	private static String prefix = "-";
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String[] args = event.getMessage().getContentRaw().split(" ");
		id = event.getGuild().getId();
		if (args[0].equalsIgnoreCase(getPrefix() + "help")) {
			if(args.length < 2) {
				return;
			}
			if(!args[1].equalsIgnoreCase("commands")) {
				return;
			}
			event.getChannel().sendTyping().delay(500L, TimeUnit.MILLISECONDS).complete();
			
			EmbedBuilder help = new EmbedBuilder();
			help.setTitle("Here's a list of my commands!");
			help.setDescription("**" + getPrefix() + "info:**  Basic info about me! \n**" + getPrefix() + "prefix `[new prefix]` (or `CadenBot-prefix`):** Change my prefix! \n**" + getPrefix() + "serverinfo:** Info about the current server!\n**" + getPrefix() + "profile `<@user>`:** Show a user's profile!\n**" + getPrefix() + "invite:**  Invites me to your server! \n**" + getPrefix() + "vote (msg):**  Turns your message into a poll!\n**" + getPrefix() + "fakemsg** `@<user>` `msg`**:**  Creates a fake msg sent by a user of your choice!");
			help.setColor((int) Math.round(Math.random() * 999999));
			
			event.getChannel().sendMessage(help.build()).queue();
			
			System.out.println((String.format("==========\n[COMMAND] %s ran command \"%s\", on server \"%s\"", event.getAuthor().getAsTag(), event.getMessage().getContentDisplay(), event.getGuild().getName())));
			
		}
		
	}
	public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) {
		String[] args = event.getMessage().getContentRaw().split(" ");
		
		if (args[0].equalsIgnoreCase(CadenBot.prefix + "help-commands")) {
			event.getChannel().sendTyping().delay(500L, TimeUnit.MILLISECONDS).complete();
			
			EmbedBuilder help = new EmbedBuilder();
			help.setTitle("What do you need help with?");
			help.setDescription("**-info:**  Basic info about me! \n**-invite:**  Invites me to your server! \n**-vote (msg):**  Turns your message into a poll!");
			help.setColor(0xf4271);

			
			event.getChannel().sendMessage(help.build()).queue();
			
		}
		
	}
	private static String getPrefix() {
		File file;
		try {
			file = new File(CadenBot.dataDirectory + "prefix/" + id + ".txt");
			Scanner scan = new Scanner(file);
			prefix = scan.nextLine();
		}
		catch (FileNotFoundException ex) {
			prefix = "-";
		}
		return prefix;
	}
	
}



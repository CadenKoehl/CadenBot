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

public class HelpSrc extends ListenerAdapter {

	private static String id;
	private static String prefix = "-";

	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String[] args = event.getMessage().getContentRaw().split(" ");
		id = event.getGuild().getId();
		if (args[0].equalsIgnoreCase(getPrefix() + "help-src")) {
			event.getChannel().sendTyping().delay(500L, TimeUnit.MILLISECONDS).complete();
			
			EmbedBuilder help = new EmbedBuilder();
			help.setTitle("Here's how you view my source code!");
			help.setDescription("While you can't view it yet, Caden soon will create a **GitHub** page for me, and if you have any questions, or want to follow the bot's progress, join my Support Server! \nhttps://discord.gg/7gnu5nVMA8");
			help.setColor(0xf4271);

			
			event.getChannel().sendMessage(help.build()).queue();
			
			System.out.println((String.format("==========\n[COMMAND] %s ran command \"%s\", on server \"%s\"", event.getAuthor().getAsTag(), event.getMessage().getContentDisplay(), event.getGuild().getName())));
			
		}
		
	}
	public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) {
		String[] args = event.getMessage().getContentRaw().split(" ");
		
		if (args[0].equalsIgnoreCase(CadenBot.prefix + "help-src")) {
			event.getChannel().sendTyping().delay(500L, TimeUnit.MILLISECONDS).complete();
			
			EmbedBuilder help = new EmbedBuilder();
			help.setTitle("Here's how you view my source code!");
			help.setDescription("While you can't view it yet, Caden soon will create a **GitHub** page for me, and if you have any questions, or want to follow the bot's progress, join my Support Server! \nhttps://discord.gg/7gnu5nVMA8");
			help.setColor((int) Math.round(Math.random() * 999999));

			
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

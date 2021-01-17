package com.cadenkoehl.cadenbot.commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import com.cadenkoehl.cadenbot.CadenBot;
import com.cadenkoehl.cadenbot.Constants;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Info extends ListenerAdapter {
	private static String id;
	private static String prefix = "-";
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		id = event.getGuild().getId();
		String[] args = event.getMessage().getContentRaw().split(" ");
		if (args[0].equalsIgnoreCase(getPrefix() + "info")) {
			event.getChannel().sendTyping().delay(500L, TimeUnit.MILLISECONDS).complete();
			event.getChannel().sendMessage("I am a discord bot currently in development! There are still many bugs trying to be fixed, and many new features soon to be added! If you wanna see my progress, join my support server! (I sent an invite in dm **:>**)").queue();
			
			event.getMember().getUser().openPrivateChannel().queue((channel) -> {
				channel.sendMessage("https://discord.gg/7gnu5nVMA8").queue();
				
				System.out.println((String.format("==========\n[COMMAND] %s ran command \"%s\", on server \"%s\"", event.getAuthor().getAsTag(), event.getMessage().getContentDisplay(), event.getGuild().getName())));
		});
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
	public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) {

		String[] args = event.getMessage().getContentRaw().split(" ");
		
		if (args[0].equalsIgnoreCase(CadenBot.prefix + "info")) {
			event.getChannel().sendTyping().delay(500L, TimeUnit.MILLISECONDS).complete();
			event.getChannel().sendMessage("I am a discord bot currently in development! There are still many bugs trying to be fixed, and many new features soon to be added! If you wanna see my progress, join my support server! \nhttps://discord.gg/7gnu5nVMA8").queue();
		
	}
	
	}
	
}

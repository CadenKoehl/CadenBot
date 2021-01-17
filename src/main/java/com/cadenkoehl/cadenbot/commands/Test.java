package com.cadenkoehl.cadenbot.commands;

import com.cadenkoehl.cadenbot.CadenBot;
import com.cadenkoehl.cadenbot.Constants;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Test extends ListenerAdapter {
	private static String id;
	private static String prefix = "-";
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		id = event.getGuild().getId();
		String[] args = event.getMessage().getContentRaw().split(" ");
		if (args[0].equalsIgnoreCase(getPrefix() + "test")) {
			event.getChannel().sendMessage("test").queue();
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
		catch (IOException ex) {
			ex.printStackTrace();
			CadenBot.jda.getTextChannelById(Constants.CADENBOTBUGSCHANNEL).sendMessage(CadenBot.jda.getUserById(Constants.CadenID).getAsMention() + " help! There is a huge bug in my code!! Someone tried to run a command, and this happened: " +
					ex).queue();
		}
		return prefix;
	}
}

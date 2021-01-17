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

public class ReactionVote extends ListenerAdapter {
	private static String id;
	private static String prefix = "-";
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		id = event.getGuild().getId();
		String[] args = event.getMessage().getContentRaw().split("\\s+"); {
			if (args[0].equalsIgnoreCase(getPrefix() + "vote")) {
				event.getMessage().addReaction("✅").queue();
				event.getMessage().addReaction("❌").queue();
				System.out.println((String.format("==========\n[COMMAND] %s ran command \"%s\", on server \"%s\"", event.getAuthor().getAsTag(), event.getMessage().getContentDisplay(), event.getGuild().getName())));
		};
		}
	}

	public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) {
		String[] args = event.getMessage().getContentRaw().split("\\s+");
			if (args[0].equalsIgnoreCase(CadenBot.prefix + "vote")) {
				event.getChannel().sendTyping().delay(500L, TimeUnit.MILLISECONDS).complete();
				event.getChannel().sendMessage("That command isn't available in private messages!").queue();
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

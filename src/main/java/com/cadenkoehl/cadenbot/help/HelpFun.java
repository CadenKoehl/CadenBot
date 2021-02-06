package com.cadenkoehl.cadenbot.help;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import com.cadenkoehl.cadenbot.CadenBot;

import com.cadenkoehl.cadenbot.util.Constants;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class HelpFun extends ListenerAdapter {

	private static String id;
	private static String prefix = "-";
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String[] args = event.getMessage().getContentRaw().split(" ");
		id = event.getGuild().getId();
		if (args[0].equalsIgnoreCase(getPrefix() + "help")) {
			if(args.length < 2) {
				return;
			}
			if(!args[1].equalsIgnoreCase("fun")) {
				return;
			}
			event.getChannel().sendTyping().delay(500L, TimeUnit.MILLISECONDS).complete();
			
			EmbedBuilder help = new EmbedBuilder();
			help.setTitle("Here's a list of my fun commands!");
			help.setDescription("__**COMMANDS**__: \n**" + getPrefix() + "YesOrNo:**  Ask me a yes-or-no question!\n**" + getPrefix() + "pp:** Calculate your PP size!\n**" + getPrefix() + "hack:** Hack your friends!\n**" + getPrefix() + "hangman** Play hangman!\n-----\nMore fun features are coming soon! If you wanna follow my progress, join my support server! (type -help)");
			help.setColor((int) Math.round(Math.random() * 999999));

			
			event.getChannel().sendMessage(help.build()).queue();
			
			System.out.println((String.format("==========\n[COMMAND] %s ran command \"%s\", on server \"%s\"", event.getAuthor().getAsTag(), event.getMessage().getContentDisplay(), event.getGuild().getName())));
			
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

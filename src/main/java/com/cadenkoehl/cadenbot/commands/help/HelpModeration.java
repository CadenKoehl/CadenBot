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

public class HelpModeration extends ListenerAdapter {

	private static String id;
	private static String prefix = "-";
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String[] args = event.getMessage().getContentRaw().split(" ");
		id = event.getGuild().getId();
		if (args[0].equalsIgnoreCase(getPrefix() + "help-moderation") || args[0].equalsIgnoreCase(getPrefix() + "help-mod") || args[0].equalsIgnoreCase(getPrefix() + "help-staff")) {
			event.getChannel().sendTyping().delay(500L, TimeUnit.MILLISECONDS).complete();
			
			EmbedBuilder help = new EmbedBuilder();
			help.setTitle("Moderation");
			help.setDescription("**Features**\nIf you name a channel \"mod-logs\", you can test out the audit logging feature!\n**Commands**\n`" + getPrefix() + "mute` `<@member>`\n`" + getPrefix() + "ban` `<@member>`");
			help.setColor((int) Math.round(Math.random() * 999999));

			
			event.getChannel().sendMessage(help.build()).queue();
			
			System.out.println((String.format("==========\n[COMMAND] %s ran command \"%s\", on server \"%s\"", event.getAuthor().getAsTag(), event.getMessage().getContentDisplay(), event.getGuild().getName())));
			
		}
		
	}
	public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) {
		String[] args = event.getMessage().getContentRaw().split(" ");
		
		if (args[0].equalsIgnoreCase(CadenBot.prefix + "help-moderation")) {
			event.getChannel().sendTyping().delay(500L, TimeUnit.MILLISECONDS).complete();
			EmbedBuilder help = new EmbedBuilder();
			help.setTitle("Moderation");
			help.setDescription("**Features**\nIf you name a channel \"mod-logs\", you can test out the audit logging feature!\n**Commands**\n`-mute` `reason` `minutes`");
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
		catch (IOException ex) {
			ex.printStackTrace();
			CadenBot.jda.getTextChannelById(Constants.CADENBOTBUGSCHANNEL).sendMessage(CadenBot.jda.getUserById(Constants.CadenID).getAsMention() + " help! There is a huge bug in my code!! Someone tried to run a command, and this happened: " +
					ex).queue();
		}
		return prefix;
	}
}

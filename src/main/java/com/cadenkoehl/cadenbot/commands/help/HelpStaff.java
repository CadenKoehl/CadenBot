package com.cadenkoehl.cadenbot.commands.help;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import com.cadenkoehl.cadenbot.CadenBot;

import com.cadenkoehl.cadenbot.Constants;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class HelpStaff extends ListenerAdapter {

	private static String id;
	private static String prefix = "-";
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String[] args = event.getMessage().getContentRaw().split(" ");
		id = event.getGuild().getId();
		if (args[0].equalsIgnoreCase(getPrefix() + "help")) {
			if(args.length < 2) {
				return;
			}
			if(!args[1].equalsIgnoreCase("staff")) {
				return;
			}
			if(!event.getGuild().getSelfMember().hasPermission(Permission.ADMINISTRATOR)) {
				event.getChannel().sendMessage(":x: My staff commands will not work because I lack the `administrator` permission!").queue();
				return;
			}
			if(!event.getMember().hasPermission(Permission.BAN_MEMBERS)) {
				event.getChannel().sendMessage(":x: You can't use that!").queue();
				return;
			}
			event.getChannel().sendTyping().delay(500L, TimeUnit.MILLISECONDS).complete();
			
			EmbedBuilder help = new EmbedBuilder();
			help.setTitle("Staff");
			help.setDescription("Here are some cool features to help you with moderation!\n**--------**\n**Commands**\n`"
					 + getPrefix() + "mute` `<@member>` `[reason]` - Mutes a member\n`"
					+ getPrefix() + "ban` `<@member>` `[reason]` - Bans a member\n`"
					+ getPrefix() + "logchannel` - Find out the current logging channel\n`"
					+ getPrefix() + "logchannel` `<#channel>` - Sets the audit log channel\n`"
					+ getPrefix() + "logging` `off` - Turns logging off");
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

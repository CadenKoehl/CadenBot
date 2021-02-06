package com.cadenkoehl.cadenbot.help;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import com.cadenkoehl.cadenbot.CadenBot;

import com.cadenkoehl.cadenbot.util.Constants;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Help extends ListenerAdapter {

	private static String id;
	private static String prefix = "-";
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String[] args = event.getMessage().getContentRaw().split(" ");
		id = event.getGuild().getId();
		if (args[0].equalsIgnoreCase(getPrefix() + "help")) {
			if(args.length != 1) {
				return;
			}
			event.getChannel().sendTyping().delay(500L, TimeUnit.MILLISECONDS).complete();
			EmbedBuilder help = new EmbedBuilder();
			help.setTitle("What do you need help with?");
			help.setDescription("**"
					+ getPrefix() + "help commands**:  Gives you a list of commands! \n**"
					+ getPrefix() + "help music**:   Jam out to some music!\n**"
					+ getPrefix() + "help welcomemsgs**:  Custom msg when someone joins or leaves the server!\n**"
					+ getPrefix() + "help reactionroles**:  Easily create customizable reaction roles! \n**"
					+ getPrefix() + "help levels**:  Check out the leveling system!\n**"
					+ getPrefix() + "help fun**:  See my fun commands, features & easter eggs! \n**"
					+ getPrefix() + "help staff**:  List of features that help with moderation!\n**"
					+ getPrefix() + "help suggestions**:  A cool feature to allow members to suggest things!\n**"
					+ getPrefix() + "help embeds**:  Create awesome embeds on your server! \n If you have anymore questions, visit my help server! " +
					"\n https://discord.gg/7gnu5nVMA8");
			help.setColor((int) Math.round(Math.random() * 999999));
			if(!event.getGuild().getSelfMember().hasPermission(Permission.ADMINISTRATOR)) {
				help.addField("", ":x: **WARNING**: Since you have not granted me the administrator permission, most of my features and commands won't work!", false);
			}
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

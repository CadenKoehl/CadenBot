package com.cadenkoehl.cadenbot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Constants extends ListenerAdapter {
	static String prefix;
	public static final long MODMAIL = 781421376086999040L;
	public static String TOKEN = "Nzc1ODA1MTY4MzE4MTUyNzA0.X6rq3Q.V8gIQZlhHTahou8fbOdDgVc60FA";
	public static long THETOWN = 730975912320827452L;
	public static int SERVERCOUNT;
	public static JDA jda;
	public static final String ERROR_MESSAGE = "A fatal error has occurred! If the issue persists, join the support server! (type -help)";
	public static long CADENBOTSUPPORT = 585338133177171968L;
	public static long CADENBOTBUGSCHANNEL = 787230827498700840L;
	public static long CadenID = 585334397914316820L;
	
	
	public void onGuildJoin(GuildJoinEvent event) {
		SERVERCOUNT = event.getJDA().getGuilds().size();
		event.getJDA().getPresence().setActivity(Activity.watching("for -help in " + SERVERCOUNT + " servers!"));
		System.out.println("I am now in " + SERVERCOUNT + " servers!");
	}
	public void onGuildLeave(GuildLeaveEvent event) {
		
		SERVERCOUNT = event.getJDA().getGuilds().size();
		event.getJDA().getPresence().setActivity(Activity.watching("for -help in " + SERVERCOUNT + " servers!"));
		System.out.println("I am now in " + SERVERCOUNT + " servers!");
			
	}
	public static String getPrefix(String id) {
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
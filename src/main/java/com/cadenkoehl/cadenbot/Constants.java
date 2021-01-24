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

public class Constants {
	private static String prefix;
	public static final long MODMAIL = 781421376086999040L;
	public static JDA jda;
	public static final String ERROR_MESSAGE = ":x: A fatal error has occurred! If the issue persists, join the support server! (type -help)";
	public static long CADENBOTSUPPORT = 585338133177171968L;
	public static long CADENBOTBUGSCHANNEL = 787230827498700840L;
	public static long CadenID = 585334397914316820L;
	public static final String[] HANGMAN = {
			"https://cdn.discordapp.com/attachments/785347122991595573/802056724261109780/hangman_6.png",
			"https://cdn.discordapp.com/attachments/785347122991595573/802056659371163658/hangman_5.png",
			"https://cdn.discordapp.com/attachments/785347122991595573/802056560314155028/hangman_4.png",
			"https://cdn.discordapp.com/attachments/785347122991595573/802056495192997948/hangman_3.png",
			"https://cdn.discordapp.com/attachments/785347122991595573/802056293534662676/hangman_2.png",
			"https://cdn.discordapp.com/attachments/785347122991595573/802056211847053332/hangman_1.png",
			"https://cdn.discordapp.com/attachments/785347122991595573/802056119748001812/hangman_0.png",
	};

	public static String[] getHangmanWords(String username) {
		return new String[]{
				"information",
				"rhythm",
				"material",
				"confession",
				"celebration",
				"reluctance",
				"demonstration",
				"commission",
				"message",
				"presentation",
				"parameter",
				"mechanical",
				"continuous",
				"electronics",
				"technology",
				"correction",
				"extract",
				"motivation",
				"cadenbot",
				"airplane",
				"firetruck",
				"painting",
				"easily",
				"discord",
				"youtube",
				"violin",
				"integer",
				"argument",
				"divorce",
				"document"
		};
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
		return prefix;
	}
	public static String getToken() {
		File file = new File(CadenBot.dataDirectory + "token.txt");
		Scanner scan = null;
		try {
			scan = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return scan.nextLine();
	}
	public static String getTestToken() {
		File file = new File(CadenBot.dataDirectory + "testbot_token.txt");
		Scanner scan = null;
		try {
			scan = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return scan.nextLine();
	}
}
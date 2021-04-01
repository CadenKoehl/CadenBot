package com.cadenkoehl.cadenbot.util;

import com.cadenkoehl.cadenbot.CadenBot;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Constants {
	private static String prefix;
	public static final long MODMAIL = 781421376086999040L;
	public static JDA jda;
	public static final String ERROR_MESSAGE = ":x: A fatal error has occurred! If the issue persists, join the support server! (type -help)";
	public static final Guild CADENBOTSUPPORT = CadenBot.jda.getGuildById("585338133177171968");
	public static final TextChannel CADENBOTANNOUNCEMENTS = CadenBot.jda.getTextChannelById("780880846588215317");
	public static final TextChannel CADENBOTBUGSCHANNEL = CadenBot.jda.getTextChannelById("787230827498700840");
	public static final TextChannel CONSOLE_CHANNEL = CadenBot.jda.getTextChannelById("826295073771225108");
	public static final User CADEN = CadenBot.jda.getUserById("585334397914316820");
	public static final String[] HANGMAN = {
			"https://cdn.discordapp.com/attachments/785347122991595573/802056724261109780/hangman_6.png",
			"https://cdn.discordapp.com/attachments/785347122991595573/802056659371163658/hangman_5.png",
			"https://cdn.discordapp.com/attachments/785347122991595573/802056560314155028/hangman_4.png",
			"https://cdn.discordapp.com/attachments/785347122991595573/802056495192997948/hangman_3.png",
			"https://cdn.discordapp.com/attachments/785347122991595573/802056293534662676/hangman_2.png",
			"https://cdn.discordapp.com/attachments/785347122991595573/802056211847053332/hangman_1.png",
			"https://cdn.discordapp.com/attachments/785347122991595573/802056119748001812/hangman_0.png",
	};

	public static String[] getHangmanWords() {
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

	/**
	 * @deprecated because the new {@code getPrefix()} method takes in a {@code guild} instead of a Guild ID. This makes writing a lot faster.
	 */
	@Deprecated
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

	public static String getPrefix(Guild guild) {
		String id = guild.getId();
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
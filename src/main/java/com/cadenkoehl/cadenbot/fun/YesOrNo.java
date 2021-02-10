package com.cadenkoehl.cadenbot.fun;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import com.cadenkoehl.cadenbot.CadenBot;

import com.cadenkoehl.cadenbot.util.Constants;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class YesOrNo extends ListenerAdapter {
	private static String id;
	private static String prefix = "-";
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		id = event.getGuild().getId();
		String[] args = event.getMessage().getContentRaw().split(" ");	     
		String[] messages = {
	    		  "Definently", 
	    		  "Yes",
	    		  "No",
	    		  "Maybe",
	    		  "***Hell*** no",
	    		  "***Of course!*** Dude is that even a question?",
	    		  "I am not sure",
	    		  "tbh i actually dont know lmao",
	    		  "ofc not, dumbass ",
	    		  "yes ofc lmao",
	    		  "Of course, dumbass"
	      };
	      
	      Random random = new Random();
	      int message = random.nextInt(messages.length);
		
		
		if (args[0].equalsIgnoreCase(getPrefix() + "yesorno")) {
			
			event.getChannel().sendTyping().delay(500L, TimeUnit.MILLISECONDS).complete();
			event.getChannel().sendMessage("The answer is:").queue();
			
			
			event.getChannel().sendTyping().delay(5000L, TimeUnit.MILLISECONDS).complete();
			event.getChannel().sendMessage(messages[message]).queue();
				
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

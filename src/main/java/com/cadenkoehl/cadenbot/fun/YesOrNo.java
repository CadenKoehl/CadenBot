package com.cadenkoehl.cadenbot.fun;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandCategory;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandEvent;
import com.cadenkoehl.cadenbot.util.exceptions.IncorrectUsageException;
import net.dv8tion.jda.api.Permission;

public class YesOrNo extends Command {
	@Override
	public void execute(CommandEvent event) throws IncorrectUsageException {
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

		event.getChannel().sendTyping().delay(500L, TimeUnit.MILLISECONDS).complete();
		event.getChannel().sendMessage("The answer is:").queue();


		event.getChannel().sendTyping().delay(5000L, TimeUnit.MILLISECONDS).complete();
		event.getChannel().sendMessage(messages[message]).queue();
	}

	@Override
	public String getName() {
		return "YesOrNo";
	}

	@Override
	public String getDescription() {
		return "Ask a yes or no question!";
	}

	@Override
	public CommandCategory getCategory() {
		return CommandCategory.FUN;
	}

	@Override
	public Permission getRequiredPermission() {
		return null;
	}

	@Override
	public String getUsage(String prefix) {
		return "YesOrNo` `[question]`";
	}

	@Override
	public String[] getAliases() {
		return new String[]{"8ball"};
	}
}

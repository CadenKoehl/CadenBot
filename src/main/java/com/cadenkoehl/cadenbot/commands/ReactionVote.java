package com.cadenkoehl.cadenbot.commands;

import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandCategory;
import com.cadenkoehl.cadenbot.util.exceptions.IncorrectUsageException;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class ReactionVote extends Command {

	@Override
	public void execute(GuildMessageReceivedEvent event) throws IncorrectUsageException {
		event.getMessage().addReaction("✅").queue();
		event.getMessage().addReaction("❌").queue();
	}

	@Override
	public String getName() {
		return "vote";
	}

	@Override
	public String getDescription() {
		return "Turn your message into a vote!";
	}

	@Override
	public CommandCategory getCategory() {
		return CommandCategory.COMMAND;
	}

	@Override
	public Permission getRequiredPermission() {
		return null;
	}

	@Override
	public String getUsage(String prefix) {
		return "vote` `[something to vote on]`";
	}

	@Override
	public String[] getAliases() {
		return new String[]{"pull"};
	}
}

package com.cadenkoehl.cadenbot.commands;

import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandCategory;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandEvent;
import net.dv8tion.jda.api.Permission;

public class Info extends Command {

	@Override
	public void execute(CommandEvent event) {
		event.getChannel().sendMessage("I am a discord bot currently in development! There are still many bugs trying to be fixed, and many new features soon to be added! If you wanna see my progress, join my support server! (I sent an invite in dm **:>**)").queue();

		event.getMember().getUser().openPrivateChannel().queue((channel) -> {
			channel.sendMessage("https://discord.gg/7gnu5nVMA8").queue();

		});
	}

	@Override
	public String getName() {
		return "info";
	}

	@Override
	public String getDescription() {
		return "Basic info about me!";
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
		return "info`";
	}

	@Override
	public String[] getAliases() {
		return new String[0];
	}
}

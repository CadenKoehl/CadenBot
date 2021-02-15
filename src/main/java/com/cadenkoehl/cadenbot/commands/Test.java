package com.cadenkoehl.cadenbot.commands;

import com.cadenkoehl.cadenbot.commands.util.Command;
import com.cadenkoehl.cadenbot.commands.util.IncorrectUsageException;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Test extends Command {
	GuildMessageReceivedEvent event;
	@Override
	public void execute(GuildMessageReceivedEvent event) throws IncorrectUsageException {
		this.event = event;
		if(args.length != 1) {
			throw new IncorrectUsageException(prefix + "test");
		}
		event.getChannel().sendMessage("test").queue();
	}

	@Override
	public String[] getNames() {
		return new String[]{"test", "testing", "testCommand"};
	}
}

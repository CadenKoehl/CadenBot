package com.cadenkoehl.cadenbot.commands;

import com.cadenkoehl.cadenbot.CadenBot;
import com.cadenkoehl.cadenbot.util.Constants;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Test extends Command {

	@Override
	public void execute(GuildMessageReceivedEvent event) {
		event.getChannel().sendMessage("test").queue();
	}

	@Override
	public String[] getNames() {
		return new String[]{"test", "testing", "testCommand"};
	}

	@Override
	public String getDescription() {
		return "A test command!";
	}
}

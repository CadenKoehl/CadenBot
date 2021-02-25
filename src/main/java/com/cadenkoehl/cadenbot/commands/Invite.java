package com.cadenkoehl.cadenbot.commands;

import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandCategory;
import com.cadenkoehl.cadenbot.util.EmbedColor;
import com.cadenkoehl.cadenbot.util.exceptions.IncorrectUsageException;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;


public class Invite extends Command {

	@Override
	public void execute(GuildMessageReceivedEvent event) throws IncorrectUsageException {
		EmbedBuilder info = new EmbedBuilder();
		info.setAuthor("Use the link below to invite me to your server!", null, event.getJDA().getSelfUser().getEffectiveAvatarUrl());
		info.setDescription("[Click Here!](https://discord.com/api/oauth2/authorize?client_id=775805168318152704&permissions=8&scope=bot)");
		info.setColor(EmbedColor.random());

		event.getChannel().sendMessage(info.build()).queue();
	}

	@Override
	public String getName() {
		return "invite";
	}

	@Override
	public String getDescription() {
		return "Invite me to your server!";
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
		return "invite`";
	}

	@Override
	public String[] getAliases() {
		return new String[0];
	}
}
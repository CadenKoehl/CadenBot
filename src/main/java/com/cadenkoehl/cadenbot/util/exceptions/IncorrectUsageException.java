package com.cadenkoehl.cadenbot.util.exceptions;

import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandEvent;
import com.cadenkoehl.cadenbot.util.Constants;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class IncorrectUsageException extends Exception {
    public IncorrectUsageException(Command cmd, CommandEvent event) {
        super(":x: **Incomplete Command!**\nUsage: `" + Constants.getPrefix(event.getGuild()) + cmd.getUsage(Constants.getPrefix(event.getGuild())));
    }
    public IncorrectUsageException(String errorMsg, Command cmd, CommandEvent event) {
        super(":x: **" + errorMsg + "**\nUsage: `" + Constants.getPrefix(event.getGuild()) + cmd.getUsage(Constants.getPrefix(event.getGuild())));
    }
    public IncorrectUsageException(String errorMsg, CommandEvent event) {
        super(":x: **" + errorMsg + "**\nUsage: `" + Constants.getPrefix(event.getGuild()) + event.getCommand().getUsage(Constants.getPrefix(event.getGuild())));
    }
    public IncorrectUsageException(CommandEvent event) {
        super(":x: **Incomplete Command!**\nUsage: `" + Constants.getPrefix(event.getGuild()) + event.getCommand().getUsage(Constants.getPrefix(event.getGuild())));
    }
}

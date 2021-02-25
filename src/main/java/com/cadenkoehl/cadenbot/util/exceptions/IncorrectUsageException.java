package com.cadenkoehl.cadenbot.util.exceptions;

import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.util.Constants;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class IncorrectUsageException extends Exception {
    public IncorrectUsageException(Command cmd, GuildMessageReceivedEvent event) {
        super(":x: **Incomplete Command!**\nUsage: `" + Constants.getPrefix(event.getGuild()) + cmd.getUsage(Constants.getPrefix(event.getGuild())));
    }
    public IncorrectUsageException(String errorMsg, Command cmd, GuildMessageReceivedEvent event) {
        super(":x: **" + errorMsg + "**\nUsage: `" + Constants.getPrefix(event.getGuild()) + cmd.getUsage(Constants.getPrefix(event.getGuild())));
    }
}

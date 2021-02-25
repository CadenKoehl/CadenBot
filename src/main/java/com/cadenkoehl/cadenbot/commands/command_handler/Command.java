package com.cadenkoehl.cadenbot.commands.command_handler;

import com.cadenkoehl.cadenbot.util.Constants;
import com.cadenkoehl.cadenbot.util.exceptions.IncorrectUsageException;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public abstract class Command {

    public abstract void execute(GuildMessageReceivedEvent event) throws IncorrectUsageException;

    public abstract String getName();

    public abstract String getDescription();

    public abstract CommandCategory getCategory();

    public abstract Permission getRequiredPermission();

    public abstract String getUsage(String prefix);

    public abstract String[] getAliases();

    public String[] getArgs(GuildMessageReceivedEvent event) {
        return event.getMessage().getContentRaw().split("\\s+");
    }

    public String getPrefix(GuildMessageReceivedEvent event) {
        return Constants.getPrefix(event.getGuild());
    }

}
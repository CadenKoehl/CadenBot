package com.cadenkoehl.cadenbot.commands.util;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public abstract class Command {
    public static String prefix;
    public static String[] args;
    abstract public void execute(GuildMessageReceivedEvent event) throws IncorrectUsageException;
    abstract public String[] getNames();
}
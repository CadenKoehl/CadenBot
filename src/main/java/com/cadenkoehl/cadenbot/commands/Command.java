package com.cadenkoehl.cadenbot.commands;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public abstract class Command {

    public String[] getArgs(GuildMessageReceivedEvent event) {
        return event.getMessage().getContentRaw().split("\\s+");
    }

    abstract public void execute(GuildMessageReceivedEvent event);
    abstract public String[] getNames();
    abstract public String getDescription();
}
package com.cadenkoehl.cadenbot.support_tickets.config.commands;

import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandCategory;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandEvent;
import com.cadenkoehl.cadenbot.support_tickets.SupportTicket;
import com.cadenkoehl.cadenbot.support_tickets.config.TicketConfig;
import com.cadenkoehl.cadenbot.util.exceptions.IncorrectUsageException;
import net.dv8tion.jda.api.Permission;

import java.util.Objects;

public class CloseTicketCmd extends Command {
    @Override
    public void execute(CommandEvent event) throws IncorrectUsageException {

        if(!TicketConfig.isTicketChannel(event.getChannel())) {
            event.error("This command only works in a support ticket channel!");
            return;
        }

        SupportTicket ticket = Objects.requireNonNull(TicketConfig.getInstance(event.getGuild())).getTicket(event.getMember());

        event.getChannel().sendMessage("Closing...").complete();
        ticket.close(ticket.getMember(), event.skipArgs(1));
    }

    @Override
    public String getName() {
        return "close";
    }

    @Override
    public String getDescription() {
        return "Close a support ticket!";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.TICKETS;
    }

    @Override
    public Permission getRequiredPermission() {
        return Permission.MANAGE_SERVER;
    }

    @Override
    public String getUsage(String prefix) {
        return "close` `[reason]` (this only works in a support ticket channel)";
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }
}

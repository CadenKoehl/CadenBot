package com.cadenkoehl.cadenbot.staff.commands;

import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandCategory;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandEvent;
import com.cadenkoehl.cadenbot.util.exceptions.IncorrectUsageException;
import net.dv8tion.jda.api.Permission;

import java.util.concurrent.TimeUnit;

public class Clear extends Command {
    @Override
    public void execute(CommandEvent event) throws IncorrectUsageException {
        String[] args = event.getArgs();

        if(args.length == 1) throw new IncorrectUsageException(event);

        int messages;

        try {
            messages = Integer.parseInt(args[1]);
        }
        catch (NumberFormatException ex) {
            throw new IncorrectUsageException("Invalid Number!", event);
        }

        if(messages > 100) throw new IncorrectUsageException("I can only delete 100 messages at once!", event);
        if(messages < 1) throw new IncorrectUsageException("Must be a number above 0!", event);

        event.getChannel().getHistory().retrievePast(messages).queue(history -> {

            if(history.size() < 1) {
                event.getChannel().sendMessage("There are no messages to clear in this channel!").queue();
                return;
            }

            int i;

            for(i = 0; i < messages; i++) {
                if(i == history.size()) break;
                history.get(i).delete().queue();
            }

            event.getChannel().sendMessage(":white_check_mark: Successfully deleted **" + i + "** messages!").queue(message -> {
                message.delete().queueAfter(2, TimeUnit.SECONDS);
            });
        });

    }

    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public String getDescription() {
        return "Delete a certain amount of messages in a channel";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.STAFF;
    }

    @Override
    public Permission getRequiredPermission() {
        return Permission.MESSAGE_MANAGE;
    }

    @Override
    public String getUsage(String prefix) {
        return "clear` `[number of messages]`";
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }
}

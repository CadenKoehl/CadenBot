package com.cadenkoehl.cadenbot.staff.commands.warn;

import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandCategory;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandEvent;
import com.cadenkoehl.cadenbot.util.exceptions.IncorrectUsageException;
import net.dv8tion.jda.api.Permission;

public class Unban extends Command {
    @Override
    public void execute(CommandEvent event) throws IncorrectUsageException {
        event.getChannel().sendMessage(":x: Since you cannot @ someone who is not in the server, it's a pain to unban someone with a command, so just go to Server Settings -> Bans, and unban the user!").queue();
    }

    @Override
    public String getName() {
        return "unban";
    }

    @Override
    public String getDescription() {
        return "Unban a member";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.STAFF;
    }

    @Override
    public Permission getRequiredPermission() {
        return Permission.BAN_MEMBERS;
    }

    @Override
    public String getUsage(String prefix) {
        return "unban`";
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }
}

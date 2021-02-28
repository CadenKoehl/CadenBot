package com.cadenkoehl.cadenbot.applications;

import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandCategory;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandEvent;
import com.cadenkoehl.cadenbot.util.exceptions.IncorrectUsageException;
import net.dv8tion.jda.api.Permission;

public class ListApps extends Command {
    @Override
    public void execute(CommandEvent event) throws IncorrectUsageException {

    }

    @Override
    public String getName() {
        return "listapps";
    }

    @Override
    public String getDescription() {
        return "View all applications on this server!";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.APPLICATIONS;
    }

    @Override
    public Permission getRequiredPermission() {
        return null;
    }

    @Override
    public String getUsage(String prefix) {
        return "listapps";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"listaps", "appslist", "apslist"};
    }
}

package com.cadenkoehl.cadenbot.applications;

import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandCategory;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandEvent;
import com.cadenkoehl.cadenbot.util.exceptions.IncorrectUsageException;
import net.dv8tion.jda.api.Permission;

public class CreateApp extends Command {
    @Override
    public void execute(CommandEvent event) throws IncorrectUsageException {

        ApplicationManager appManager = new ApplicationManager();

        String[] args = event.getArgs();
        if(args.length == 1) {
            throw new IncorrectUsageException("Please specify a name for the application!", this, event);
        }
        String name = args[1];

        if(name.length() > 20) {
            throw new IncorrectUsageException("Name must be under 20 characters!", this, event);
        }

        if(name.contains(" ") || name.contains(".")) throw new IncorrectUsageException("Name must not have spaces or periods!", this, event);

        if(appManager.createApplication(event.getGuild(), name)) event.getChannel().sendMessage(":white_check_mark: **Success!** The application \"" + name + "\" was created!").queue();
        else throw new IncorrectUsageException("The application \"" + name + "\" already exists!", this, event);
    }

    @Override
    public String getName() {
        return "createapp";
    }

    @Override
    public String getDescription() {
        return "Create a new application!";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.APPLICATIONS;
    }

    @Override
    public Permission getRequiredPermission() {
        return Permission.MANAGE_SERVER;
    }

    @Override
    public String getUsage(String prefix) {
        return "createapp` `[application name]`";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"createap"};
    }
}

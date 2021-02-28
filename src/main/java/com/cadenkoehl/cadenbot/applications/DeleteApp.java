package com.cadenkoehl.cadenbot.applications;

import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandCategory;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandEvent;
import com.cadenkoehl.cadenbot.util.exceptions.IncorrectUsageException;
import net.dv8tion.jda.api.Permission;

public class DeleteApp extends Command {
    @Override
    public void execute(CommandEvent event) throws IncorrectUsageException {
        String[] args = event.getArgs();

        if(args.length == 1) throw new IncorrectUsageException("Please specify an application to delete!", event);

        ApplicationManager manager = new ApplicationManager();
        Application app = manager.getApplication(event.getGuild(), args[1]);

        if(app == null) {
            event.getChannel().sendMessage(":x: Could not find an application by the name of \"" + args[1] + "\"!").queue();
            return;
        }

        if(app.getFile().delete()) event.getChannel().sendMessage(":white_check_mark: The application was successfully deleted!").queue();
        else event.getChannel().sendMessage(":x: Something went wrong! Please try again!").queue();
    }

    @Override
    public String getName() {
        return "deleteapp";
    }

    @Override
    public String getDescription() {
        return "Delete an application!";
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
        return "deleteapp` `[application name]`";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"deleteap", "deletapp", "deletap"};
    }
}

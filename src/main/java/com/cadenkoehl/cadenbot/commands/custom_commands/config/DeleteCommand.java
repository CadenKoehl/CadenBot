package com.cadenkoehl.cadenbot.commands.custom_commands.config;

import com.cadenkoehl.cadenbot.CadenBot;
import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandCategory;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandEvent;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandHandler;
import com.cadenkoehl.cadenbot.util.Constants;
import com.cadenkoehl.cadenbot.util.exceptions.IncorrectUsageException;
import net.dv8tion.jda.api.Permission;

import java.io.File;

public class DeleteCommand extends Command {
    @Override
    public void execute(CommandEvent event) throws IncorrectUsageException {
        String[] args = event.getArgs();
        String prefix = event.getPrefix();

        if(args.length == 1) {
            throw new IncorrectUsageException(event);
        }

        File dir = new File(CadenBot.dataDirectory + "custom_commands/" + event.getGuild().getId());
        dir.mkdirs();

        File file = new File(dir, args[1] + ".txt");
        if(!file.exists()) {
            if(Command.getByName(args[1]) != null) {
                event.getChannel().sendMessage(":x: If you want to delete a default command, use `" + prefix + "toggle`").queue();
                return;
            }
            event.getChannel().sendMessage(":x: Command \"" + args[1] + "\" does not exist!").queue();
            return;
        }
        if(file.delete()) {
            CommandHandler.commands.remove(Command.getByName(args[1]));
            event.getChannel().sendMessage(":white_check_mark: Command was successfully deleted!").queue();
        }
        else {
            event.getChannel().sendMessage(Constants.ERROR_MESSAGE).queue();
        }
    }

    @Override
    public String getName() {
        return "deletecommand";
    }

    @Override
    public String getDescription() {
        return "Delete a custom command!";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.SETTINGS;
    }

    @Override
    public Permission getRequiredPermission() {
        return Permission.MANAGE_SERVER;
    }

    @Override
    public String getUsage(String prefix) {
        return "deletecommand` `[command name]`";
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }
}

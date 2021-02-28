package com.cadenkoehl.cadenbot.applications;

import com.cadenkoehl.cadenbot.CadenBot;
import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandCategory;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandEvent;
import com.cadenkoehl.cadenbot.util.exceptions.IncorrectUsageException;
import net.dv8tion.jda.api.Permission;

import java.io.File;

public class RemovePingRole extends Command {
    @Override
    public void execute(CommandEvent event) throws IncorrectUsageException {
        File file = new File(CadenBot.dataDirectory + "applications/rolemention/" + event.getGuild().getId() + ".txt");
        if(!file.delete()) {
            event.getChannel().sendMessage(":x: You don't have a ping role set!").queue();
            return;
        }
        event.getChannel().sendMessage(":white_check_mark: **Success**! I will not mention any roles when new apps are submitted!").queue();
    }

    @Override
    public String getName() {
        return "removepingrole";
    }

    @Override
    public String getDescription() {
        return "Make me not ping any roles when new apps are submitted!";
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
        return "removepingrole`";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"removepr"};
    }
}

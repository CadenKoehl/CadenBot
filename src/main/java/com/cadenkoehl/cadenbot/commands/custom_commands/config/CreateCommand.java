package com.cadenkoehl.cadenbot.commands.custom_commands.config;

import com.cadenkoehl.cadenbot.CadenBot;
import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandCategory;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandEvent;
import com.cadenkoehl.cadenbot.commands.custom_commands.CustomCommandFactory;
import com.cadenkoehl.cadenbot.util.exceptions.IncorrectUsageException;
import net.dv8tion.jda.api.Permission;

import java.io.File;

public class CreateCommand extends Command {

    @Override
    public void execute(CommandEvent event) throws IncorrectUsageException {
        CustomCommandFactory factory = CustomCommandFactory.get(event.getChannel(), event.getMember());

        if(factory == null) {
            File dir = new File(CadenBot.dataDirectory + "custom_commands/" + event.getGuild().getId());
            dir.mkdirs();

            if(dir.list().length > 10) {
                event.getChannel().sendMessage(":x: You cannot create more than 10 custom commands!").queue();
                return;
            }
            new CustomCommandFactory(event.getChannel(), event.getMember());
            event.getChannel().sendMessage("Enter the name of this command below").queue();
        }
    }

    @Override
    public String getName() {
        return "createcommand";
    }

    @Override
    public String getDescription() {
        return "Create a new custom command!";
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
        return "createcommand`";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"creatcmd", "createcomand"};
    }
}

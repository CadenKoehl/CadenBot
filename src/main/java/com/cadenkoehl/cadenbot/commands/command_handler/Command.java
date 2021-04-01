package com.cadenkoehl.cadenbot.commands.command_handler;

import com.cadenkoehl.cadenbot.commands.custom_commands.CustomCommand;
import com.cadenkoehl.cadenbot.util.exceptions.IncorrectUsageException;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;

public abstract class Command {

    public abstract void execute(CommandEvent event) throws IncorrectUsageException;

    public abstract String getName();

    public abstract String getDescription();

    public abstract CommandCategory getCategory();

    public abstract Permission getRequiredPermission();

    public abstract String getUsage(String prefix);

    public abstract String[] getAliases();


    public static Command getByName(String name, Guild guild) {
        for(Command cmd : CommandHandler.commands) {
            if(cmd instanceof CustomCommand) {
                CustomCommand customCmd = (CustomCommand) cmd;
                if(!customCmd.getGuild().getId().equals(guild.getId())) continue;
            }
            if(name.equalsIgnoreCase(cmd.getName())) return cmd;
        }
        return null;
    }
}
package com.cadenkoehl.cadenbot.commands.settings;

import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandCategory;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandEvent;
import com.cadenkoehl.cadenbot.util.exceptions.IncorrectUsageException;
import net.dv8tion.jda.api.Permission;

public class Toggle extends Command {
    @Override
    public void execute(CommandEvent event) throws IncorrectUsageException {
        String[] args = event.getArgs();

        if(args.length == 1) throw new IncorrectUsageException(event);

        GuildSettingsManager settings = new GuildSettingsManager(event.getGuild());
        Command cmd = Command.getByName(args[1], event.getGuild());

        if (cmd == null) throw new IncorrectUsageException("Command not found!", event);

        if(args[1].equalsIgnoreCase("toggle")) throw new IncorrectUsageException("You cannot toggle the toggle command!", event);
        if(args[1].equalsIgnoreCase("help")) throw new IncorrectUsageException("You cannot toggle the help command!", event);

        if(settings.isToggledOff(cmd)) {
            settings.toggleCommandOn(cmd);
            event.getChannel().sendMessage(":white_check_mark: **Success**! Members will now be able to use the " + cmd.getName() + " command!").queue();
        }
        else {
            settings.toggleCommandOff(cmd);
            event.getChannel().sendMessage(":white_check_mark: **Success**! Members will no longer be able to use the " + cmd.getName() + " command until you toggle it back on!").queue();
        }
    }

    @Override
    public String getName() {
        return "toggle";
    }

    @Override
    public String getDescription() {
        return "Turn commands on and off!";
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
        return "toggle` `[command name]`";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"togle", "tgl"};
    }
}

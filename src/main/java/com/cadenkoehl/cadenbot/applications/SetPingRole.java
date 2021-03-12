package com.cadenkoehl.cadenbot.applications;

import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandCategory;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandEvent;
import com.cadenkoehl.cadenbot.util.data.Data;
import com.cadenkoehl.cadenbot.util.exceptions.IncorrectUsageException;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Role;

import java.util.List;

public class SetPingRole extends Command {

    @Override
    public void execute(CommandEvent event) throws IncorrectUsageException {
        String[] args = event.getArgs();
        String prefix = event.getPrefix();

        List<Role> roles = event.getMessage().getMentionedRoles();

        if(roles.size() == 0) throw new IncorrectUsageException("Please specify a role!", event);

        Role role = roles.get(0);

        Data.writeToFile("applications/rolemention/", event.getGuild().getId() + ".txt", role.getId());

        event.getChannel().sendMessage(":white_check_mark: **Success**! I will now mention the **" + role.getName() + "** role when an application is submitted!").queue();

    }

    @Override
    public String getName() {
        return "setpingrole";
    }

    @Override
    public String getDescription() {
        return "Set a role that gets pinged when new apps are submitted!";
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
        return "setpingrole` `<@role>`";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"setpr"};
    }
}

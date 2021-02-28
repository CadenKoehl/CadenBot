package com.cadenkoehl.cadenbot.applications;

import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandCategory;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandEvent;
import com.cadenkoehl.cadenbot.util.exceptions.IncorrectUsageException;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;

import java.util.List;

public class RemoveQuestion extends Command {
    @Override
    public void execute(CommandEvent event) throws IncorrectUsageException {
        ApplicationManager manager = new ApplicationManager();
        String[] args = event.getArgs();
        Guild guild = event.getGuild();

        if(args.length != 3) throw new IncorrectUsageException(event);

        Application app = manager.getApplication(guild, args[1]);

        if(app == null) {
            event.getChannel().sendMessage(":x: Could not find an application by the name of \"" + args[1] + "\"").queue();
            return;
        }

        List<String> questions = app.getQuestions();

        int index;
        try {
            index = Integer.parseInt(args[2]);
        }
        catch (NumberFormatException ex) {
            throw new IncorrectUsageException("Invalid Number!", event);
        }

        if(index > questions.size() || index < 1) {
            event.getChannel().sendMessage(":x: Question " + index + " does not exist in this application!").queue();
            return;
        }

        manager.removeQuestion(guild, args[1], index - 1);

        event.getChannel().sendMessage(":white_check_mark: **Success**! Question " + index + " was removed from application \"" + app.getName() + "\"").queue();
    }

    @Override
    public String getName() {
        return "removequestion";
    }

    @Override
    public String getDescription() {
        return "Remove a question from an application!";
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
        return "removequestion` `[application name]` `[question number]`";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"removeq"};
    }
}

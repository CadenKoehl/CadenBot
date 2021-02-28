package com.cadenkoehl.cadenbot.applications;

import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandCategory;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandEvent;
import com.cadenkoehl.cadenbot.util.exceptions.IncorrectUsageException;
import net.dv8tion.jda.api.Permission;

import java.util.Arrays;
import java.util.stream.Collectors;

public class AddQuestion extends Command {

    @Override
    public void execute(CommandEvent event) throws IncorrectUsageException {
        String[] args = event.getArgs();

        if(args.length == 1) throw new IncorrectUsageException(this, event);

        String appName = args[1];

        String content = Arrays.stream(args).skip(2).collect(Collectors.joining(" "));

        ApplicationManager applicationManager = new ApplicationManager();
        Application application = applicationManager.getApplication(event.getGuild(), appName);

        if (application == null) {
            event.getChannel().sendMessage(":x: **Could not find an application by the name of **\"" + appName + "\"! Type `" + event.getPrefix() + "listapps` to view all applications on this server!").queue();
            return;
        }

        applicationManager.addQuestion(event.getGuild(), appName, content);

        event.getChannel().sendMessage(":white_check_mark: Successfully added question to \"" + appName + "\"").queue();

    }

    @Override
    public String getName() {
        return "addquestion";
    }

    @Override
    public String getDescription() {
        return "Add a question to your application!";
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
        return "addquestion` `[application name]` `[question content]`";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"addq", "adquestion", "adq"};
    }
}

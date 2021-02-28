package com.cadenkoehl.cadenbot.applications;

import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandCategory;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandEvent;
import com.cadenkoehl.cadenbot.util.Utils;
import com.cadenkoehl.cadenbot.util.exceptions.IncorrectUsageException;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.exceptions.ContextException;

public class ApplyCommand extends Command {

    @Override
    public void execute(CommandEvent event) throws IncorrectUsageException {
        String[] args = event.getArgs();
        String prefix = event.getPrefix();
        Member member = event.getMember();

        if(args.length == 1) throw new IncorrectUsageException(event);

        String appName = args[1];

        ApplicationManager applicationManager = new ApplicationManager();
        Application application = applicationManager.getApplication(event.getGuild(), appName);

        if(application == null) {
            event.getChannel().sendMessage(":x: **Could not find an application by the name of **\"" + appName + "\"! Type `" + prefix + "listapps` to view all applications on this server!").queue();
            return;
        }

        if(application.getQuestions().size() == 0) {
            if(!member.hasPermission(Permission.MANAGE_SERVER)) event.getChannel().sendMessage(":x: There is nothing in this application! Please ask your server administrators to add some questions to it! (type `" + prefix + "help` applications`)").queue();
            else event.getChannel().sendMessage(":x: There is nothing in this application! Please add some questions to it! (type `" + prefix + "help` applications`)").queue();
            return;
        }
        if(application.start(member)) event.getChannel().sendMessage(":white_check_mark: **Success**! Application started in DM!").queue();
        else event.getChannel().sendMessage(":x: **Error**! I was unable to DM you! Please check that you have DMs enabled for this server, and that you haven't blocked me!").queue();
    }

    @Override
    public String getName() {
        return "apply";
    }

    @Override
    public String getDescription() {
        return "Apply for an application on this server!";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.APPLICATIONS;
    }

    @Override
    public Permission getRequiredPermission() {
        return null;
    }

    @Override
    public String getUsage(String prefix) {
        return "apply` `[application name]`";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"aply"};
    }
}

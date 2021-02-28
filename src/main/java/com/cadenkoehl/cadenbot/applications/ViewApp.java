package com.cadenkoehl.cadenbot.applications;

import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandCategory;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandEvent;
import com.cadenkoehl.cadenbot.util.EmbedColor;
import com.cadenkoehl.cadenbot.util.exceptions.IncorrectUsageException;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;

import java.util.List;

public class ViewApp extends Command {
    @Override
    public void execute(CommandEvent event) throws IncorrectUsageException {
        String[] args = event.getArgs();
        ApplicationManager manager = new ApplicationManager();

        if(args.length == 1) throw new IncorrectUsageException("Please specify an application to view!", event);

        Application app = manager.getApplication(event.getGuild(), args[1]);
        if(app == null) {
            event.getChannel().sendMessage(":x: Could not find an application by the name of \"" + args[1] + "\"").queue();
            return;
        }

        List<String> questions = app.getQuestions();
        String questionString = "";

        for(int i = 0; i < questions.size(); i++) {
            questionString = questionString + "\n" + (i + 1) + ". " + questions.get(i);
        }

        if(questions.size() == 0) {
            questionString = ":x: This application doesn't have any questions yet!";
        }

        EmbedBuilder embed = new EmbedBuilder();
        embed.setAuthor(app.getName(), null, event.getGuild().getIconUrl());
        embed.addField("Questions", questionString, false);
        embed.setColor(EmbedColor.BLUE);
        event.getChannel().sendMessage(embed.build()).queue();

    }

    @Override
    public String getName() {
        return "viewapp";
    }

    @Override
    public String getDescription() {
        return "See details of an application!";
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
        return "viewapp` `[application name]`";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"veiwapp", "viewap", "veiwap"};
    }
}

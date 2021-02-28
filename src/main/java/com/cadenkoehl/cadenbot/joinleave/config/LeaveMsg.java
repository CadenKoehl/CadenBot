package com.cadenkoehl.cadenbot.joinleave.config;

import com.cadenkoehl.cadenbot.CadenBot;
import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandCategory;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandEvent;
import com.cadenkoehl.cadenbot.util.Constants;
import com.cadenkoehl.cadenbot.util.exceptions.IncorrectUsageException;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.User;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class LeaveMsg extends Command {

    @Override
    public void execute(CommandEvent event) throws IncorrectUsageException {
        String[] args = event.getArgs();
        User user = event.getAuthor();
        String guildId = event.getGuild().getId();
        if(args.length == 1) {
            event.getChannel().sendMessage("Please specify a message!").queue();
            return;
        }
        String message = Arrays.stream(args).skip(1).collect(Collectors.joining(" "));
        if(!message.contains("{user}")) {
            event.getChannel().sendMessage("Please put {user} in your message! (It must be lower case!) It will be replaced by the name of the user who joined!").queue();
            return;
        }
        try {
            File file = new File(CadenBot.dataDirectory + "joinleave/leavemessage/" + guildId + ".txt");
            FileWriter write = new FileWriter(file);
            write.write(message);
            write.close();
            event.getChannel().sendMessage(":white_check_mark: **Success**! Your goodbye message was saved! " +
                    "Here's an example of what it will look like:\n" +
                    message.replace("{user}", user.getAsTag())).queue();
        } catch (IOException e) {
            event.getChannel().sendMessage(Constants.ERROR_MESSAGE).queue();
            e.printStackTrace();
        }
    }

    @Override
    public String getName() {
        return "leavemsg";
    }

    @Override
    public String getDescription() {
        return "Set a custom goodbye message when someone leaves the server!";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.WELCOME_MESSAGES;
    }

    @Override
    public Permission getRequiredPermission() {
        return Permission.MANAGE_SERVER;
    }

    @Override
    public String getUsage(String prefix) {
        return "leavemsg` `[message]`";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"leavemessage", "goodbyemsg", "goodbyemessage"};
    }
}

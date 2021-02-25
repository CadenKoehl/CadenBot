package com.cadenkoehl.cadenbot.commands;

import com.cadenkoehl.cadenbot.CadenBot;
import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandCategory;
import com.cadenkoehl.cadenbot.util.ExceptionHandler;
import com.cadenkoehl.cadenbot.util.exceptions.IncorrectUsageException;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Prefix extends Command {

    @Override
    public void execute(GuildMessageReceivedEvent event) throws IncorrectUsageException {
        String prefix = Arrays.stream(this.getArgs(event)).skip(1).collect(Collectors.joining(" "));

        if(prefix.equalsIgnoreCase("") || prefix.contains(" ")) {
            if(prefix.contains(" ")) {
                event.getChannel().sendMessage("Prefix cannot have spaces!").queue();
            }
            if(prefix.equalsIgnoreCase("")) {
                event.getChannel().sendMessage("The current prefix for this server is `" + getPrefix(event) + "`").queue();
            }
        }
        else {

            File file = new File(CadenBot.dataDirectory + "prefix/" + event.getGuild().getId() + ".txt");
            try {
                if(file.createNewFile()) {
                    System.out.println(file.getPath() + " was created");
                }
                FileWriter write = new FileWriter(file);
                write.write(prefix);
                write.close();
                event.getChannel().sendMessage("My prefix on this server was changed to `" + prefix + "`").queue();
            }
            catch (IOException e) {
                ExceptionHandler.sendStackTrace(e);
            }
        }
    }

    @Override
    public String getName() {
        return "prefix";
    }

    @Override
    public String getDescription() {
        return "Change my prefix for this server!";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.STAFF;
    }

    @Override
    public Permission getRequiredPermission() {
        return Permission.MANAGE_SERVER;
    }

    @Override
    public String getUsage(String prefix) {
        return "prefix` `[new prefix]`";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"setprefix", "changeprefix"};
    }
}


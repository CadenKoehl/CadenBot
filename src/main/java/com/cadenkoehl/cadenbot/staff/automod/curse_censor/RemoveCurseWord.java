package com.cadenkoehl.cadenbot.staff.automod.curse_censor;

import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandCategory;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandEvent;
import com.cadenkoehl.cadenbot.util.data.Data;
import com.cadenkoehl.cadenbot.util.exceptions.IncorrectUsageException;
import net.dv8tion.jda.api.Permission;

import java.util.Arrays;
import java.util.stream.Collectors;

public class RemoveCurseWord extends Command {
    @Override
    public void execute(CommandEvent event) throws IncorrectUsageException {
        String[] args = event.getArgs();

        if(args.length == 1) throw new IncorrectUsageException(event);

        String word = Arrays.stream(args).skip(1).collect(Collectors.joining(" "));

        if(Data.removeLineFromFile("bad_words", event.getGuild().getId(), word)) {
            event.getChannel().sendMessage(":white_check_mark: **Success**! Removed word from list of blacklisted words!").queue();
        }
        else {
            event.getChannel().sendMessage(":x: Word was never blacklisted!").queue();
        }
    }

    @Override
    public String getName() {
        return "removecurse";
    }

    @Override
    public String getDescription() {
        return "Remove a blacklisted word!";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.AUTO_MOD;
    }

    @Override
    public Permission getRequiredPermission() {
        return Permission.BAN_MEMBERS;
    }

    @Override
    public String getUsage(String prefix) {
        return "removecurse` `[word]`";
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }
}
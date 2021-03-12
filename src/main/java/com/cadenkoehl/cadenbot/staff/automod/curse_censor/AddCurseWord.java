package com.cadenkoehl.cadenbot.staff.automod.curse_censor;

import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandCategory;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandEvent;
import com.cadenkoehl.cadenbot.util.data.Data;
import com.cadenkoehl.cadenbot.util.exceptions.IncorrectUsageException;
import net.dv8tion.jda.api.Permission;

import java.util.Arrays;
import java.util.stream.Collectors;

public class AddCurseWord extends Command {
    @Override
    public void execute(CommandEvent event) throws IncorrectUsageException {
        String[] args = event.getArgs();

        if(args.length == 1) throw new IncorrectUsageException(event);

        String word = Arrays.stream(args).skip(1).collect(Collectors.joining(" "));

        Data.appendToFile("bad_words", event.getGuild().getId(), word);

        event.getChannel().sendMessage("**Success**! Added word to list of blacklisted words!").queue();
    }

    @Override
    public String getName() {
        return "addcurse";
    }

    @Override
    public String getDescription() {
        return "Add a blacklisted word! (members will be warned for using it)";
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
        return "addcurse` `[word]`";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"addcurse", "addswear", "adcurse"};
    }
}

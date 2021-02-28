package com.cadenkoehl.cadenbot.staff.commands.suggest;

import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandCategory;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandEvent;
import com.cadenkoehl.cadenbot.util.Constants;
import com.cadenkoehl.cadenbot.util.exceptions.IncorrectUsageException;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;

import java.util.Arrays;
import java.util.stream.Collectors;

public class SuggestCmd extends Command {

    @Override
    public void execute(CommandEvent event) throws IncorrectUsageException {
        String[] args = event.getArgs();
        Member member = event.getMember();
        if(member == null) {
            event.getChannel().sendMessage(Constants.ERROR_MESSAGE).queue();
            return;
        }
        if(args.length == 1) {
            event.getChannel().sendMessage(":x: Please specify something to suggest!").queue();
            return;
        }
        SuggestionManager manager = new SuggestionManager();
        String content = Arrays.stream(args).skip(1).collect(Collectors.joining(" "));
        manager.addSuggestion(member, content, event.getChannel());
    }

    @Override
    public String getName() {
        return "suggest";
    }

    @Override
    public String getDescription() {
        return "Make a suggestion for this server!";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.SUGGESTIONS;
    }

    @Override
    public Permission getRequiredPermission() {
        return null;
    }

    @Override
    public String getUsage(String prefix) {
        return null;
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }
}

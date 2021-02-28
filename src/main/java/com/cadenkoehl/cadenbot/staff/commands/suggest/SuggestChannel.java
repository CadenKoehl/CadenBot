package com.cadenkoehl.cadenbot.staff.commands.suggest;

import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandCategory;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandEvent;
import com.cadenkoehl.cadenbot.util.exceptions.IncorrectUsageException;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.List;

public class SuggestChannel extends Command {

    @Override
    public void execute(CommandEvent event) throws IncorrectUsageException {
        SuggestionManager manager = new SuggestionManager();
        List<TextChannel> channels = event.getMessage().getMentionedChannels();
        TextChannel sugChannel = manager.getSuggestionChannel(event.getGuild());
        if(channels.size() == 0) {
            if(sugChannel == null) {
                event.getChannel().sendMessage(":x: Please specify a channel!").queue();
                return;
            }
            event.getChannel().sendMessage(sugChannel.getAsMention() + " is your current suggestion channel").queue();
            return;
        }
        TextChannel channel = channels.get(0);
        manager.setSuggestionChannel(channel);
        event.getChannel().sendMessage("Your suggestion channel was set to " + channel.getAsMention()).queue();
    }

    @Override
    public String getName() {
        return "sugchannel";
    }

    @Override
    public String getDescription() {
        return "Set the suggestion channel!";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.SUGGESTIONS;
    }

    @Override
    public Permission getRequiredPermission() {
        return Permission.MANAGE_CHANNEL;
    }

    @Override
    public String getUsage(String prefix) {
        return "sugchannel` `<#channel>`";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"suggestchannel", "suggestionchannel", "sugchanel"};
    }
}

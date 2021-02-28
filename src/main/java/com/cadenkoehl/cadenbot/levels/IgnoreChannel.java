package com.cadenkoehl.cadenbot.levels;

import com.cadenkoehl.cadenbot.CadenBot;
import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandCategory;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandEvent;
import com.cadenkoehl.cadenbot.util.ExceptionHandler;
import com.cadenkoehl.cadenbot.util.exceptions.IncorrectUsageException;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.TextChannel;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class IgnoreChannel extends Command {
    @Override
    public void execute(CommandEvent event) throws IncorrectUsageException {
        List<TextChannel> channels = event.getMessage().getMentionedChannels();
        if(channels.size() == 0) {
            throw new IncorrectUsageException(this, event);
        }
        String channelId = channels.get(0).getId();
        File file = new File(CadenBot.dataDirectory + "levels/ignored_channels/" + channelId + ".txt");
        if(file.exists()) {
            event.getChannel().sendMessage(event.getGuild().getTextChannelById(channelId).getAsMention() + " is already ignored").queue();
        }
        if(!file.exists()) {
            try {
                file.createNewFile();
            }
            catch (IOException ex) {
                ExceptionHandler.sendStackTrace(ex);
            }
            event.getChannel().sendMessage("Members will no longer gain xp from talking in " + event.getGuild().getTextChannelById(channelId).getAsMention()).queue();
        }
    }

    @Override
    public String getName() {
        return "ignorechannel";
    }

    @Override
    public String getDescription() {
        return "Stop users from earning xp in specific channels!";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.LEVELS;
    }

    @Override
    public Permission getRequiredPermission() {
        return Permission.MANAGE_SERVER;
    }

    @Override
    public String getUsage(String prefix) {
        return "ignorechannel` `<#channel>`";
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }
}

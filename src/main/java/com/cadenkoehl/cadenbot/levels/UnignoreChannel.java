package com.cadenkoehl.cadenbot.levels;

import com.cadenkoehl.cadenbot.CadenBot;
import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandCategory;
import com.cadenkoehl.cadenbot.util.exceptions.IncorrectUsageException;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.io.File;
import java.util.List;

public class UnignoreChannel extends Command {
    @Override
    public void execute(GuildMessageReceivedEvent event) throws IncorrectUsageException {
        List<TextChannel> channels = event.getMessage().getMentionedChannels();
        if(channels.size() == 0) {
            throw new IncorrectUsageException(this, event);
        }
        String channelId = channels.get(0).getId();
        File file = new File(CadenBot.dataDirectory + "levels/ignored_channels/" + channelId + ".txt");
        if(!file.exists()) {
            event.getChannel().sendMessage(event.getGuild().getTextChannelById(channelId).getAsMention() + " is not ignored").queue();
        }
        if(file.exists()) {
            file.delete();
            event.getChannel().sendMessage("Members will now be able to gain xp from talking in " + event.getGuild().getTextChannelById(channelId).getAsMention()).queue();
        }
    }

    @Override
    public String getName() {
        return "unignorechannel";
    }

    @Override
    public String getDescription() {
        return "Unignore a specific channel!";
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
        return "unignorechannel` `<#channel>`";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"unignore"};
    }
}

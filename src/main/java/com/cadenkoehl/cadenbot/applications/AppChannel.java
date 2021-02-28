package com.cadenkoehl.cadenbot.applications;

import com.cadenkoehl.cadenbot.CadenBot;
import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandCategory;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandEvent;
import com.cadenkoehl.cadenbot.util.exceptions.IncorrectUsageException;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

import java.io.File;
import java.util.List;

public class AppChannel extends Command {
    @Override
    public void execute(CommandEvent event) throws IncorrectUsageException {
        String[] args = event.getArgs();
        String prefix = event.getPrefix();

        ApplicationManager manager = new ApplicationManager();
        Guild guild = event.getGuild();

        File dir = new File(CadenBot.dataDirectory + "applications/channel/");
        if(dir.mkdirs()) System.out.println(dir.getPath() + " was created!");

        List<TextChannel> channels = event.getMessage().getMentionedChannels();

        if(channels.size() == 0) {
            TextChannel channel = manager.getApplicationChannel(guild);
            if(channel == null) throw new IncorrectUsageException(event);
            event.getChannel().sendMessage(channel.getAsMention() + " is the current application channel! To change it, type `" + prefix + "appchannel` `<#channel>`").queue();
            return;
        }

        manager.setApplicationChannel(channels.get(0));
        event.getChannel().sendMessage(":white_check_mark: **Success**! Incoming applications will now show up in " + channels.get(0).getAsMention()).queue();

    }

    @Override
    public String getName() {
        return "appchannel";
    }

    @Override
    public String getDescription() {
        return "Set the channel for incoming applications!";
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
        return "appchannel` `<#channel>`";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"apchannel", "apchanel", "appchanel", "apchnl", "applicationchannel"};
    }
}

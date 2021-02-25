package com.cadenkoehl.cadenbot.joinleave.config;

import com.cadenkoehl.cadenbot.CadenBot;
import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandCategory;
import com.cadenkoehl.cadenbot.util.Constants;
import com.cadenkoehl.cadenbot.util.ExceptionHandler;
import com.cadenkoehl.cadenbot.util.exceptions.IncorrectUsageException;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class JoinChannel extends Command {

    @Override
    public void execute(GuildMessageReceivedEvent event) throws IncorrectUsageException {
        List<TextChannel> channels = event.getMessage().getMentionedChannels();
        Guild guild = event.getGuild();
        String guildId = guild.getId();
        String prefix = Constants.getPrefix(guild);
        if(channels.size() == 0) {
            File file = new File(CadenBot.dataDirectory + "joinleave/joinchannel/" + guildId + ".txt");
            if(!file.exists()) {
                TextChannel systemChannel = guild.getSystemChannel();
                if(systemChannel == null) {
                    event.getChannel().sendMessage(":x: You currently have no channel set for welcome messages!\nTo set one, type `-joinchannel` `<#channel>").queue();
                    return;
                }
                event.getChannel().sendMessage(systemChannel.getAsMention() + " is your current channel for welcome messages!\nTo change it, type `-joinchannel` `<#channel>").queue();
                return;
            }
            try {
                Scanner scan = new Scanner(file);
                String channelId = scan.nextLine();
                TextChannel channel = guild.getTextChannelById(channelId);
                if (channel == null) {
                    event.getChannel().sendMessage(":x: You currently have no channel set for welcome messages!\nTo set one, type `" + prefix +  "joinchannel` `<#channel>`").queue();
                    return;
                }
                event.getChannel().sendMessage(channel.getAsMention() + " is your current channel for welcome messages!\nTo change it, type `" + prefix +  "joinchannel` `<#channel>`").queue();
                return;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        TextChannel channel = channels.get(0);
        String channelId = channel.getId();
        try {
            File file = new File(CadenBot.dataDirectory + "joinleave/joinchannel/" + guildId + ".txt");
            FileWriter write = new FileWriter(file);
            write.write(channelId);
            write.close();
            event.getChannel().sendMessage(":white_check_mark: **Success**! Welcome messages will now show up in " + channel.getAsMention()).queue();
        }
        catch (IOException ex) {
            ExceptionHandler.sendStackTrace(ex);
            event.getChannel().sendMessage(Constants.ERROR_MESSAGE).queue();
        }
    }

    @Override
    public String getName() {
        return "joinchannel";
    }

    @Override
    public String getDescription() {
        return "Set a custom welcome messages channel! ";
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
        return "joinchannel` `<#channel>`";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"welcomechannel"};
    }
}
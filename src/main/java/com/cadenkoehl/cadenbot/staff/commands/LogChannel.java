package com.cadenkoehl.cadenbot.staff.commands;

import com.cadenkoehl.cadenbot.CadenBot;
import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandCategory;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandEvent;
import com.cadenkoehl.cadenbot.util.Constants;
import com.cadenkoehl.cadenbot.util.exceptions.IncorrectUsageException;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class LogChannel extends Command {

    @Override
    public void execute(CommandEvent event) throws IncorrectUsageException {
        String[] args = event.getArgs();
        String prefix = event.getPrefix();
        if(args.length == 1) {
            String logChannelId = getLogChannelId(event.getGuild());
            TextChannel logChannel = event.getGuild().getTextChannelById(logChannelId);
            if(logChannel != null) {
                String channelMention = logChannel.getAsMention();
                event.getChannel().sendMessage(channelMention + " is the current log channel.\nTo change it, type `" + prefix + "logchannel` `<#channel>`").queue();
            }
            else {
                event.getChannel().sendMessage(":x: You do not have a log channel!\nTo set one, type `" + prefix + "logchannel` `<#channel>`").queue();
            }
            return;
        }
        List<TextChannel> channels = event.getMessage().getMentionedChannels();
        if(channels.size() == 0) {
            event.getChannel().sendMessage(":x: Please specify a channel!\n**Example:** " + prefix + "logchannel " + event.getChannel().getAsMention()).queue();
            return;
        }
        if(!event.getGuild().getSelfMember().hasPermission(Permission.ADMINISTRATOR)) {
            event.getChannel().sendMessage(":x: I need the `administrator` permission to perform this action!").queue();
            return;
        }
        TextChannel channel = channels.get(0);
        try {
            File file = new File(CadenBot.dataDirectory + "logging/" + event.getGuild().getId() + ".txt");
            FileWriter write = new FileWriter(file);
            write.write(channel.getId());
            write.close();
            event.getChannel().sendMessage(":white_check_mark: **Success**! Audit logs will now show up in " + channel.getAsMention()).queue();
            channel.sendMessage(":white_check_mark: Audit logs will now show up in this channel!").queue();
        } catch (IOException e) {
            e.printStackTrace();
            event.getChannel().sendMessage(Constants.ERROR_MESSAGE).queue();
        }
    }

    @Override
    public String getName() {
        return "logchannel";
    }

    @Override
    public String getDescription() {
        return "Set the audit-logging channel!";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.AUTO_MOD;
    }

    @Override
    public Permission getRequiredPermission() {
        return Permission.VIEW_AUDIT_LOGS;
    }

    @Override
    public String getUsage(String prefix) {
        return "logchannel` `<#channel>`";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"logchanel", "logchnl"};
    }

    public static String getLogChannelId(Guild guild) {
        String channelId;
        String guildId = guild.getId();
        File file = new File(CadenBot.dataDirectory + "logging/" + guildId + ".txt");
        if(!file.exists()) {
            return "";
        }
        try {
            Scanner scan = new Scanner(file);
            channelId = scan.nextLine();
        }
        catch (FileNotFoundException e) {
            return "";
        }
        return channelId;
    }
}
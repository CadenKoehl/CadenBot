package com.cadenkoehl.cadenbot.staff.commands;

import com.cadenkoehl.cadenbot.CadenBot;
import com.cadenkoehl.cadenbot.Constants;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class LogChannel extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        String prefix = Constants.getPrefix(event.getGuild().getId());
        if(args[0].equalsIgnoreCase(prefix + "logchannel")) {
            if(event.isWebhookMessage()) {
                return;
            }
            Member member = event.getMember();
            User user = member.getUser();
            if(user.isBot()) {
                return;
            }
            if(!member.hasPermission(Permission.VIEW_AUDIT_LOGS)) {
                event.getChannel().sendMessage(":x: You must have the `view_audit_logs` permission to use this command!").queue();
                return;
            }
            if(args.length == 1) {
                String logChannelId = getLogChannelId(event.getGuild());
                if(logChannelId != null) {
                    String channelMention = event.getGuild().getTextChannelById(logChannelId).getAsMention();
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
        if(args[0].equalsIgnoreCase(prefix + "logging")) {
            if(event.isWebhookMessage()) {
                return;
            }
            Member member = event.getMember();
            User user = member.getUser();
            if(user.isBot()) {
                return;
            }
            if(!member.hasPermission(Permission.VIEW_AUDIT_LOGS)) {
                event.getChannel().sendMessage(":x: You must have the `view_audit_logs` permission to use this command!").queue();
                return;
            }
            if(args.length != 2) {
                return;
            }
            if(args[1].equalsIgnoreCase("off")) {
                String guildId = event.getGuild().getId();
                File file = new File(CadenBot.dataDirectory + "logging/" + guildId + ".txt");
                if(!file.exists()) {
                    event.getChannel().sendMessage(":x: Logging is already off!").queue();
                    return;
                }
                file.delete();
                event.getChannel().sendMessage("Logging has been turned off!\nTo turn it back on, type `" + prefix + "logchannel` `<#channel>` to choose a new channel!").queue();
            }
        }
    }
    public static String getLogChannelId(Guild guild) {
        String channelId;
        String guildId = guild.getId();
        File file = new File(CadenBot.dataDirectory + "logging/" + guildId + ".txt");
        if(!file.exists()) {
            return null;
        }
        try {
            Scanner scan = new Scanner(file);
            channelId = scan.nextLine();
        }
        catch (FileNotFoundException e) {
            return null;
        }
        return channelId;
    }
}
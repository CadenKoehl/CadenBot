package com.cadenkoehl.cadenbot.joinleave.config;

import com.cadenkoehl.cadenbot.CadenBot;
import com.cadenkoehl.cadenbot.Constants;
import net.dv8tion.jda.api.Permission;
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

public class JoinChannel extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        String guildId = event.getGuild().getId();
        String prefix = Constants.getPrefix(guildId);
        if(args[0].equalsIgnoreCase(prefix + "joinchannel")) {
            if(event.isWebhookMessage()) {
                return;
            }
            Member member = event.getMember();
            User user = event.getAuthor();
            if(user.isBot()) {
                return;
            }
            if(!member.hasPermission(Permission.MANAGE_SERVER)) {
                event.getChannel().sendMessage(":x: You must have the `manage_server` permission to use this command!").queue();
                return;
            }
            List<TextChannel> channels = event.getMessage().getMentionedChannels();
            if(channels.size() == 0) {
                File file = new File(CadenBot.dataDirectory + "joinleave/joinchannel/" + guildId + ".txt");
                if(!file.exists()) {
                    TextChannel systemChannel = event.getGuild().getSystemChannel();
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
                    TextChannel channel = event.getGuild().getTextChannelById(channelId);
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
                ex.printStackTrace();
                event.getChannel().sendMessage(Constants.ERROR_MESSAGE).queue();
            }
        }
    }
}
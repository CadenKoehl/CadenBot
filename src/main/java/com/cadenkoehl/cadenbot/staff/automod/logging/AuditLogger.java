package com.cadenkoehl.cadenbot.staff.automod.logging;

import com.cadenkoehl.cadenbot.CadenBot;
import com.cadenkoehl.cadenbot.staff.commands.LogChannel;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class AuditLogger {

    public static void log(MessageEmbed embed, Guild guild) {
        String channelId = LogChannel.getLogChannelId(guild);
        if (channelId != null) {
            TextChannel channel = CadenBot.jda.getTextChannelById(channelId);
            if(channel == null) {
                File file = new File(CadenBot.dataDirectory + "logging/" + channelId + ".txt");
                if(file.exists()) {
                    file.delete();
                }
                return;
            }
            channel.sendMessage(embed).queue();
        }
    }

    public static void logAfter(MessageEmbed embed, Guild guild, int time, TimeUnit unit) {
        String channelId = LogChannel.getLogChannelId(guild);
        if (channelId != null) {
            TextChannel channel = CadenBot.jda.getTextChannelById(channelId);
            if(channel == null) {
                File file = new File(CadenBot.dataDirectory + "logging/" + channelId + ".txt");
                if(file.exists()) {
                    file.delete();
                }
                return;
            }
            channel.sendMessage(embed).queueAfter(time, unit);
        }
    }
}

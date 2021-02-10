package com.cadenkoehl.cadenbot.staff.logging;

import com.cadenkoehl.cadenbot.CadenBot;
import com.cadenkoehl.cadenbot.staff.commands.LogChannel;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;

import java.io.File;

public class Logger {
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
}

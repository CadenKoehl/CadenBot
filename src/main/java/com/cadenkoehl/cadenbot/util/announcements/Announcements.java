package com.cadenkoehl.cadenbot.util.announcements;

import com.cadenkoehl.cadenbot.util.Constants;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class Announcements extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        if(event.getChannel() == Constants.CADENBOTANNOUNCEMENTS && event.getAuthor() == Constants.CADEN) {

            TextChannel channel = AnnouncementManager.INSTANCE.getAnnouncementChannel(event.getGuild());
            if(channel == null) return;

            channel.sendMessage(event.getMessage().getContentRaw()).queue();


        }
    }
}

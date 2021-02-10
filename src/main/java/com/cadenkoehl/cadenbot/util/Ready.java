package com.cadenkoehl.cadenbot.util;

import com.cadenkoehl.cadenbot.util.Constants;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.Presence;
import org.jetbrains.annotations.NotNull;

public class Ready extends ListenerAdapter {

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        event.getJDA().getUserById(Constants.CadenID).openPrivateChannel().queue(channel -> {
            channel.sendMessage("I am back online!").queue();
        });
    }
}

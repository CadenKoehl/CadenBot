package com.cadenkoehl.cadenbot.util;

import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class Ready extends ListenerAdapter {

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        event.getJDA().getUserById(Constants.CadenID).openPrivateChannel().queue(channel -> {
            channel.sendMessage("I am back online!").queue();
        });
    }
}

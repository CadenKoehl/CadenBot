package com.cadenkoehl.cadenbot.util;

import com.cadenkoehl.cadenbot.commands.custom_commands.CustomCommandFactory;
import com.cadenkoehl.cadenbot.support_tickets.config.TicketConfig;
import com.cadenkoehl.cadenbot.util.data.Data;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Icon;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Ready extends ListenerAdapter {

    @Override
    public void onReady(@NotNull ReadyEvent event) {

        Constants.CADEN.openPrivateChannel().queue(channel -> {
            channel.sendMessage("I am back online!").queue();
        });

        List<Guild> guilds = event.getJDA().getGuilds();
        for(Guild guild : guilds) {
            if(guild.getEmotesByName("CadenBot", false).size() != 0) continue;
            try {
                guild.createEmote("CadenBot", Icon.from(Data.getSelfIconFile())).queue();
                System.out.println("Created emote for " + guild.getName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        CustomCommandFactory.loadCommands();
        TicketConfig.loadInstances();
    }
}
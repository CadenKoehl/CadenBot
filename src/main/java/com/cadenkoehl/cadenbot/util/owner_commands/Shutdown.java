package com.cadenkoehl.cadenbot.util.owner_commands;

import com.cadenkoehl.cadenbot.CadenBot;
import com.cadenkoehl.cadenbot.util.Constants;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

//This class doesn't use the command manager so it doesn't show up in -help
public class Shutdown extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        if(event.getAuthor().equals(Constants.CADEN) && event.getMessage().getContentRaw().equalsIgnoreCase("-shutdown")) {
            event.getChannel().sendMessage(":wave: Cya!").queue();
            try {
                event.getJDA().getPresence().setStatus(OnlineStatus.OFFLINE);
                CadenBot.jda.shutdownNow();
                System.exit(0);
            }
            catch (Exception ex) {
                event.getChannel().sendMessage(":x: [**ERROR**]: `" + ex + "`").queue();
            }
        }
    }
}

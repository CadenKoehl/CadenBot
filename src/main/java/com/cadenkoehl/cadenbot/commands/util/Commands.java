package com.cadenkoehl.cadenbot.commands.util;

import com.cadenkoehl.cadenbot.commands.Test;
import com.cadenkoehl.cadenbot.util.Constants;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class Commands extends ListenerAdapter {
    String[] args;
    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        Command.prefix = Constants.getPrefix(event.getGuild().getId());
        Command.args = event.getMessage().getContentRaw().split("\\s+");
        args = event.getMessage().getContentRaw().split("\\s+");
        if(event.isWebhookMessage()) return;
        if(event.getAuthor().isBot()) return;
        executeCommands(event,
                new Test()
        );

    }
    private void executeCommands(GuildMessageReceivedEvent event, Command... commands) {
        String prefix = Constants.getPrefix(event.getGuild().getId());
        for(Command cmd : commands) {
            String[] names = cmd.getNames();
            for(String name : names) {
                name = prefix + name;
                if(!name.equalsIgnoreCase(args[0])) continue;
                event.getChannel().sendTyping().delay(300, TimeUnit.MILLISECONDS).complete();
                try {
                    cmd.execute(event);
                }
                catch (IncorrectUsageException ex) {
                    String message = ex.getMessage();
                    event.getChannel().sendMessage(":x: **Incomplete Command!**\nCorrect Usage:" + message).queue();
                }
            }
        }
    }
}

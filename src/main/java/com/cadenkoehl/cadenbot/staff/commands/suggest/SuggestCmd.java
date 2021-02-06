package com.cadenkoehl.cadenbot.staff.commands.suggest;

import com.cadenkoehl.cadenbot.util.Constants;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.stream.Collectors;

public class SuggestCmd extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        String guildId = event.getGuild().getId();
        String prefix = Constants.getPrefix(guildId);
        if(args[0].equalsIgnoreCase(prefix + "suggest")) {
            if(event.isWebhookMessage()) {
                return;
            }
            if(event.getAuthor().isBot()) {
                return;
            }
            Member member = event.getMember();
            if(member == null) {
                event.getChannel().sendMessage(Constants.ERROR_MESSAGE).queue();
                return;
            }
            if(args.length == 1) {
                event.getChannel().sendMessage(":x: Please specify something to suggest!").queue();
                return;
            }
            SuggestionManager manager = new SuggestionManager();
            String content = Arrays.stream(args).skip(1).collect(Collectors.joining(" "));
            manager.addSuggestion(member, content, event.getChannel());
        }
    }
}

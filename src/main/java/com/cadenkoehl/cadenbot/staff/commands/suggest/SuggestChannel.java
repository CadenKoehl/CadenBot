package com.cadenkoehl.cadenbot.staff.commands.suggest;

import com.cadenkoehl.cadenbot.util.Constants;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class SuggestChannel extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        String guildId = event.getGuild().getId();
        String prefix = Constants.getPrefix(guildId);
        if(args[0].equalsIgnoreCase(prefix + "suggestchannel") || args[0].equalsIgnoreCase(prefix + "sugchannel")) {
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
            if(!member.hasPermission(Permission.ADMINISTRATOR)) {
                event.getChannel().sendMessage(":x: You must have the `administrator` permission to use this command!").queue();
                return;
            }
            SuggestionManager manager = new SuggestionManager();
            List<TextChannel> channels = event.getMessage().getMentionedChannels();
            TextChannel sugChannel = manager.getSuggestionChannel(event.getGuild());
            if(channels.size() == 0) {
                if(sugChannel == null) {
                    event.getChannel().sendMessage(":x: Please specify a channel!").queue();
                    return;
                }
                event.getChannel().sendMessage(sugChannel.getAsMention() + " is your current suggestion channel").queue();
                return;
            }
            TextChannel channel = channels.get(0);
            manager.setSuggestionChannel(channel);
            event.getChannel().sendMessage("Your suggestion channel was set to " + channel.getAsMention()).queue();
        }
    }
}

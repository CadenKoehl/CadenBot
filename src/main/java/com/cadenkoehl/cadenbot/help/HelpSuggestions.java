package com.cadenkoehl.cadenbot.help;

import com.cadenkoehl.cadenbot.util.Constants;
import com.cadenkoehl.cadenbot.util.EmbedColor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class HelpSuggestions extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        String prefix = Constants.getPrefix(event.getGuild().getId());
        if(args[0].equalsIgnoreCase(prefix + "help")) {
            if(args.length == 1) {
                return;
            }
            if(args[1].equalsIgnoreCase("suggestions") || args[1].equalsIgnoreCase("sug") || args[1].equalsIgnoreCase("sugs")) {
                EmbedBuilder embed = new EmbedBuilder();
                embed.setTitle("Suggestions!");
                embed.setDescription("`" +
                        prefix + "suggest` `[suggestion]` - Suggest an idea for **" + event.getGuild().getName() + "**!\n`" +
                        prefix + "approve` `[suggestion number]` `[reason]` - Approve a suggestion!\n`" +
                        prefix + "deny` `[suggestion number]` `[reason]` - Deny a suggestion!\n`" +
                        prefix + "consider` `[suggestion number]` `[reason]` - Consider a suggestion\n`" +
                        prefix + "sugchannel` `<#channel>` Set the suggestion channel!\n" +
                        "*If you want to suggest a feature for CadenBot, join my support server!* (type `" + prefix + "help`)"
                );
                embed.setColor(EmbedColor.random());
                event.getChannel().sendMessage(embed.build()).queue();
            }
        }
    }
}

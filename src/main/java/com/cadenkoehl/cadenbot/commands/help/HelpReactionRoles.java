package com.cadenkoehl.cadenbot.commands.help;

import com.cadenkoehl.cadenbot.Constants;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class HelpReactionRoles extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        String guildId = event.getGuild().getId();
        String prefix = Constants.getPrefix(guildId);
        if(args[0].equalsIgnoreCase(prefix + "help-reactionroles")) {
            if(!event.getAuthor().isBot()) {
                EmbedBuilder embed = new EmbedBuilder();
                embed.setTitle("Reaction Roles!");
                embed.setDescription("Here's how you can easily create customizable reaction roles!\n**Usage:** `" + prefix + "reactionrole` `<#channel>` `<@role>` `<emote>` `[message-content]`\n*You must chose a custom emote from your server!*");
                embed.setColor((int) Math.round(Math.random() * 999999));
                event.getChannel().sendMessage(embed.build()).queue();
            }
        }
    }
}

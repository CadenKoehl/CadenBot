package com.cadenkoehl.cadenbot.help;

import com.cadenkoehl.cadenbot.util.Constants;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class HelpReactionRoles extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        String guildId = event.getGuild().getId();
        String prefix = Constants.getPrefix(guildId);
        if(args[0].equalsIgnoreCase(prefix + "help")) {
            if(args.length < 2) {
                return;
            }
            if(!args[1].equalsIgnoreCase("reactionroles")) {
                return;
            }
            if(!event.getAuthor().isBot()) {
                EmbedBuilder embed = new EmbedBuilder();
                embed.setTitle("Reaction Roles!");
                embed.setDescription("Here's how you can easily create unlimited customizable reaction roles!\n**Usage:** `" + prefix + "reactionrole` `<#channel>` `<@role>` `<emote>` `[message-content]`");
                embed.setColor((int) Math.round(Math.random() * 999999));
                event.getChannel().sendMessage(embed.build()).queue();
            }
        }
    }
}

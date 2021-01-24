package com.cadenkoehl.cadenbot.commands.help;

import com.cadenkoehl.cadenbot.Constants;
import com.cadenkoehl.cadenbot.EmbedColor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class HelpLevels extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String prefix = Constants.getPrefix(event.getGuild().getId());
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        if(args[0].equalsIgnoreCase(prefix + "help")) {
            if(args.length < 2) {
                return;
            }
            if(!args[1].equalsIgnoreCase("levels")) {
                return;
            }
            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("**Commands:**\n");
            embed.setDescription("`" + prefix + "rank` - View your RankCard!\n`" + prefix + "setlevel` - Allows staff to set a user's level\n`" + prefix + "levelchannel` `<#channel>` - Set a custom channel for leveling msgs!\n`" + prefix + "levelchannel` `default` - Resets the leveling channel to the default channel\n`" + prefix + "ignorechannel` `<#channel>` - Make users be unable to gain levels in a channel!\n`" + prefix + "unignorechannel` `<#channel>` - Unignore an ignored channel\n`" + prefix + "ignoredchannels` - See a list of ignored channels!");
            embed.setColor((int) Math.round(Math.random() * 999999));
            event.getChannel().sendMessage(embed.build()).queue();
        }
    }
}

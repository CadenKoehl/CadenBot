package com.cadenkoehl.cadenbot.help;

import com.cadenkoehl.cadenbot.util.Constants;
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
            embed.setTitle("⬆ **Levels!** ⬆ \n");
            embed.setDescription("`" +
                    prefix + "rank` - View your RankCard!\n`" +
                    prefix + "leaderboard` - View the server's leaderboard!\n`" +
                    prefix + "setlevel` - Allows staff to set a user's level\n`" +
                    prefix + "levelchannel` `<#channel>` - Set a custom channel for leveling msgs!\n`" +
                    prefix + "levelchannel` `default` - Resets the leveling channel to the default channel\n" +
                    "-------------\n`" +
                    prefix + "levelmsg` `[message]` - Set a custom message when someone levels up!\nExample: `" + prefix + "levelmsg` `Hello, {user} just leveled up to level {lvl}` \n{user} and {level} will be replaced with the user and their level)\n" +
                    "-------------\n`" +
                    prefix + "ignorechannel` `<#channel>` - Make users be unable to gain levels in a channel!\n`" +
                    prefix + "unignorechannel` `<#channel>` - Unignore an ignored channel\n`" +
                    prefix + "ignoredchannels` - See a list of ignored channels!");
            embed.setColor((int) Math.round(Math.random() * 999999));
            event.getChannel().sendMessage(embed.build()).queue();
        }
    }
}
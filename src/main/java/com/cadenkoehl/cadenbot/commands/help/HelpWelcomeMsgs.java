package com.cadenkoehl.cadenbot.commands.help;

import com.cadenkoehl.cadenbot.Constants;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class HelpWelcomeMsgs extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        String prefix = Constants.getPrefix(event.getGuild().getId());
        if(args[0].equalsIgnoreCase(prefix + "help")) {
            if(event.isWebhookMessage()) {
                return;
            }
            if(event.getAuthor().isBot()) {
                return;
            }
            if(args.length == 1) {
                return;
            }
            if(args[1].equalsIgnoreCase("welcomemsgs")) {
                EmbedBuilder embed = new EmbedBuilder();
                embed.setTitle("Join & Leave Messages!");
                embed.setColor((int) Math.round(Math.random() * 999999));
                embed.setDescription("`"
                        + prefix + "joinchannel` `<#channel>` - Set the channel that welcome msgs appear!\n`"
                        + prefix + "leavechannel` `<#channel>` - Set the channel that goodbye msgs appear!\n"
                        + "---------------\n`"
                        + prefix + "joinmsg` - Set a custom join msg! Example: `" + prefix + "joinmsg` `{user} hopped on the server!` (**{user}** will be replaced with the user who joined)\n"
                        + "---------------\n`"
                        + prefix + "leavemsg` - Set a custom leave msg! Example: `" + prefix + "leavemsg` `{user} left the server :(` (**{user}** will be replaced with the user who left)\n"
                        + "---------------\n`"
                        + prefix + "joinleave random` - Send a fun, randomized message when someone joins and leaves!"
                );
                event.getChannel().sendMessage(embed.build()).queue();
            }
        }
    }
}

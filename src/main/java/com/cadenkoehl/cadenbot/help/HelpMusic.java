package com.cadenkoehl.cadenbot.help;

import com.cadenkoehl.cadenbot.util.Constants;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class HelpMusic extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        String prefix = Constants.getPrefix(event.getGuild().getId());
        if(args[0].equalsIgnoreCase(prefix + "help")) {
            if(args.length < 2) {
                return;
            }
            if(!args[1].equalsIgnoreCase("music")) {
                return;
            }
            if(!event.getAuthor().isBot()) {
                EmbedBuilder embed = new EmbedBuilder();
                embed.setTitle("🎶 Music! 🎵");
                embed.setColor((int) Math.round(Math.random() * 999999));
                embed.setDescription("`" + prefix + "join` - Joins your current voice channel!" +
                        "\n`" + prefix + "leave` - Leaves your current voice channel!" +
                        "\n`" + prefix + "play` - Play a song!" +
                        "\n`" + prefix + "stop` - Stops the current song!" +
                        "\n`" + prefix + "skip` - Skips the current song!" +
                        "\n`" + prefix + "queue` - View all the upcoming songs!" +
                        "\n`" + prefix + "repeat` - Turn repeating on and off!" +
                        "\n`" + prefix + "np` - Details of the current song!");
                event.getChannel().sendMessage(embed.build()).queue();
            }
        }
    }
}

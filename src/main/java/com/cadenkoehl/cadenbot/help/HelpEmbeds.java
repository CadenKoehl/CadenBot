package com.cadenkoehl.cadenbot.help;

import com.cadenkoehl.cadenbot.CadenBot;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class HelpEmbeds extends ListenerAdapter {
    private static String id;
    private static String prefix = "-";
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        id = event.getGuild().getId();
        if(args[0].equalsIgnoreCase(getPrefix() + "help")) {
            if(args.length < 2) {
                return;
            }
            if(!args[1].equalsIgnoreCase("embeds")) {
                return;
            }
            event.getChannel().sendMessage("Here's how you can easily create awesome embeds on your server!\n**----------**\nExample: `" + prefix + "embed Embed Title Here | Embed Description Here| Embed Field 1 Here | Here is embed field number 2!`\n").queue();
        }
    }
    private static String getPrefix() {
        File file;
        try {
            file = new File(CadenBot.dataDirectory + "prefix/" + id + ".txt");
            Scanner scan = new Scanner(file);
            prefix = scan.nextLine();
        }
        catch (FileNotFoundException ex) {
            prefix = "-";
        }
        return prefix;
    }
}

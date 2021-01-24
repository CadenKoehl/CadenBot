package com.cadenkoehl.cadenbot.commands.help;

import com.cadenkoehl.cadenbot.CadenBot;
import com.cadenkoehl.cadenbot.Constants;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
            event.getChannel().sendMessage("Here's how you can easily create awesome embeds on your server!\n**----------**\n" +
                    "First, make sure that instead of spaces, you use \"-\". Don't worry, they won't show up in the final product. " +
                    "Only use spaces when specifying a new field, or element to the embed. \n**Usage:**:\n`" + getPrefix() + "embed` `embed-title-here` `embed-description-here` `embed-field-one` `embed-field-two`\n**----------**\n" +
                    "If you want to leave an element blank, just put a \"-\" instead.").queue();
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

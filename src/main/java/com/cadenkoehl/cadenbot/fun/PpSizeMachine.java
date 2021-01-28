package com.cadenkoehl.cadenbot.fun;

import com.cadenkoehl.cadenbot.CadenBot;
import com.cadenkoehl.cadenbot.util.Constants;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class PpSizeMachine extends ListenerAdapter {
    private static String id;
    private static String prefix = "-";
    String name;
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        id = event.getGuild().getId();
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        if(args[0].equalsIgnoreCase(getPrefix() + "pp")) {
            try {
                name = event.getMessage().getMentionedMembers().get(0).getEffectiveName();
            }
            catch (IndexOutOfBoundsException ex) {
                name = event.getMember().getEffectiveName();
            }
            Random random = new Random();
            String[] pps = {
                    "8=D",
                    "8==D",
                    "8===D",
                    "8====D",
                    "8====D",
                    "8=====D",
                    "8======D",
                    "8=======D",
                    "8========D",
                    "8==========D",
                    "8=============D",
                    "8==============D",
                    "8===============D",
                    "A fatal error has occurred: `Cannot calculate PeePee size because " + name + "'s Pee Pee is too small!`",
                    "A fatal error has occurred: `Cannot calculate PeePee size because PeePee is null (" + name + " is not a guy)`"
            };
            int pp = random.nextInt(pps.length);
            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle(name + "'s PeePee");
            embed.setDescription(pps[pp]);
            embed.setColor((int) Math.round(Math.random() * 999999));
            event.getChannel().sendMessage(embed.build()).queue();
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
        catch (IOException ex) {
            ex.printStackTrace();
            CadenBot.jda.getTextChannelById(Constants.CADENBOTBUGSCHANNEL).sendMessage(CadenBot.jda.getUserById(Constants.CadenID).getAsMention() + " help! There is a huge bug in my code!! Someone tried to run a command, and this happened: " +
                    ex).queue();
        }
        return prefix;
    }
}

package com.cadenkoehl.cadenbot.commands;

import com.cadenkoehl.cadenbot.CadenBot;
import com.cadenkoehl.cadenbot.Constants;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Suggest extends ListenerAdapter {
    private static String id;
    private static String prefix = "-";
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        id = event.getGuild().getId();
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        String sug = Arrays.stream(args).skip(1).collect(Collectors.joining(" "));
        if(args[0].equalsIgnoreCase(getPrefix() + "suggest")) {
            if(event.getChannel().getId().equalsIgnoreCase("730994435088711690")) {
                Webhooks webhook = new Webhooks("https://discord.com/api/webhooks/795883873229078538/iHjy5_eo45_enlulGqMc8sMjYPnrJVJFWVNpYeaKFVTmd1deYmy7pHeT8HqTKTH9dNdM");
                webhook.setAvatarUrl(event.getMember().getUser().getEffectiveAvatarUrl());
                webhook.setUsername(event.getMember().getEffectiveName());
                webhook.setContent(sug);
                Webhooks confirmSug = new Webhooks("https://discord.com/api/webhooks/795888540240052294/1Ptx1wv53oevdvVjS37bPbqnHpXnDXmXuxXjoVCW80YjjvSkITqEtHE-O2imZ7kaTRrg");
                confirmSug.setUsername(event.getMember().getEffectiveName());
                confirmSug.setAvatarUrl(event.getMember().getUser().getEffectiveAvatarUrl());
                confirmSug.setContent("Your suggestion was added to suggestions!");

                try {
                    System.out.println("Webhook was executed!");
                    webhook.execute();
                    confirmSug.execute();
                } catch (IOException ex) {
                    ex.printStackTrace();
                    event.getChannel().sendMessage("A fatal error has occurred. Please contact Caden#7777 as soon as possible!").queue();
                }
            }
            if(event.getGuild().getId().equalsIgnoreCase("730975912320827452")) {
                if(!event.getChannel().getId().equalsIgnoreCase("730994435088711690")) {
                    event.getChannel().sendMessage("That command is only available in " + event.getGuild().getTextChannelById("730994435088711690").getAsMention() + "!").queue();
                }
            }
        }
        if(event.getChannel().getId().equalsIgnoreCase("795773307206893619")) {
            event.getMessage().addReaction("👍🏻").queue();
            event.getMessage().addReaction("👎🏻").queue();
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

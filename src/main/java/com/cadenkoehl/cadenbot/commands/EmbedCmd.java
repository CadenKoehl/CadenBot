package com.cadenkoehl.cadenbot.commands;

import com.cadenkoehl.cadenbot.CadenBot;
import com.cadenkoehl.cadenbot.util.Constants;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class EmbedCmd extends ListenerAdapter {
    private static String id;
    private static String prefix = "-";

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        id = event.getGuild().getId();
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        if(!event.getMessage().isWebhookMessage()) {
            if(!event.getAuthor().isBot()) {
                if(args[0].equalsIgnoreCase(getPrefix() + "embed") || args[0].equalsIgnoreCase(CadenBot.prefix + "eb")) {
                    if(event.getMember().hasPermission(Permission.MANAGE_CHANNEL)) {
                        String[] embedElement = Arrays.stream(args).skip(1).collect(Collectors.joining(" ")).split("\\|");
                        EmbedBuilder embed = new EmbedBuilder();
                        if(embedElement.length >= 1) {
                            embed.setTitle(embedElement[0]);
                        }

                        if(embedElement.length >= 2) {
                            embed.setDescription(embedElement[1]);
                        }

                        if(embedElement.length >= 3) {
                            embed.addField("", embedElement[2], false);
                        }

                        if(embedElement.length >= 4) {
                            embed.addField("", embedElement[3], false);
                        }

                        embed.setColor((int) Math.round(Math.random() * 999999));
                        event.getChannel().sendMessage(embed.build()).queue();
                        event.getMessage().delete().queueAfter(5, TimeUnit.SECONDS);
                    }
                    if(!event.getMember().hasPermission(Permission.MANAGE_CHANNEL)) {
                        event.getChannel().sendMessage("You must have the manage channel permission to use that command!").queue();
                    }
                }
            }
        }
    }

    public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        if(args[0].equalsIgnoreCase(CadenBot.prefix + "embed") || args[0].equalsIgnoreCase(CadenBot.prefix + "eb")) {
            event.getChannel().sendMessage("That command isn't available in private messages!").queue();
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

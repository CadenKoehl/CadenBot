package com.cadenkoehl.cadenbot.commands;

import com.cadenkoehl.cadenbot.CadenBot;
import com.cadenkoehl.cadenbot.Constants;
import com.cadenkoehl.cadenbot.EmbedColor;
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
                        try {
                            //Sets up all embed variables:
                            String titleRaw = Arrays.stream(args).skip(1).collect(Collectors.joining(" "));
                            String descRaw = Arrays.stream(args).skip(2).collect(Collectors.joining(" "));
                            String field1Raw = Arrays.stream(args).skip(3).collect(Collectors.joining(" "));
                            String field2Raw = Arrays.stream(args).skip(4).collect(Collectors.joining(" "));

                            String[] title = titleRaw.split(" ");
                            String[] desc = descRaw.split(" ");
                            String[] field1 = field1Raw.split(" ");
                            String[] field2 = field2Raw.split(" ");

                            //Creates the EmbedCmd
                            EmbedBuilder embed = new EmbedBuilder();

                            if(title[0] != "") {
                                embed.setTitle(title[0].replace("-", " "));
                            }
                            if(desc[0] != "") {
                                embed.setDescription(desc[0].replace("-", " "));
                            }
                            if(field1[0] != "") {
                                embed.addField("----------\n" + field1[0].replace("-", " "), "", false);
                            }
                            if(field2[0] != "") {
                                embed.addField(field2[0].replace("-", " "), "", false);
                            }
                            embed.setColor(EmbedColor.DARK_BLUE);
                            event.getChannel().sendMessage(embed.build()).queue();
                            event.getMessage().delete().queueAfter(5, TimeUnit.SECONDS);
                        } catch(Exception ex) {
                            event.getChannel().sendMessage("**Error**: Incomplete command!\n**----------**\n**Correct Usage:**:\n`-embed embed-title-here` `embed-description-here` `embed-field-one` `embed-field-two`\n**----------**\nIf this continues, join my support server! (do -help)").queue();
                            ex.printStackTrace();
                        }
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

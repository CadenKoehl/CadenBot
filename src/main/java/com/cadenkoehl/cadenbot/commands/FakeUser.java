package com.cadenkoehl.cadenbot.commands;

import com.cadenkoehl.cadenbot.CadenBot;
import com.cadenkoehl.cadenbot.Constants;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class FakeUser extends ListenerAdapter {
    private static String id;
    private static String prefix = "-";
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        id = event.getGuild().getId();
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        if(args[0].equalsIgnoreCase(getPrefix() + "fakemsg")) {
            if(event.getMessage().getMember().hasPermission(Permission.MANAGE_CHANNEL)) {
                try {
                    Member member = event.getMessage().getMentionedMembers().get(0);
                    String content = Arrays.stream(args).skip(2).collect(Collectors.joining(" "));
                    String url = event.getChannel().createWebhook("Fake User: " + member.getUser().getAsTag() + " Created by " + event.getAuthor().getAsTag()).complete().getUrl();
                    Webhooks webhook = new Webhooks(url);
                    webhook.setAvatarUrl(member.getUser().getEffectiveAvatarUrl());
                    webhook.setUsername(member.getEffectiveName());
                    webhook.setContent(content);
                    webhook.execute();
                    event.getMessage().delete().queue();
                    event.getChannel().retrieveWebhooks().complete().get(event.getChannel().retrieveWebhooks().complete().size() - 1).delete().queueAfter(4, TimeUnit.SECONDS);
                    System.out.println("test");
                }
                catch (Exception e) {
                    event.getChannel().sendMessage("**Error**: Incomplete command!\nUsage: `-fakemsg` `@<user>` `msg`").queue();
                }
            }
            if(!event.getMember().hasPermission(Permission.BAN_MEMBERS)) {
                event.getChannel().sendMessage("You can't use that!").queue();
            }
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

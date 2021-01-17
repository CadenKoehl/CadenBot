package com.cadenkoehl.cadenbot.commands;

import com.cadenkoehl.cadenbot.CadenBot;
import com.cadenkoehl.cadenbot.Constants;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Scanner;

public class UserInfo extends ListenerAdapter {
    private static String id;
    private static String prefix = "-";
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        id = event.getGuild().getId();
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        if(args[0].equalsIgnoreCase(getPrefix() + "profile")) {
            try {
                Member member = event.getMessage().getMentionedMembers().get(0);
                User user = member.getUser();
                String name = user.getName();
                String dayOfWeekCreated = user.getTimeCreated().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.US);
                String monthCreated = user.getTimeCreated().getMonth().getDisplayName(TextStyle.FULL, Locale.US);
                int dayCreated = user.getTimeCreated().getDayOfMonth();
                int yearCreated = user.getTimeCreated().getYear();
                String id = user.getId();
                String avatarURL = user.getEffectiveAvatarUrl();

                EmbedBuilder embed = new EmbedBuilder();
                embed.setAuthor(member.getUser().getAsTag(), null, member.getUser().getEffectiveAvatarUrl());
                if(user.isBot()) {
                    embed.setTitle(name + " **[BOT]**'s profile:\n------------------");
                }
                if(!user.isBot()) {
                    embed.setTitle(name + "'s profile:\n------------------");
                }
                embed.addField("**Account Created**: *" + dayOfWeekCreated + ", " + monthCreated + " " + dayCreated + ", " + yearCreated + "*","", false);
                embed.addField("**User ID**: *" + id + "*","", false);
                embed.addField("**Avatar URL**: ","[Click Here](" + avatarURL + " \"Click Here\")", false);
                embed.setColor((int) Math.round(Math.random() * 999999));
                event.getChannel().sendMessage(embed.build()).queue();
            }
            catch (IndexOutOfBoundsException ex) {
                event.getChannel().sendMessage("**Incomplete Command!**\n**Correct Usage**: `-profile` `@<user>`").queue();
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

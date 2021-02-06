package com.cadenkoehl.cadenbot.staff.commands.suggest;

import com.cadenkoehl.cadenbot.CadenBot;
import com.cadenkoehl.cadenbot.util.Constants;
import com.cadenkoehl.cadenbot.util.EmbedColor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class SuggestionManager {

    public Suggestion getSuggestion(Guild guild, int number) {
        if(number == 0) {
            System.out.println("Suggestion number is 0: Returning null");
            return null;
        }
        File dir = new File(CadenBot.dataDirectory + "suggestions/" + guild.getId());
        if(dir.mkdirs()) {
            System.out.println(dir.getPath() + " was created");
        }
        String content = null;
        String memberId = null;
        String messageURL = null;
        File file = new File(dir, number + ".txt");
        try {
            Scanner scan = new Scanner(file);
            if(scan.hasNextLine()) {
                content = scan.nextLine();
            }
            if(scan.hasNextLine()) {
                memberId = scan.nextLine();
            }
            if(scan.hasNextLine()) {
                messageURL = scan.nextLine();
            }
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage() + ": Returning null");
            return null;
        }
        String finalContent = content;
        String finalMemberId = memberId;
        String finalMessageURL = messageURL;
        return new Suggestion() {
            @Override
            public String getContent() {
                return finalContent;
            }

            @Override
            public Member getMember() {
                Guild guildById = CadenBot.jda.getGuildById(guild.getId());
                if(guildById == null) {
                    return null;
                }
                return guildById.getMemberById(finalMemberId);
            }

            @Override
            public String getMessageURL() {
                return finalMessageURL;
            }

            @Override
            public int getNumber() {
                return number;
            }
        };
    }

    public void addSuggestion(Member member, String content, TextChannel channel) {
        Guild guild = member.getGuild();
        TextChannel sugChannel = getSuggestionChannel(guild);
        String prefix = Constants.getPrefix(guild.getId());
        if(sugChannel == null) {
            channel.sendMessage(":x: A suggestion channel has not been set! (type `" + prefix + "help` `suggestions`)").queue();
            return;
        }
        String guildId = guild.getId();
        File dir = new File(CadenBot.dataDirectory + "suggestions/" + guildId);
        if(dir.mkdirs()) {
            System.out.println(dir.getPath() + " was created");
        }
        String[] paths = dir.list();
        int number;
        if(paths.length == 0) {
            number = 0;
        }
        else {
            String path = paths[0].replace(".txt", "");
            number = Integer.parseInt(path);
        }
        number++;
        File file = new File(dir, number + ".txt");
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(EmbedColor.DARK_BLUE);
        embed.setAuthor(member.getEffectiveName() + " made a suggestion!", null, member.getUser().getEffectiveAvatarUrl());
        embed.setTitle("Suggestion #" + number);
        embed.setDescription(content);
        sugChannel.sendMessage(embed.build()).queue(message -> {
            try {
                message.addReaction("\uD83D\uDC4D").queue();
                message.addReaction("\uD83D\uDC4E").queue();
                FileWriter write = new FileWriter(file);
                write.write(content);
                write.append("\n").append(member.getId());
                write.append("\n").append(message.getJumpUrl());
                write.close();
            }
            catch (IOException e) {
                channel.sendMessage(Constants.ERROR_MESSAGE).queue();
                e.printStackTrace();
            }
        });
        channel.sendMessage(":white_check_mark: **Success**! Your suggestion was added to " + sugChannel.getAsMention()).queue();
    }

    public TextChannel getSuggestionChannel(Guild guild) {
        String guildId = guild.getId();
        File dir = new File(CadenBot.dataDirectory + "suggestions/channel");
        if(dir.mkdirs()) {
            System.out.println(dir.getPath() + " was created");
        }
        File file = new File(dir, guildId + ".txt");
        String channelId;
        try {
            Scanner scan = new Scanner(file);
            channelId = scan.nextLine();
        }
        catch (FileNotFoundException e) {
            System.out.println(e.getMessage() + ": Returning null");
            return null;
        }
        TextChannel textChannel = guild.getTextChannelById(channelId);
        if(textChannel == null) {
            System.out.println("Returning null because channel of id " + channelId + " does not exist!");
            return null;
        }
        return textChannel;
    }

    public void setSuggestionChannel(TextChannel channel) {
        String guildId = channel.getGuild().getId();
        String channelId = channel.getId();
        File dir = new File(CadenBot.dataDirectory + "suggestions/channel");
        if(dir.mkdirs()) {
            System.out.println(dir.getPath() + " was created");
        }
        File file = new File(dir, guildId + ".txt");
        try {
            FileWriter write = new FileWriter(file);
            write.write(channelId);
            write.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
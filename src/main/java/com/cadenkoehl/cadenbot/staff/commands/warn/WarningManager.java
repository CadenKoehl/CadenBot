package com.cadenkoehl.cadenbot.staff.commands.warn;

import com.cadenkoehl.cadenbot.CadenBot;
import com.cadenkoehl.cadenbot.staff.commands.LogChannel;
import com.cadenkoehl.cadenbot.staff.commands.mute.MuteManager;
import com.cadenkoehl.cadenbot.util.EmbedColor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class WarningManager {
    public void warn(Member member, TextChannel channel, String reason, Member moderator) {
        try {
            Guild guild = member.getGuild();
            String guildId = guild.getId();
            String memberId = member.getId();
            File dir = new File(CadenBot.dataDirectory + "warns/members/");
            if(dir.mkdirs()) {
                System.out.println("Successfully created directory " + dir.getPath());
            }
            File file = new File(dir, memberId + " " + guildId + ".txt");
            if(!file.exists()) {
                FileWriter write = new FileWriter(file);
                write.write("1");
                write.close();
                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(EmbedColor.RED);
                embed.setAuthor(member.getUser().getAsTag() + " was warned!", null, member.getUser().getEffectiveAvatarUrl());
                embed.setFooter("Moderator: " + moderator.getUser().getAsTag(), moderator.getUser().getEffectiveAvatarUrl());
                embed.setDescription("Reason: " + reason);
                channel.sendMessage(embed.build()).queue();
                String channelId;
                channelId = LogChannel.getLogChannelId(member.getGuild());
                if (channelId != null) {
                    TextChannel logChannel = CadenBot.jda.getTextChannelById(channelId);
                    if(logChannel == null) {
                        File logFile = new File(CadenBot.dataDirectory + "logging/" + channelId + ".txt");
                        if(logFile.exists()) {
                            logFile.delete();
                        }
                        return;
                    }
                    logChannel.sendMessage(embed.build()).queue();
                }
                return;
            }
            int maxWarnVal = getMaxWarnVal(guild);
            Scanner scan = new Scanner(file);
            if(!scan.hasNextLine()) {
                FileWriter write = new FileWriter(file);
                write.write("1");
                write.close();
                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(EmbedColor.RED);
                embed.setAuthor(member.getUser().getAsTag() + " was warned!", null, member.getUser().getEffectiveAvatarUrl());
                embed.setFooter("Moderator: " + moderator.getUser().getAsTag(), moderator.getUser().getEffectiveAvatarUrl());
                embed.setDescription("Reason: " + reason);
                channel.sendMessage(embed.build()).queue();
                String channelId;
                channelId = LogChannel.getLogChannelId(member.getGuild());
                if (channelId != null) {
                    TextChannel logChannel = CadenBot.jda.getTextChannelById(channelId);
                    if(logChannel == null) {
                        File logFile = new File(CadenBot.dataDirectory + "logging/" + channelId + ".txt");
                        if(logFile.exists()) {
                            logFile.delete();
                        }
                        return;
                    }
                    logChannel.sendMessage(embed.build()).queue();
                }
                return;
            }
            String oldValRaw = scan.nextLine();
            int val = Integer.parseInt(oldValRaw);
            val++;
            if(val >= maxWarnVal) {
                if(file.delete()) {
                    System.out.println(file.getPath() + " was deleted");
                }
                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(EmbedColor.RED);
                embed.setAuthor(member.getUser().getAsTag() + " was warned!", null, member.getUser().getEffectiveAvatarUrl());
                embed.setFooter("Moderator: " + moderator.getUser().getAsTag(), moderator.getUser().getEffectiveAvatarUrl());
                embed.setDescription("Reason: " + reason);
                channel.sendMessage(embed.build()).queue();
                String channelId;
                channelId = LogChannel.getLogChannelId(member.getGuild());
                if (channelId != null) {
                    TextChannel logChannel = CadenBot.jda.getTextChannelById(channelId);
                    if(logChannel == null) {
                        File logFile = new File(CadenBot.dataDirectory + "logging/" + channelId + ".txt");
                        if(logFile.exists()) {
                            logFile.delete();
                        }
                        return;
                    }
                    logChannel.sendMessage(embed.build()).queue();
                }
                MuteManager.tempMute(member, channel, "Too Many Infractions", 5, TimeUnit.MINUTES, guild.getSelfMember());
                return;
            }
            FileWriter write = new FileWriter(file);
            write.write(String.valueOf(val));
            write.close();
            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(EmbedColor.RED);
            embed.setAuthor(member.getUser().getAsTag() + " was warned!", null, member.getUser().getEffectiveAvatarUrl());
            embed.setDescription("Reason: " + reason);
            embed.setFooter("Moderator: " + moderator.getUser().getAsTag(), moderator.getUser().getEffectiveAvatarUrl());
            channel.sendMessage(embed.build()).queue();
            String channelId;
            channelId = LogChannel.getLogChannelId(member.getGuild());
            if (channelId != null) {
                TextChannel logChannel = CadenBot.jda.getTextChannelById(channelId);
                if(logChannel == null) {
                    File logFile = new File(CadenBot.dataDirectory + "logging/" + channelId + ".txt");
                    if(logFile.exists()) {
                        logFile.delete();
                    }
                    return;
                }
                logChannel.sendMessage(embed.build()).queue();
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public int getMaxWarnVal(Guild guild) {
        return 3;
    }
}

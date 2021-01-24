package com.cadenkoehl.cadenbot.levels;

import com.cadenkoehl.cadenbot.CadenBot;
import com.cadenkoehl.cadenbot.Constants;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.*;
import java.util.Scanner;

public class Levels extends ListenerAdapter {
    Guild guild;
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        guild = event.getGuild();
        if(!event.getMember().getUser().isBot()) {
            String currentChannelId = event.getChannel().getId();
            File ignoredChannel = new File(CadenBot.dataDirectory + "levels/ignored_channels/" + currentChannelId + ".txt");
            if(!ignoredChannel.exists()) {
                if(Xp.getXp(event.getMember().getId(), guild.getId()) == 100) {
                    Guild guild = event.getGuild();
                    String guildId = guild.getId();
                    Member member = event.getMember();
                    String memberId = member.getId();
                    String lvlRaw;

                    File file = new File(CadenBot.dataDirectory + "levels/" + guildId + " " + memberId + ".txt");
                    Scanner scan;
                    FileWriter write;
                    try {
                        if(!file.exists()) {
                            file.createNewFile();
                            write = new FileWriter(file);
                            write.write("0");
                            write.close();
                        }
                        if(file.exists()) {
                            scan = new Scanner(file);
                            lvlRaw = scan.nextLine();
                            int lvl = Integer.parseInt(lvlRaw);
                            lvl++;
                            FileWriter lvlWriter = new FileWriter(file);
                            lvlWriter.write(String.valueOf(lvl));
                            lvlWriter.close();
                            File customChannel = new File(CadenBot.dataDirectory + "levels/channel/" + guildId + ".txt");
                            if(customChannel.exists()) {
                                Scanner channelScanner = new Scanner(customChannel);
                                String channelId = channelScanner.nextLine();
                                event.getGuild().getTextChannelById(channelId).sendMessage("Heyo! " + member.getAsMention() + " just reached **level " + lvl + "**! So yeah **GG**! okay bye now!").queue();
                            }
                            if(!customChannel.exists()) {
                                event.getChannel().sendMessage("Heyo! " + member.getAsMention() + " just reached **level " + lvl + "**! So yeah **gg**! okay bye now!").queue();
                            }
                        }
                    }
                    catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    public static int getLevel(String userId, String guildId) {
        File file = new File(CadenBot.dataDirectory + "levels/" + guildId + " " + userId + ".txt");
        try {
            Scanner scan = new Scanner(file);
            String lvlRaw = scan.nextLine();
            return Integer.parseInt(lvlRaw);
        } catch (FileNotFoundException exception) {
            return 0;
        }
    }
}
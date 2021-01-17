package com.cadenkoehl.cadenbot.levels;

import com.cadenkoehl.cadenbot.CadenBot;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.*;
import java.util.Scanner;

public class Levels extends ListenerAdapter {
    static Guild guild;
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        guild = event.getGuild();
        if(!event.getMember().getUser().isBot()) {
            String currentChannelId = event.getChannel().getId();
            int rng = (int) Math.round(Math.random() * 500);
            System.out.println("Level-up RNG: " + rng);
            File ignoredChannel = new File(CadenBot.dataDirectory + "levels/ignored_channels/" + currentChannelId + ".txt");
            if(!ignoredChannel.exists()) {
                if(rng < 6) {
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
                            //System.out.println(lvlRaw);
                            int lvl = Integer.parseInt(lvlRaw);
                            lvl++;
                            //System.out.println(lvl);
                            FileWriter lvlWriter = new FileWriter(file);
                            lvlWriter.write(String.valueOf(lvl));
                            lvlWriter.close();
                            File customChannel = new File(CadenBot.dataDirectory + "levels/channel/" + guildId + ".txt");
                            if(customChannel.exists()) {
                                Scanner channelScanner = new Scanner(customChannel);
                                String channelId = channelScanner.nextLine();
                                event.getGuild().getTextChannelById(channelId).sendMessage("Heyo! " + member.getAsMention() + " just reached **level " + lvl + "**! So yeah **gg**! okay bye now!").queue();
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
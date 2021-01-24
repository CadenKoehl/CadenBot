package com.cadenkoehl.cadenbot.levels;

import com.cadenkoehl.cadenbot.CadenBot;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Xp extends ListenerAdapter {
    Guild guild;
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        guild = event.getGuild();
        if(!event.getMember().getUser().isBot()) {
            String currentChannelId = event.getChannel().getId();
            File ignoredChannel = new File(CadenBot.dataDirectory + "levels/ignored_channels/" + currentChannelId + ".txt");
            if(!ignoredChannel.exists()) {
                Guild guild = event.getGuild();
                String guildId = guild.getId();
                Member member = event.getMember();
                String memberId = member.getId();
                String xpRaw;

                File file = new File(CadenBot.dataDirectory + "levels/xp/" + guildId + " " + memberId + ".txt");
                Scanner scan;
                FileWriter write;
                try {
                    if(!file.exists()) {
                        file.createNewFile();
                        write = new FileWriter(file);
                        write.write("290");
                        write.close();
                    }
                    if(file.exists()) {
                        scan = new Scanner(file);
                        xpRaw = scan.nextLine();
                        int xp = Integer.parseInt(xpRaw);
                        xp++;
                        FileWriter xpWriter = new FileWriter(file);
                        if(xp == 301) {
                            xpWriter.write("0");
                            xpWriter.close();
                        }
                        if(xp != 301) {
                            xpWriter.write(String.valueOf(xp));
                            xpWriter.close();
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

    public static int getXp(String userId, String guildId) {
        File file = new File(CadenBot.dataDirectory + "levels/xp/" + guildId + " " + userId + ".txt");
        try {
            Scanner scan = new Scanner(file);
            String xpRaw = scan.nextLine();
            return Integer.parseInt(xpRaw);
        } catch (FileNotFoundException exception) {
            return 0;
        }
    }
}

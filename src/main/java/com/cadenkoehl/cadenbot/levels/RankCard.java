package com.cadenkoehl.cadenbot.levels;

import com.cadenkoehl.cadenbot.CadenBot;
import com.cadenkoehl.cadenbot.Constants;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class RankCard extends ListenerAdapter {
    private static String id;
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        id = event.getGuild().getId();
        int users = event.getGuild().getMemberCount();
        int start = 0;
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        if(args[0].equalsIgnoreCase(Constants.getPrefix(id) + "rank") || args[0].equalsIgnoreCase(Constants.getPrefix(id) + "rankcard")) {
            if(!event.getMember().getUser().isBot()) {
                try {
                    Member member = event.getMessage().getMentionedMembers().get(0);
                    if(!member.getUser().isBot()) {
                        Map<String, Integer> unsortedMap = new HashMap<>();
                        List<Member> members = event.getGuild().getMembers();

                        for (int i = 0; i < members.size(); i++) {
                            int lvl = Levels.getLevel(members.get(i).getUser().getId(), event.getGuild().getId());
                            if(!members.get(i).getUser().isBot()) {
                                unsortedMap.put(members.get(i).getUser().getName(), lvl);
                            }
                        }

                        Map<String, Integer> sortedMap = sortByValue(unsortedMap);
                        int finish = start + users;

                        if (start != 0) {
                            finish++;
                        }
                        if (start != 0) {
                            start++;
                        }

                        String authorName = member.getUser().getName();

                        int counter = 0;
                        int rank = sortedMap.size();

                        for (Map.Entry<String,Integer> entry : sortedMap.entrySet()) {
                            counter++;
                            if (entry.getKey() == authorName) { rank = counter; }
                        }


                        int level = Levels.getLevel(member.getId(), id);
                        EmbedBuilder embed = new EmbedBuilder();
                        embed.setAuthor(member.getUser().getName() + "'s RankCard!", null, member.getUser().getEffectiveAvatarUrl());
                        embed.setTitle("**Level:** *" + level + "*\n**Rank:** *" + rank + "*\n--------");
                        embed.setColor((int) Math.round(Math.random() * 999999));
                        embed.setFooter(event.getGuild().getName(), event.getGuild().getIconUrl());
                        event.getChannel().sendMessage(embed.build()).queue();
                    }
                    if(member.getUser().isBot()) {
                        event.getChannel().sendMessage("Sorry, **" + member.getUser().getName() + "** is a bot, and isn't invited to the super cool rank party").queue();
                    }
                }
                catch (IndexOutOfBoundsException ex) {
                    Member member = event.getMember();
                    Map<String, Integer> unsortedMap = new HashMap<>();
                    List<Member> members = event.getGuild().getMembers();

                    for (int i = 0; i < members.size(); i++) {
                        int lvl = Levels.getLevel(members.get(i).getUser().getId(), event.getGuild().getId());
                        if(!members.get(i).getUser().isBot()) {
                            unsortedMap.put(members.get(i).getUser().getName(), lvl);
                        }
                    }

                    Map<String, Integer> sortedMap = sortByValue(unsortedMap);
                    int finish = start + users;

                    if (start != 0) {
                        finish++;
                    }
                    if (start != 0) {
                        start++;
                    }

                    String authorName = event.getAuthor().getName();

                    int counter = 0;
                    int rank = sortedMap.size();

                    for (Map.Entry<String,Integer> entry : sortedMap.entrySet()) {
                        counter++;
                        if (entry.getKey() == authorName) { rank = counter; }
                    }

                    int level = Levels.getLevel(member.getId(), id);
                    double xp = level * 10.5;
                    EmbedBuilder embed = new EmbedBuilder();
                    embed.setColor((int) Math.round(Math.random() * 999999));
                    embed.setAuthor(member.getUser().getName() + "'s RankCard!", null, member.getUser().getEffectiveAvatarUrl());
                    embed.setFooter(event.getGuild().getName(), event.getGuild().getIconUrl());
                    embed.setTitle("**Level:** *" + level + "*\n**Rank:** *" + rank + "*\n--------");
                    event.getChannel().sendMessage(embed.build()).queue();
                }
            }
        }
    }
    private Map<String, Integer> sortByValue(Map<String, Integer> unsortMap) {
        // Convert Map to List of Map
        List<Map.Entry<String, Integer>> list = new LinkedList<>(unsortMap.entrySet());

        // Sort list with Collections.sort(), provide a custom Comparator
        // Try switch the o1 o2 position for a different order
        list.sort(Map.Entry.<String, Integer>comparingByValue().reversed());

        // Loop the sorted list and put it into a new insertion order Map LinkedHashMap
        Map<String, Integer> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }
}

package com.cadenkoehl.cadenbot.levels;

import com.cadenkoehl.cadenbot.util.Constants;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.*;

public class Leaderboard extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        int usersPerPage = 20;
        int start = 0;
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        String prefix = Constants.getPrefix(event.getGuild().getId());
        if(args[0].equalsIgnoreCase(prefix + "leaderboard") || args[0].equalsIgnoreCase(prefix + "lb")) {
            if(!event.getAuthor().isBot()) {
                Map<String, Integer> unsortedMap = new HashMap<>();
                List<Member> members = event.getGuild().getMembers();

                for (int i = 0; i < members.size(); i++) {
                    int lvl = Levels.getLevel(members.get(i).getUser().getId(), event.getGuild().getId());
                    if(!members.get(i).getUser().isBot()) {
                        unsortedMap.put(members.get(i).getAsMention(), lvl);
                    }
                }
                Map<String, Integer> sortedMap = sortByValue(unsortedMap);
                EmbedBuilder embed = new EmbedBuilder();
                embed.setAuthor(event.getGuild().getName() + "'s Leaderboard!", null, event.getGuild().getIconUrl());
                String desc = "";
                int finish = start + usersPerPage;

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
                    if (counter >= start && counter < finish) {
                        String name = entry.getKey();
                        long lvl = entry.getValue();
                        desc += (counter + 1) + ". " + name + " ✨ " + lvl + "\n";
                    }
                    counter++;
                    if (entry.getKey() == authorName) { rank = counter; }
                }

                embed.setDescription(desc);
                embed.setColor((int) Math.round(Math.random() * 999999));
                embed.setFooter("Your Leaderboard Rank: " + rank, event.getAuthor().getEffectiveAvatarUrl());
                event.getChannel().sendMessage(embed.build()).queue();
            }
        }
    }

    private Map<String, Integer> sortByValue(Map<String, Integer> unsortMap) {
        List<Map.Entry<String, Integer>> list = new LinkedList<>(unsortMap.entrySet());
        list.sort(Map.Entry.<String, Integer>comparingByValue().reversed());
        Map<String, Integer> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }
}

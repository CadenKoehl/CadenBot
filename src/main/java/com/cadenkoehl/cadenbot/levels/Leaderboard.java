package com.cadenkoehl.cadenbot.levels;

import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandCategory;
import com.cadenkoehl.cadenbot.util.Constants;
import com.cadenkoehl.cadenbot.util.exceptions.IncorrectUsageException;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.*;

public class Leaderboard extends Command {


    private Map<String, Integer> sortByValue(Map<String, Integer> unsortMap) {
        List<Map.Entry<String, Integer>> list = new LinkedList<>(unsortMap.entrySet());
        list.sort(Map.Entry.<String, Integer>comparingByValue().reversed());
        Map<String, Integer> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

    @Override
    public void execute(GuildMessageReceivedEvent event) throws IncorrectUsageException {
        int start = 0;
        int usersPerPage = 20;
        Map<String, Integer> unsortedMap = new HashMap<>();
        List<Member> members = event.getGuild().getMembers();

        for (Member member : members) {
            int lvl = Levels.getLevel(member.getUser().getId(), event.getGuild().getId());
            if (!member.getUser().isBot()) {
                unsortedMap.put(member.getAsMention(), lvl);
            }
        }
        Map<String, Integer> sortedMap = sortByValue(unsortedMap);
        EmbedBuilder embed = new EmbedBuilder();
        embed.setAuthor(event.getGuild().getName() + "'s Leaderboard!", null, event.getGuild().getIconUrl());
        String desc = "";
        int finish = start + usersPerPage;

        int counter = 0;
        int rank = sortedMap.size();
        int authorRank = 0;
        String name = null;
        for (Map.Entry<String,Integer> entry : sortedMap.entrySet()) {
            if (counter >= start && counter < finish) {
                name = entry.getKey();
                long lvl = entry.getValue();
                desc += (counter + 1) + ". " + name + " ✨ " + lvl + "\n";
            }
            counter++;
            if(name == null) continue;
            if (entry.getKey().equals(name)) {
                rank = counter;
            }
            if(entry.getKey().equalsIgnoreCase(event.getMember().getAsMention())) authorRank = rank;
        }

        embed.setDescription(desc);
        embed.setColor((int) Math.round(Math.random() * 999999));
        embed.setFooter("Your Leaderboard Rank: " + authorRank, event.getAuthor().getEffectiveAvatarUrl());
        event.getChannel().sendMessage(embed.build()).queue();
    }

    @Override
    public String getName() {
        return "leaderboard";
    }

    @Override
    public String getDescription() {
        return "View this server's leveling leaderboard!";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.LEVELS;
    }

    @Override
    public Permission getRequiredPermission() {
        return Permission.MANAGE_SERVER;
    }

    @Override
    public String getUsage(String prefix) {
        return "leaderboard`";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"lb"};
    }
}

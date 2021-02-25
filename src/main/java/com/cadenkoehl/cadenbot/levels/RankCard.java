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

public class RankCard extends Command {

    @Override
    public void execute(GuildMessageReceivedEvent event) throws IncorrectUsageException {
        String guildId = event.getGuild().getId();
        int users = 20;
        int start = 0;
        try {
            Member member = event.getMessage().getMentionedMembers().get(0);
            if (!member.getUser().isBot()) {
                if (Levels.getLevel(member.getId(), guildId) != 0) {
                    Map<String, Integer> unsortedMap = new HashMap<>();
                    List<Member> members = event.getGuild().getMembers();

                    for (Member value : members) {
                        int lvl = Levels.getLevel(value.getUser().getId(), event.getGuild().getId());
                        if (!value.getUser().isBot()) {
                            unsortedMap.put(value.getUser().getName(), lvl);
                        }
                    }

                    Map<String, Integer> sortedMap = sortByValue(unsortedMap);
                    int finish = start + users;

                    String authorName = member.getUser().getName();

                    int counter = 0;
                    int rank = sortedMap.size();

                    for (Map.Entry<String, Integer> entry : sortedMap.entrySet()) {
                        counter++;
                        if (entry.getKey().equals(authorName)) {
                            rank = counter;
                        }
                    }


                    int level = Levels.getLevel(member.getId(), guildId);
                    double xp = Xp.getXp(member.getId(), event.getGuild().getId()) * 2.5;
                    EmbedBuilder embed = new EmbedBuilder();
                    embed.setAuthor(member.getUser().getName() + "'s RankCard!", null, member.getUser().getEffectiveAvatarUrl());
                    embed.setTitle("**Level:** *" + level + "*\n**Rank:** *" + rank + "*\n**XP:** *" + xp + "*\n--------");
                    embed.setColor((int) Math.round(Math.random() * 999999));
                    embed.setFooter(event.getGuild().getName(), event.getGuild().getIconUrl());
                    event.getChannel().sendMessage(embed.build()).queue();
                }
                if (Levels.getLevel(member.getId(), guildId) == 0) {
                    event.getChannel().sendMessage(":x: User is not ranked yet!").queue();
                }
            }
            if (member.getUser().isBot()) {
                event.getChannel().sendMessage("Sorry, **" + member.getUser().getName() + "** is a bot, and isn't invited to the super cool rank party").queue();
            }
        } catch (IndexOutOfBoundsException ex) {
            Member member = event.getMember();
            if (Levels.getLevel(member.getId(), guildId) != 0) {
                Map<String, Integer> unsortedMap = new HashMap<>();
                List<Member> members = event.getGuild().getMembers();

                for (int i = 0; i < members.size(); i++) {
                    int lvl = Levels.getLevel(members.get(i).getUser().getId(), event.getGuild().getId());
                    if (!members.get(i).getUser().isBot()) {
                        unsortedMap.put(members.get(i).getUser().getName(), lvl);
                    }
                }

                Map<String, Integer> sortedMap = sortByValue(unsortedMap);
                int finish = start + users;

                String authorName = event.getAuthor().getName();

                int counter = 0;
                int rank = sortedMap.size();

                for (Map.Entry<String, Integer> entry : sortedMap.entrySet()) {
                    counter++;
                    if (entry.getKey() == authorName) {
                        rank = counter;
                    }
                }

                int level = Levels.getLevel(member.getId(), guildId);
                double xp = Xp.getXp(member.getId(), event.getGuild().getId()) * 2.5;
                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor((int) Math.round(Math.random() * 999999));
                embed.setAuthor(member.getUser().getName() + "'s RankCard!", null, member.getUser().getEffectiveAvatarUrl());
                embed.setFooter(event.getGuild().getName(), event.getGuild().getIconUrl());
                embed.setTitle("**Level:** *" + level + "*\n**Rank:** *" + rank + "*\n**XP:** *" + xp + "*\n--------");
                event.getChannel().sendMessage(embed.build()).queue();
            }
            if (Levels.getLevel(member.getId(), guildId) == 0) {
                event.getChannel().sendMessage(":x: You are not ranked yet!").queue();
            }
        }
    }

    @Override
    public String getName() {
        return "rank";
    }

    @Override
    public String getDescription() {
        return "See your RankCard!";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.LEVELS;
    }

    @Override
    public Permission getRequiredPermission() {
        return null;
    }

    @Override
    public String getUsage(String prefix) {
        return "rank` *optional*: `<@user>`";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"rankcard"};
    }

    private Map<String, Integer> sortByValue(Map<String, Integer> unsortMap) {
        // Convert Map to List of Map
        List<Map.Entry<String, Integer>> list = new LinkedList<>(unsortMap.entrySet());

        // Sort list with Collections.sort(), provide a custom Comparator
        // Try switch the o1 o2 position for a different order
        list.sort(Map.Entry.<String, Integer>comparingByValue().reversed());

        // Repeat the sorted list and put it into a new insertion order Map LinkedHashMap
        Map<String, Integer> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }
}

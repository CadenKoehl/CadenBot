package com.cadenkoehl.cadenbot.automod.commands;

import com.cadenkoehl.cadenbot.CadenBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.HierarchyException;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Tempban extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        if(args[0].equalsIgnoreCase(CadenBot.prefix + "tempban")) {
            if(!event.getAuthor().isBot()) {
                if(event.getMember().hasPermission(Permission.BAN_MEMBERS)) {
                    try {
                        Member member = event.getMessage().getMentionedMembers().get(0);
                        String timeUnitInput = Arrays.stream(args).skip(3).collect(Collectors.joining(" "));
                        TimeUnit timeUnit = null;
                        String length = Arrays.stream(args).skip(2).collect(Collectors.joining(" "));
                        String reason = Arrays.stream(args).skip(4).collect(Collectors.joining(" "));
                        switch (timeUnitInput) {
                            case "days":
                                timeUnit = TimeUnit.DAYS;
                                break;
                            case "hours":
                                timeUnit = TimeUnit.HOURS;
                            case "minutes":
                                timeUnit = TimeUnit.MINUTES;
                            case "min":
                                timeUnit = TimeUnit.MINUTES;
                        }
                        EmbedBuilder embed = new EmbedBuilder();
                        embed.setAuthor(member.getUser().getAsTag() + " was banned for " + timeUnitInput + "", null, member.getUser().getEffectiveAvatarUrl());
                        embed.setFooter("Moderator: " + event.getAuthor().getAsTag() + ", reason: \"" + reason + "\"");
                        event.getChannel().sendMessage(embed.build()).queue();
                        member.ban(0, reason).queueAfter(1, TimeUnit.SECONDS);
                        event.getGuild().unban(member.getUser()).queueAfter(Integer.parseInt(length), timeUnit);
                    }
                    catch (IndexOutOfBoundsException ex) {
                        event.getChannel().sendMessage("Hold on, who are we tempbanning?").queue();
                    }
                    catch (HierarchyException ex) {
                        event.getChannel().sendMessage("I do not have permission to ban that person!").queue();
                    }
                }
                if(!event.getMember().hasPermission(Permission.BAN_MEMBERS)) {
                    event.getChannel().sendMessage("You can't use that!").queue();
                }
            }
        }
    }
}

package com.cadenkoehl.cadenbot.staff.commands.suggest;

import com.cadenkoehl.cadenbot.util.Constants;
import com.cadenkoehl.cadenbot.util.EmbedColor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.stream.Collectors;

public class SuggestApprove extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        String guildId = event.getGuild().getId();
        String prefix = Constants.getPrefix(guildId);
        if(args[0].equalsIgnoreCase(prefix + "approve")) {
            if(event.isWebhookMessage()) {
                return;
            }
            if(event.getAuthor().isBot()) {
                return;
            }
            Member member = event.getMember();
            if(!member.hasPermission(Permission.ADMINISTRATOR)) {
                event.getChannel().sendMessage(":x: You must have the `administrator` permission to use this command!").queue();
                return;
            }
            SuggestionManager manager = new SuggestionManager();
            if(args.length == 1) {
                event.getChannel().sendMessage(":x: Please specify the suggestion number you want to approve!").queue();
                return;
            }
            int number;
            try {
                number = Integer.parseInt(args[1]);
            }
            catch (NumberFormatException ex) {
                event.getChannel().sendMessage(":x: Invalid suggestion number!").queue();
                return;
            }
            Suggestion sug = manager.getSuggestion(event.getGuild(), number);
            if(sug == null) {
                event.getChannel().sendMessage(":x: Suggestion #" + number + " does not exist!").queue();
                return;
            }
            TextChannel sugChannel = manager.getSuggestionChannel(event.getGuild());
            if(sugChannel == null) {
                event.getChannel().sendMessage(":x: A suggestion channel has not been set! (type `" + prefix + "help` `suggestions`)").queue();
                return;
            }
            String reason = Arrays.stream(args).skip(2).collect(Collectors.joining(" "));
            EmbedBuilder embed = new EmbedBuilder();

            embed.setAuthor(sug.getMember().getEffectiveName() + "'s Suggestion was Approved!", null, sug.getMember().getUser().getEffectiveAvatarUrl());
            embed.setTitle("Suggestion #" + sug.getNumber());
            embed.addField("", "[Jump to Suggestion](" + sug.getMessageURL() + ")", false);
            if(reason.isEmpty()) {
                reason = "Unspecified";
            }

            embed.setDescription("Reason: " + reason);

            embed.setColor(EmbedColor.GREEN);
            sugChannel.sendMessage(embed.build()).queue();
            event.getChannel().sendMessage(":white_check_mark: **Success**! Suggestion #" + number + " from **" + sug.getMember().getEffectiveName() + "** was approved!").queue();
        }
    }
}

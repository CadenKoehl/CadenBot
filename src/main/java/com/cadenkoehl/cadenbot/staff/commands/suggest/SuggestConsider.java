package com.cadenkoehl.cadenbot.staff.commands.suggest;

import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandCategory;
import com.cadenkoehl.cadenbot.util.Constants;
import com.cadenkoehl.cadenbot.util.EmbedColor;
import com.cadenkoehl.cadenbot.util.exceptions.IncorrectUsageException;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.stream.Collectors;

public class SuggestConsider extends Command {
    @Override
    public void execute(GuildMessageReceivedEvent event) throws IncorrectUsageException {
        String[] args = this.getArgs(event);
        String prefix = this.getPrefix(event);

        SuggestionManager manager = new SuggestionManager();
        if(args.length == 1) {
            event.getChannel().sendMessage(":x: Please specify the suggestion number you want to consider!").queue();
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

        embed.setAuthor(sug.getMember().getEffectiveName() + "'s suggestion was considered!", null, sug.getMember().getUser().getEffectiveAvatarUrl());
        embed.setTitle("Suggestion #" + sug.getNumber());
        embed.addField("", "[Jump to Suggestion](" + sug.getMessageURL() + ")", false);
        if(reason.isEmpty()) {
            reason = "Unspecified";
        }

        embed.setDescription("Reason: " + reason);

        embed.setColor(EmbedColor.YELLOW);
        sugChannel.sendMessage(embed.build()).queue();
        event.getChannel().sendMessage(":white_check_mark: **Success**! Suggestion #" + number + " from **" + sug.getMember().getEffectiveName() + "** was considered!").queue();
    }

    @Override
    public String getName() {
        return "consider";
    }

    @Override
    public String getDescription() {
        return "Consider a suggestion!";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.SUGGESTIONS;
    }

    @Override
    public Permission getRequiredPermission() {
        return Permission.ADMINISTRATOR;
    }

    @Override
    public String getUsage(String prefix) {
        return "consider` `[suggestion]`";
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }
}

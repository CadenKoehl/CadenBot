package com.cadenkoehl.cadenbot.commands;

import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandCategory;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandEvent;
import com.cadenkoehl.cadenbot.util.exceptions.IncorrectUsageException;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class EmbedCmd extends Command {
    @Override
    public void execute(CommandEvent event) throws IncorrectUsageException {
        String[] args = event.getArgs();
        String[] embedElement = Arrays.stream(args).skip(1).collect(Collectors.joining(" ")).split("\\|");
        EmbedBuilder embed = new EmbedBuilder();

        if(args.length == 1) throw new IncorrectUsageException(this, event);

        if(embedElement.length >= 1) {
            embed.setTitle(embedElement[0]);
        }

        if(embedElement.length >= 2) {
            embed.setDescription(embedElement[1]);
        }

        if(embedElement.length >= 3) {
            embed.addField("", embedElement[2], false);
        }

        if(embedElement.length >= 4) {
            embed.addField("", embedElement[3], false);
        }

        embed.setColor((int) Math.round(Math.random() * 999999));
        event.getChannel().sendMessage(embed.build()).queue();
        event.getMessage().delete().queueAfter(5, TimeUnit.SECONDS);
    }

    @Override
    public String getName() {
        return "embed";
    }

    @Override
    public String getDescription() {
        return "Easily create unlimited, customizable embeds on your server!";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.COMMAND;
    }

    @Override
    public Permission getRequiredPermission() {
        return Permission.MANAGE_CHANNEL;
    }

    @Override
    public String getUsage(String prefix) {
        return "embed` `Embed Title` | `Embed Description` | `Embed Field 1` | `Embed Field 2`\n(separate each element with \"|\")";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"eb"};
    }
}

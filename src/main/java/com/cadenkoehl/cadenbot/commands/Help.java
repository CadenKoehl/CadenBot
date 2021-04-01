package com.cadenkoehl.cadenbot.commands;

import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandCategory;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandEvent;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandHandler;
import com.cadenkoehl.cadenbot.commands.custom_commands.CustomCommand;
import com.cadenkoehl.cadenbot.util.EmbedColor;
import com.cadenkoehl.cadenbot.util.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Help extends Command {

    @Override
    public void execute(CommandEvent event) {

        String[] args = event.getArgs();
        String prefix = event.getPrefix();
        String helpSearch = Arrays.stream(args).skip(1).collect(Collectors.joining(" "));

        CommandCategory[] categories = CommandCategory.values();

        if(args.length == 1) {
            EmbedBuilder embed = new EmbedBuilder();
            embed.setAuthor("What do you need help with?", null, event.getAuthor().getEffectiveAvatarUrl());
            embed.setColor(EmbedColor.random());
            for(CommandCategory category : categories) {
                embed.appendDescription("\n" + prefix + "help " + category.getAliases()[0]);
            }
            embed.appendDescription("\n**If you have an issue, or you find a bug in CadenBot**, [join the support server!](https://discord.gg/9U4gnW2ueV)");
            event.getChannel().sendMessage(embed.build()).queue();
            return;
        }

        for(CommandCategory category : categories) {
            for(String alias : category.getAliases()) {
                if(helpSearch.equalsIgnoreCase(alias)) {
                    EmbedBuilder embed = new EmbedBuilder();
                    embed.setTitle(category.getDisplayName());
                    embed.setColor(EmbedColor.random());
                    for(Command cmd : CommandHandler.commands) {
                        if(cmd.getCategory() != category) continue;
                        if(cmd instanceof CustomCommand) {
                            if(!((CustomCommand) cmd).getGuild().getId().equals(event.getGuild().getId())) continue;
                        }
                        embed.appendDescription("\n`" + prefix + cmd.getName() + "` - " + cmd.getDescription());
                    }
                    embed.appendDescription("\n**For more info on a command**, type `" + prefix + "help` `[command name]`");
                    if(category.isStaff() && !event.getMember().hasPermission(Permission.BAN_MEMBERS)) {
                        event.getChannel().sendMessage(":x: You must have the **Ban Members** permission to view this category!").queue();
                        return;
                    }
                    event.getChannel().sendMessage(embed.build()).queue();
                    return;
                }
            }
            for(Command cmd : CommandHandler.commands) {
                if(helpSearch.equalsIgnoreCase(cmd.getName())) {
                    EmbedBuilder embed = new EmbedBuilder();
                    embed.setAuthor(Utils.capFirstLetter(cmd.getName()), null, event.getAuthor().getEffectiveAvatarUrl());
                    embed.setColor(EmbedColor.random());
                    embed.addField("Description:", cmd.getDescription(), false);
                    embed.addField("Usage:", "`" + prefix + cmd.getUsage(prefix), false);
                    embed.addField("Category:", category.getDisplayName(), false);
                    if(cmd.getRequiredPermission() != null) {
                        embed.addField("Required Permission:", cmd.getRequiredPermission().getName(), false);
                    }
                    if(!event.getMember().hasPermission(cmd.getRequiredPermission())) {
                        event.getChannel().sendMessage(":x: You must have the **" + cmd.getRequiredPermission().getName() + "** permission to use this command!").queue();
                        return;
                    }
                    if(cmd.getAliases().length != 0) {
                        embed.addField("Aliases:", Arrays.toString(cmd.getAliases()), false);
                    }
                    event.getChannel().sendMessage(embed.build()).queue();
                    return;
                }
            }
        }
        event.getChannel().sendMessage(":x: \"" + helpSearch + "\" is not a command or category! Type `" + prefix + "help` for help!").queue();
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "The command you just used!";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.COMMAND;
    }

    @Override
    public Permission getRequiredPermission() {
        return null;
    }

    @Override
    public String getUsage(String prefix) {
        return "help` `[command or category]`";
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }
}
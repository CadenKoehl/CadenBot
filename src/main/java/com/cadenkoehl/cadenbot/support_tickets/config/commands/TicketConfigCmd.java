package com.cadenkoehl.cadenbot.support_tickets.config.commands;

import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandCategory;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandEvent;
import com.cadenkoehl.cadenbot.support_tickets.config.TicketConfig;
import com.cadenkoehl.cadenbot.util.EmbedColor;
import com.cadenkoehl.cadenbot.util.exceptions.IncorrectUsageException;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;

import java.util.Map;

public class TicketConfigCmd extends Command {
    @Override
    public void execute(CommandEvent event) throws IncorrectUsageException {
        String[] args = event.getArgs();
        String prefix = event.getPrefix();

        Guild guild = event.getGuild();
        String guildName = guild.getName();

        Map<String, String> subCommands = Map.of(
                "setup", "Automatically setup support tickets with default settings!",
                "message", "Set the message that shows up in the :tickets: reaction embed"
        );

        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(EmbedColor.BLUE);

        if(args.length == 1) {
            embed.setTitle("Ticket Config");
            embed.setDescription("Here's some sub-commands to help you configure support tickets on your server!");

            for(Map.Entry<String, String> entry : subCommands.entrySet()) {
                embed.appendDescription("\n`" + prefix + this.getName() + "` `" + entry.getKey() + "` - " + entry.getValue());
            }

            event.getChannel().sendMessage(embed.build()).queue();
        }
        else {
            switch (args[1].toLowerCase()) {
                case "setup" -> {
                    TicketConfig config = new TicketConfig(guild);
                    config.createTicketChannel();
                    event.getChannel().sendMessage(":white_check_mark: **Success**! Setup complete!").queue();
                }
                case "message" -> {
                    TicketConfig config = TicketConfig.getInstance(guild);
                    if(config == null) {
                        event.error("I cannot edit your config because you haven't done `" + prefix + "setup` yet!");
                        return;
                    }
                    String message = event.skipArgs(2);
                    if(message.isEmpty()) {
                        event.error("Please provide a message!");
                        return;
                    }
                    config.getTicketMessage().editMessage(config.getTicketEmbedBuilder().setDescription(message).build()).queue();
                    event.success("Embed in " + config.getTicketChannel().getAsMention() + " was successfully edited!");
                }
            }
        }
    }

    @Override
    public String getName() {
        return "ticketconfig";
    }

    @Override
    public String getDescription() {
        return "Configure support ticket settings!";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.TICKETS;
    }

    @Override
    public Permission getRequiredPermission() {
        return Permission.MANAGE_SERVER;
    }

    @Override
    public String getUsage(String prefix) {
        return "ticketconfig`";
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }
}

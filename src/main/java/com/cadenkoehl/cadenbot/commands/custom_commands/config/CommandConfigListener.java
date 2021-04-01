package com.cadenkoehl.cadenbot.commands.custom_commands.config;

import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandHandler;
import com.cadenkoehl.cadenbot.commands.custom_commands.CustomCommand;
import com.cadenkoehl.cadenbot.commands.custom_commands.CustomCommandFactory;
import com.cadenkoehl.cadenbot.util.Constants;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class CommandConfigListener extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {

        if(event.isWebhookMessage()) return;
        if(event.getAuthor().isBot()) return;

        String[] args = event.getMessage().getContentRaw().split("\\s+");
        String prefix = Constants.getPrefix(event.getGuild());

        CustomCommandFactory factory = CustomCommandFactory.get(event.getChannel(), event.getMember());

        if(factory == null) return;
        if(Arrays.asList(new CreateCommand().getAliases()).contains(args[0].replace(Constants.getPrefix(event.getGuild()), ""))
                || new CreateCommand().getName().equalsIgnoreCase(args[0].replace(Constants.getPrefix(event.getGuild()), ""))) return;

        if(factory.getState() == CustomCommandFactory.State.NAME) {

            if(args.length != 1) {
                event.getChannel().sendMessage(":x: Command name must be one word!").queue();
                return;
            }
            if(args[0].toCharArray().length > 10) {
                event.getChannel().sendMessage(":x: Command name must not be over 10 characters!").queue();
                return;
            }
            if(Command.getByName(args[0], event.getGuild()) != null) {
                event.getChannel().sendMessage(":x: Command \"" + args[0] + "\" already exists!").queue();
                return;
            }
        }

        if(factory.getState() == CustomCommandFactory.State.DESC) {
            if(event.getMessage().getContentRaw().toCharArray().length > 30) {
                event.getChannel().sendMessage(":x: Command description must not be over 30 characters!").queue();
                return;
            }
        }

        CustomCommandFactory.State state = factory.getState() == CustomCommandFactory.State.NAME ? factory.nextState(args[0]) : factory.nextState(event.getMessage().getContentRaw());

        String message = "Custom Command was successfully created! Type `" + prefix + "help commands` to view it in help!";

        switch (state) {
            case NAME -> message = "Enter the name of this command below";
            case DESC -> message = "Enter the description of this command below (this will show up in `" + prefix + "help`)";
            case RESPONSE -> message = "Enter the response to this command below (what I will say in chat when the command is run)";
        }

        event.getChannel().sendMessage(message).queue();

        if(state == CustomCommandFactory.State.NONE) {
            CustomCommand command = factory.build();
            CommandHandler.commands.add(command);

            factory.delete();
        }
    }
}

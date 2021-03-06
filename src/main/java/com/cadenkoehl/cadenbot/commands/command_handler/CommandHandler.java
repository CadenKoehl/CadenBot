package com.cadenkoehl.cadenbot.commands.command_handler;

import com.cadenkoehl.cadenbot.commands.settings.GuildSettingsManager;
import com.cadenkoehl.cadenbot.util.Constants;
import com.cadenkoehl.cadenbot.util.ExceptionHandler;
import com.cadenkoehl.cadenbot.util.exceptions.IncorrectUsageException;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandHandler extends ListenerAdapter {

    public static List<Command> commands = new ArrayList<>();

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        try {
            String[] args = event.getMessage().getContentRaw().split("\\s+");
            String prefix = Constants.getPrefix(event.getGuild());

            GuildSettingsManager settings = new GuildSettingsManager(event.getGuild());

            Member member = event.getMember();
            if (event.isWebhookMessage()) return;
            if (member == null) return;
            if (event.getAuthor().isBot()) return;
            if (!args[0].startsWith(prefix)) return;

            for (Command cmd : commands) {
                if (args[0].equalsIgnoreCase(prefix + cmd.getName())) {
                    if(settings.isToggledOff(cmd)) {
                        event.getChannel().sendMessage(":x: You cannot use the " + cmd.getName() + " command because it has been toggled off!").queue();
                        return;
                    }
                    if (!member.hasPermission(cmd.getRequiredPermission())) {
                        event.getChannel().sendMessage(":x: You must have the **" + cmd.getRequiredPermission().getName() + "** permission to use this command!").queue();
                        return;
                    }
                    cmd.execute(new CommandEvent(event, cmd));
                    return;
                }
                if (Arrays.asList(cmd.getAliases()).contains(args[0].replace(prefix, ""))) {
                    if(settings.isToggledOff(cmd)) {
                        event.getChannel().sendMessage(":x: You cannot use the " + cmd.getName() + " command because it has been toggled off!").queue();
                        return;
                    }
                    if (!member.hasPermission(cmd.getRequiredPermission())) {
                        event.getChannel().sendMessage(":x: You must have the **" + cmd.getRequiredPermission().getName() + "** permission to use this command!").queue();
                        return;
                    }
                    cmd.execute(new CommandEvent(event, cmd));
                    return;
                }
            }
        } catch (IncorrectUsageException ex) {
            event.getChannel().sendMessage(ex.getMessage()).queue();
        } catch (Exception ex) {
            ExceptionHandler.sendStackTrace(ex);
        }
    }
}

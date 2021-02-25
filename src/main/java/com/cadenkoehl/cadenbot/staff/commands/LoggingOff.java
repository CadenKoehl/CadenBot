package com.cadenkoehl.cadenbot.staff.commands;

import com.cadenkoehl.cadenbot.CadenBot;
import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandCategory;
import com.cadenkoehl.cadenbot.util.exceptions.IncorrectUsageException;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.io.File;

public class LoggingOff extends Command {

    private String s = "";

    @Override
    public void execute(GuildMessageReceivedEvent event) throws IncorrectUsageException {
        String[] args = this.getArgs(event);
        String prefix = this.getPrefix(event);
        if(args[0].equalsIgnoreCase(prefix + "logging")) {
            if(event.isWebhookMessage()) {
                return;
            }
            Member member = event.getMember();

            if(!member.hasPermission(Permission.VIEW_AUDIT_LOGS)) {
                event.getChannel().sendMessage(":x: You must have the `view_audit_logs` permission to use this command!").queue();
                return;
            }
            if(args.length != 2) {
                return;
            }
            if(args[1].equalsIgnoreCase("off")) {
                String guildId = event.getGuild().getId();
                File file = new File(CadenBot.dataDirectory + "logging/" + guildId + ".txt");
                if(!file.exists()) {
                    event.getChannel().sendMessage(":x: Logging is already off!").queue();
                    return;
                }
                file.delete();
                event.getChannel().sendMessage("Logging has been turned off!\nTo turn it back on, type `" + prefix + "logchannel` `<#channel>` to choose a new channel!").queue();
            }
        }
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public CommandCategory getCategory() {
        return null;
    }

    @Override
    public Permission getRequiredPermission() {
        return Permission.VIEW_AUDIT_LOGS;
    }

    @Override
    public String getUsage(String prefix) {
        return null;
    }

    @Override
    public String[] getAliases() {
        return new String[]{"logging"};
    }

    //This exists to give the help command info on how to use the command
    public static class Help extends Command {

        @Override
        public void execute(GuildMessageReceivedEvent event) throws IncorrectUsageException {}

        @Override
        public String getName() {
            return "logging off";
        }

        @Override
        public String getDescription() {
            return "Turn logging off!";
        }

        @Override
        public CommandCategory getCategory() {
            return CommandCategory.AUTO_MOD;
        }

        @Override
        public Permission getRequiredPermission() {
            return Permission.VIEW_AUDIT_LOGS;
        }

        @Override
        public String getUsage(String prefix) {
            return "logging` `off`";
        }

        @Override
        public String[] getAliases() {
            return new String[0];
        }
    }
}

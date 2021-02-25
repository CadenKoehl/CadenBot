package com.cadenkoehl.cadenbot.joinleave.config;

import com.cadenkoehl.cadenbot.CadenBot;
import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandCategory;
import com.cadenkoehl.cadenbot.util.exceptions.IncorrectUsageException;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.io.File;


public class RandomMsgs extends Command {
    @Override
    public void execute(GuildMessageReceivedEvent event) throws IncorrectUsageException {
        String guildId = event.getGuild().getId();
        File joinFile = new File(CadenBot.dataDirectory + "joinleave/joinmessage/" + guildId + ".txt");
        File leaveFile = new File(CadenBot.dataDirectory + "joinleave/leavemessage/" + guildId + ".txt");
        if(joinFile.exists()) {
            joinFile.delete();
        }
        if(leaveFile.exists()) {
            leaveFile.delete();
        }
        event.getChannel().sendMessage(":white_check_mark: **Success**! I will now send my default, randomized messages when someone joins the server!").queue();
    }

    @Override
    public String getName() {
        return "randommsgs";
    }

    @Override
    public String getDescription() {
        return "Change back to my default, fun, randomized join/leave messages!";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.WELCOME_MESSAGES;
    }

    @Override
    public Permission getRequiredPermission() {
        return Permission.MANAGE_SERVER;
    }

    @Override
    public String getUsage(String prefix) {
        return "randommsgs`";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"randomsgs", "randommessages", "randomessages"};
    }
}
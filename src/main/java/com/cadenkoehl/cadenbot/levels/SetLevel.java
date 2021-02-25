package com.cadenkoehl.cadenbot.levels;

import com.cadenkoehl.cadenbot.CadenBot;
import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandCategory;
import com.cadenkoehl.cadenbot.util.ExceptionHandler;
import com.cadenkoehl.cadenbot.util.exceptions.IncorrectUsageException;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class SetLevel extends Command {

    @Override
    public void execute(GuildMessageReceivedEvent event) throws IncorrectUsageException {
        if(!event.getMember().isOwner())  {
            event.getChannel().sendMessage(":x: You must be the server owner to use this command!").queue();
            return;
        }
        String[] args = this.getArgs(event);
        String guildId = event.getGuild().getId();
        List<Member> members = event.getMessage().getMentionedMembers();
        if (args.length != 3) {
            throw new IncorrectUsageException(this, event);
        }
        if (members.size() == 0) {
            throw new IncorrectUsageException("Please specify a member!", this, event);
        }
        Member member = members.get(0);
        if (member.getUser().isBot()) {
            event.getChannel().sendMessage("Sorry, **" + member.getEffectiveName() + "** is a bot, and isn't invited to the super cool rank party").queue();
            return;
        }
        String memberId = member.getId();
        int amount;
        try {
            amount = Integer.parseInt(args[2]);
        } catch (NumberFormatException ex) {
            throw new IncorrectUsageException("Invalid Number!", this, event);
        }
        File file = new File(CadenBot.dataDirectory + "levels/" + guildId + " " + memberId + ".txt");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter write = new FileWriter(file);
            write.write(String.valueOf(amount));
            write.close();
        } catch (IOException ex) {
            ExceptionHandler.sendStackTrace(ex);
        }
        event.getChannel().sendMessage("Set " + member.getEffectiveName() + "'s level to " + amount).queue();
    }

    @Override
    public String getName() {
        return "setlevel";
    }

    @Override
    public String getDescription() {
        return "Allows staff to set a member's level!";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.LEVELS;
    }

    @Override
    public Permission getRequiredPermission() {
        return Permission.ADMINISTRATOR;
    }

    @Override
    public String getUsage(String prefix) {
        return "setlevel` `<@member>` `[number]`";
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }
}

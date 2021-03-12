package com.cadenkoehl.cadenbot.staff.automod.curse_censor;

import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandCategory;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandEvent;
import com.cadenkoehl.cadenbot.util.data.Data;
import com.cadenkoehl.cadenbot.util.exceptions.IncorrectUsageException;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;

import java.util.List;

public class CurseWords extends Command {
    @Override
    public void execute(CommandEvent event) throws IncorrectUsageException {
        List<String> badWords = Data.getStringsFromFile("bad_words", event.getGuild().getId());

        if (badWords == null) {
            event.getChannel().sendMessage(":x: You haven't set any blacklisted words!").queue();
            return;
        }

        if(badWords.size() == 0) {
            event.getChannel().sendMessage(":x: You haven't set any blacklisted words!").queue();
            return;
        }

        EmbedBuilder eb = new EmbedBuilder();
        eb.setAuthor(event.getGuild().getName() + "'s Blacklisted Words");

        for(String word : badWords) {
            eb.appendDescription("\n" + word);
        }

        event.getChannel().sendMessage(eb.build()).queue();
    }

    @Override
    public String getName() {
        return "cursewords";
    }

    @Override
    public String getDescription() {
        return "See a list of blacklisted words!";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.AUTO_MOD;
    }

    @Override
    public Permission getRequiredPermission() {
        return Permission.BAN_MEMBERS;
    }

    @Override
    public String getUsage(String prefix) {
        return "cursewords`";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"badwords", "blacklistedwords"};
    }
}

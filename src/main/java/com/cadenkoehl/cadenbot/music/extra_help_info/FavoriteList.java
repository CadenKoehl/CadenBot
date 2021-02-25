package com.cadenkoehl.cadenbot.music.extra_help_info;

import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandCategory;
import com.cadenkoehl.cadenbot.util.exceptions.IncorrectUsageException;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class FavoriteList extends Command {
    @Override
    public void execute(GuildMessageReceivedEvent event) throws IncorrectUsageException {}

    @Override
    public String getName() {
        return "favorite list";
    }

    @Override
    public String getDescription() {
        return "See a list of your favorite songs!";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.MUSIC;
    }

    @Override
    public Permission getRequiredPermission() {
        return null;
    }

    @Override
    public String getUsage(String prefix) {
        return "favorite` `list`";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"fav list"};
    }
}

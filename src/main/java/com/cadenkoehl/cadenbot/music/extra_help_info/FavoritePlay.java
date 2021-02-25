package com.cadenkoehl.cadenbot.music.extra_help_info;

import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandCategory;
import com.cadenkoehl.cadenbot.util.exceptions.IncorrectUsageException;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class FavoritePlay extends Command {
    @Override
    public void execute(GuildMessageReceivedEvent event) throws IncorrectUsageException {}

    @Override
    public String getName() {
        return "favorite play";
    }

    @Override
    public String getDescription() {
        return "Play all your favorite songs at once in random order!";
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
        return "favorite` `play`";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"fav play", "favorites play"};
    }
}

package com.cadenkoehl.cadenbot.music.extra_help_info;

import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandCategory;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandEvent;
import com.cadenkoehl.cadenbot.util.exceptions.IncorrectUsageException;
import net.dv8tion.jda.api.Permission;

public class FavoriteRemove extends Command {
    @Override
    public void execute(CommandEvent event) throws IncorrectUsageException {}

    @Override
    public String getName() {
        return "favorite remove";
    }

    @Override
    public String getDescription() {
        return "Remove a song from your favorites!";
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
        return "favorite` `remove` `[youtube link]`";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"fav remove"};
    }
}

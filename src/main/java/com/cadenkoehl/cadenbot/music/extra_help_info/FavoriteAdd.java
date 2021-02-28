package com.cadenkoehl.cadenbot.music.extra_help_info;

import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandCategory;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandEvent;
import com.cadenkoehl.cadenbot.util.exceptions.IncorrectUsageException;
import net.dv8tion.jda.api.Permission;

public class FavoriteAdd extends Command {
    @Override
    public void execute(CommandEvent event) throws IncorrectUsageException {}

    @Override
    public String getName() {
        return "favorite add";
    }

    @Override
    public String getDescription() {
        return "Add a song to your favorites!";
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
        return "favorite` `add` `[youtube link]`";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"fav add"};
    }
}

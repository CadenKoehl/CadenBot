package com.cadenkoehl.cadenbot.applications;

import com.cadenkoehl.cadenbot.CadenBot;
import com.cadenkoehl.cadenbot.commands.EmbedCmd;
import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandCategory;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandEvent;
import com.cadenkoehl.cadenbot.util.EmbedColor;
import com.cadenkoehl.cadenbot.util.exceptions.IncorrectUsageException;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;

import java.io.File;

public class ListApps extends Command {
    @Override
    public void execute(CommandEvent event) throws IncorrectUsageException {
        File dir = new File(CadenBot.dataDirectory + "applications/" + event.getGuild().getId());
        String[] list = dir.list();

        if(list == null) {
            event.getChannel().sendMessage(":x: This server doesn't have any applications yet!").queue();
            return;
        }

        if(list.length == 0) {
            event.getChannel().sendMessage(":x: This server doesn't have any applications yet!").queue();
            return;
        }
        if(list.length == 1 && list[0].equalsIgnoreCase(".DS_Store")) {
            event.getChannel().sendMessage(":x: This server doesn't have any applications yet!").queue();
            return;
        }

        EmbedBuilder embed = new EmbedBuilder();
        embed.setAuthor(event.getGuild().getName() + "'s Applications", null, event.getGuild().getIconUrl());
        embed.setColor(EmbedColor.BLUE);

        for(String app : list) {
            if(app.equalsIgnoreCase(".DS_Store")) continue;
            embed.appendDescription("\n" + app.replace(".json", ""));
        }

        event.getChannel().sendMessage(embed.build()).queue();
    }

    @Override
    public String getName() {
        return "listapps";
    }

    @Override
    public String getDescription() {
        return "View all applications on this server!";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.APPLICATIONS;
    }

    @Override
    public Permission getRequiredPermission() {
        return null;
    }

    @Override
    public String getUsage(String prefix) {
        return "listapps";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"listaps", "appslist", "apslist"};
    }
}

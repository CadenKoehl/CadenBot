package com.cadenkoehl.cadenbot.levels;

import com.cadenkoehl.cadenbot.CadenBot;
import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandCategory;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandEvent;
import com.cadenkoehl.cadenbot.util.exceptions.IncorrectUsageException;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.TextChannel;

import java.io.File;

public class IgnoredChannelList extends Command {
    @Override
    public void execute(CommandEvent event) throws IncorrectUsageException {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setAuthor(event.getGuild().getName(), null, event.getGuild().getIconUrl());
        embed.setTitle("List of ignored channels:");
        embed.setColor((int) Math.round(Math.random() * 999999));
        embed.setDescription("Users cannot earn xp in these channels\n------------------------------");
        boolean hasAnyChannels = false;
        for(TextChannel channel : event.getGuild().getTextChannels()) {
            String channelId = channel.getId();
            File file = new File(CadenBot.dataDirectory + "levels/ignored_channels/" + channelId + ".txt");
            if(file.exists()) {
                embed.appendDescription("\n" + channel.getAsMention());
                hasAnyChannels = true;
            }
        }
        if(!hasAnyChannels) {
            event.getChannel().sendMessage(":x: You don't have any ignored channels yet!").queue();
            return;
        }
        event.getChannel().sendMessage(embed.build()).queue();
    }

    @Override
    public String getName() {
        return "ignoredchannels";
    }

    @Override
    public String getDescription() {
        return "See a list of ignored channels!";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.LEVELS;
    }

    @Override
    public Permission getRequiredPermission() {
        return null;
    }

    @Override
    public String getUsage(String prefix) {
        return "ignoredchannels`";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"igch"};
    }
}

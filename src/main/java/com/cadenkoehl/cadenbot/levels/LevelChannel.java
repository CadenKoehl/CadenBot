package com.cadenkoehl.cadenbot.levels;

import com.cadenkoehl.cadenbot.CadenBot;
import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandCategory;
import com.cadenkoehl.cadenbot.util.Constants;
import com.cadenkoehl.cadenbot.util.exceptions.IncorrectUsageException;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;


public class LevelChannel extends Command {
    @Override
    public void execute(GuildMessageReceivedEvent event) throws IncorrectUsageException {
        String channelType = this.getArgs(event)[1];
        File file = new File(CadenBot.dataDirectory + "levels/channel/" + event.getGuild().getId() + ".txt");
        try {
            String channelId = event.getMessage().getMentionedChannels().get(0).getId();
            if(!file.exists()) {
                file.createNewFile();
            }
            FileWriter write = new FileWriter(file);
            write.write(channelId);
            write.close();
            event.getChannel().sendMessage(":white_check_mark: **Success**! Level-up messages will now show up in " + event.getGuild().getTextChannelById(channelId).getAsMention() + "!\nTo make them show up in the channel the user leveled up in, type `" + getPrefix(event) + "levelchannel` `default`").queue();
        }
        catch (IndexOutOfBoundsException ex) {
            if(!channelType.equalsIgnoreCase("default")) {
                event.getChannel().sendMessage(":x: Please specify a channel!").queue();
            }
            if(channelType.equalsIgnoreCase("default")) {
                file.delete();
                event.getChannel().sendMessage(":white_check_mark: **Success**! The leveling messages channel was set to the default channel. This means they will show up in the same channel the member leveled up in!").queue();
            }
        }
        catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        catch (IOException ex) {
            event.getChannel().sendMessage("A fatal error has occurred! If this issue persists, please join the support server! (type " + Constants.getPrefix(event.getGuild().getId()) + "help)").queue();
        }
    }

    @Override
    public String getName() {
        return "levelchannel";
    }

    @Override
    public String getDescription() {
        return "Set a custom channel for leveling messages!";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.LEVELS;
    }

    @Override
    public Permission getRequiredPermission() {
        return Permission.MANAGE_SERVER;
    }

    @Override
    public String getUsage(String prefix) {
        return "levelchannel` `<#channel>`\nOr, `" + prefix + "levelchannel` `default` (sets levelmsgs to show up in the channel the user leveled up in)";
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }
}

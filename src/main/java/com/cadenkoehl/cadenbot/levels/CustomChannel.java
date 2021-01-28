package com.cadenkoehl.cadenbot.levels;

import com.cadenkoehl.cadenbot.CadenBot;
import com.cadenkoehl.cadenbot.util.Constants;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;


public class CustomChannel extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        if(args[0].equalsIgnoreCase(Constants.getPrefix(event.getGuild().getId()) + "levelchannel")) {
            String channelType = Arrays.stream(args).skip(1).collect(Collectors.joining(" "));
            if(!event.getAuthor().isBot()) {
                if(event.getMember().hasPermission(Permission.MANAGE_SERVER)) {
                    File file = new File(CadenBot.dataDirectory + "levels/channel/" + event.getGuild().getId() + ".txt");
                    try {
                        String channelId = event.getMessage().getMentionedChannels().get(0).getId();
                        if(!file.exists()) {
                            file.createNewFile();
                        }
                        FileWriter write = new FileWriter(file);
                        write.write(channelId);
                        write.close();
                        event.getChannel().sendMessage("Level-up messages will now show up in " + event.getGuild().getTextChannelById(channelId).getAsMention() + "!").queue();
                    }
                    catch (IndexOutOfBoundsException ex) {
                        if(!channelType.equalsIgnoreCase("default")) {
                            event.getChannel().sendMessage("Please specify a channel!").queue();
                        }
                        if(channelType.equalsIgnoreCase("default")) {
                            file.delete();
                            event.getChannel().sendMessage("The leveling messages channel was set to the default channel. This means they will show up in the same channel the member leveled up in!").queue();
                        }
                    }
                    catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    }
                    catch (IOException ex) {
                        event.getChannel().sendMessage("A fatal error has occurred! If this issue persists, please join the support server! (type " + Constants.getPrefix(event.getGuild().getId()) + "help)").queue();
                    }
                }
                if(!event.getMember().hasPermission(Permission.MANAGE_SERVER)) {
                    event.getChannel().sendMessage("You must have the `manage_server` permission to use that command!").queue();
                }
            }
        }
    }
}

package com.cadenkoehl.cadenbot.joinleave.config;

import com.cadenkoehl.cadenbot.CadenBot;
import com.cadenkoehl.cadenbot.Constants;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class LeaveMsg extends ListenerAdapter {
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        String guildId = event.getGuild().getId();
        String prefix = Constants.getPrefix(guildId);
        if(args[0].equalsIgnoreCase(prefix + "leavemsg") || args[0].equalsIgnoreCase(prefix + "leavemessage")) {
            if(event.isWebhookMessage()) {
                return;
            }
            Member member = event.getMember();
            User user = event.getAuthor();
            if(user.isBot()) {
                return;
            }
            if(!member.hasPermission(Permission.MANAGE_SERVER)) {
                event.getChannel().sendMessage(":x: You must have the `manage_server` permission to use this command!").queue();
                return;
            }
            if(args.length == 1) {
                event.getChannel().sendMessage("Please specify a message!").queue();
                return;
            }
            String message = Arrays.stream(args).skip(1).collect(Collectors.joining(" "));
            try {
                File file = new File(CadenBot.dataDirectory + "joinleave/leavemessage/" + guildId + ".txt");
                FileWriter write = new FileWriter(file);
                write.write(message);
                write.close();
                event.getChannel().sendMessage(":white_check_mark: **Success**! Your goodbye message was saved! " +
                        "Here's an example of what it will look like:\n" +
                        message.replace("{user}", user.getAsTag())).queue();
            } catch (IOException e) {
                event.getChannel().sendMessage(Constants.ERROR_MESSAGE).queue();
                e.printStackTrace();
            }
        }
    }
}
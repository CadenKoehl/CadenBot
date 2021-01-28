package com.cadenkoehl.cadenbot.joinleave.config;

import com.cadenkoehl.cadenbot.CadenBot;
import com.cadenkoehl.cadenbot.util.Constants;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.io.File;


public class RandomMsgs extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        String guildId = event.getGuild().getId();
        String prefix = Constants.getPrefix(guildId);
        if(args[0].equalsIgnoreCase(prefix + "joinleave")) {
            if(event.isWebhookMessage()) {
                return;
            }
            Member member = event.getMember();
            User user = event.getAuthor();
            if(user.isBot()) {
                return;
            }
            if(args.length == 1) {
                System.out.println(args.length);
                return;
            }
            if(!args[1].equalsIgnoreCase("random")) {
                return;
            }
            if(!member.hasPermission(Permission.MANAGE_SERVER)) {
                event.getChannel().sendMessage(":x: You must have the `manage_server` permission to use this command!").queue();
                return;
            }
            File joinFile = new File(CadenBot.dataDirectory + "joinleave/joinmessage/" + guildId + ".txt");
            File leaveFile = new File(CadenBot.dataDirectory + "joinleave/leavemessage/" + guildId + ".txt");
            if(joinFile.exists()) {
                joinFile.delete();
            }
            if(leaveFile.exists()) {
                leaveFile.delete();
            }
            event.getChannel().sendMessage(":white_check_mark: **Success**! I will now send my default, randomized messages when someone joins the server!").queue();
        }
    }
}
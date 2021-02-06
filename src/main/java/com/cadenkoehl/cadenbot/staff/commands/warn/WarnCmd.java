package com.cadenkoehl.cadenbot.staff.commands.warn;

import com.cadenkoehl.cadenbot.util.Constants;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class WarnCmd extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        String guildId = event.getGuild().getId();
        String prefix = Constants.getPrefix(guildId);
        if(args[0].equalsIgnoreCase(prefix + "warn")) {
            if(event.isWebhookMessage()) {
                return;
            }
            if(event.getAuthor().isBot()) {
                return;
            }
            Member mod = event.getMember();
            if(!mod.hasPermission(Permission.BAN_MEMBERS)) {
                event.getChannel().sendMessage(":x: You can't use that!").queue();
                return;
            }
            if(args.length == 1) {
                event.getChannel().sendMessage("**Incomplete Command!**\nUsage: `" + prefix + "warn` `@user` `[reason]`").queue();
                return;
            }
            List<Member> mentionedMembers = event.getMessage().getMentionedMembers();
            if(mentionedMembers.size() == 0) {
                event.getChannel().sendMessage("**Incomplete Command!**\nUsage: `" + prefix + "warn` `@user` `[reason]`").queue();
                return;
            }
            String reason = Arrays.stream(args).skip(2).collect(Collectors.joining(" "));
            if(reason.isEmpty()) {
                reason = "Unspecified";
            }
            Member member = mentionedMembers.get(0);
            if(member.hasPermission(Permission.BAN_MEMBERS)) {
                event.getChannel().sendMessage(":x: You can't warn a moderator!").queue();
                return;
            }
            WarningManager manager = new WarningManager();
            manager.warn(member, event.getChannel(), reason);
        }
    }
}

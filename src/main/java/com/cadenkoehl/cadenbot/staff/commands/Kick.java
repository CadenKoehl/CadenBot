package com.cadenkoehl.cadenbot.staff.commands;

import com.cadenkoehl.cadenbot.Constants;
import com.cadenkoehl.cadenbot.EmbedColor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.HierarchyException;
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Kick extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String id = event.getGuild().getId();
        String prefix = Constants.getPrefix(id);
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        if(args[0].equalsIgnoreCase(prefix + "kick")) {
            if(event.isWebhookMessage()) {
                return;
            }
            Member mod = event.getMember();
            if(mod == null) {
                event.getChannel().sendMessage(Constants.ERROR_MESSAGE).queue();
            }
            if(mod.getUser().isBot()) {
                return;
            }
            if(!mod.hasPermission(Permission.KICK_MEMBERS)) {
                event.getChannel().sendMessage(":x: You can't use that!").queue();
                return;
            }
            List<Member> mentionedMembers = event.getMessage().getMentionedMembers();
            if(mentionedMembers.size() == 0) {
                event.getChannel().sendMessage(":x: Please specify a member to kick!\n**Usage:** `" + prefix + "kick` `<@user>` `[reason]`").queue();
                return;
            }
            String reason = Arrays.stream(args).skip(2).collect(Collectors.joining(" "));
            Member member = mentionedMembers.get(0);
            String userTag = member.getUser().getAsTag();
            EmbedBuilder embed = new EmbedBuilder();
            if(reason.isEmpty()) {
                reason = "Unspecified";
            }
            embed.setDescription("Reason: " + reason);
            embed.setAuthor(userTag + " was kicked!", null, member.getUser().getEffectiveAvatarUrl());
            embed.setColor(EmbedColor.RED);
            try {
                member.kick().queue();
                event.getChannel().sendMessage(embed.build()).queue();
            }
            catch (HierarchyException ex) {
                event.getChannel().sendMessage(":x: You can't kick a moderator!").queue();
            }
            catch (InsufficientPermissionException ex) {
                event.getChannel().sendMessage(":x: I cannot perform this action due to lack of permission! Please give me the `kick_members` permission!").queue();
            }
        }
    }
}

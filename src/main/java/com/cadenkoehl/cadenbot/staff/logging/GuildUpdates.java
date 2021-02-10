package com.cadenkoehl.cadenbot.staff.logging;

import com.cadenkoehl.cadenbot.staff.logging.Logger;
import com.cadenkoehl.cadenbot.util.EmbedColor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.guild.invite.GuildInviteCreateEvent;
import net.dv8tion.jda.api.events.guild.update.*;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class GuildUpdates extends ListenerAdapter {

    @Override
    public void onGuildUpdateName(@NotNull GuildUpdateNameEvent event) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(EmbedColor.GREEN);
        embed.setAuthor("Server Name Updated", null, event.getGuild().getIconUrl());
        embed.addField("New Name:", event.getNewName(), true);
        embed.addField("Old Name:", event.getOldName(), true);
        Logger.log(embed.build(), event.getGuild());
    }

    @Override
    public void onGuildInviteCreate(@NotNull GuildInviteCreateEvent event) {
        Invite inv = event.getInvite();
        User user = inv.getInviter();
        Guild guild = event.getGuild();
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(EmbedColor.GREEN);
        if(user == null) {
            embed.setAuthor("\uD83D\uDCE8 New Invite Created!", null, guild.getIconUrl());
        }
        else {
            embed.setAuthor("\uD83D\uDCE8 New Invite Created by " + user.getAsTag(), null, user.getEffectiveAvatarUrl());
            embed.setFooter(guild.getName(), guild.getIconUrl());
        }
        int maxUsesInt = inv.getMaxUses();
        String maxUses = String.valueOf(maxUsesInt);
        if(maxUses.equalsIgnoreCase("0")) {
            maxUses = "♾️";
        }
        embed.setDescription(
                "**Invite URL**: " + inv.getUrl()
                + "\n**Max Uses**: " + maxUses
                + "\n**Channel**: " + event.getChannel().getName()
        );
        Logger.log(embed.build(), guild);
    }

    @Override
    public void onGuildUpdateSystemChannel(@NotNull GuildUpdateSystemChannelEvent event) {
        TextChannel oldChannel = event.getOldSystemChannel();
        TextChannel newChannel = event.getNewSystemChannel();
        String oldChannelName = "[No System Channel]";
        String newChannelName = "[No System Channel]";
        if(oldChannel != null) {
            oldChannelName = oldChannel.getAsMention();
        }
        if(newChannel != null) {
            newChannelName = newChannel.getAsMention();
        }
        EmbedBuilder embed = new EmbedBuilder();
        embed.setAuthor("System Messages Channel Updated!", null, event.getGuild().getIconUrl());
        embed.setColor(EmbedColor.GREEN);
        embed.addField("Old Channel", oldChannelName, true);
        embed.addField("New Channel", newChannelName, true);
        Logger.log(embed.build(), event.getGuild());
    }

    @Override
    public void onGuildUpdateAfkChannel(@NotNull GuildUpdateAfkChannelEvent event) {
        VoiceChannel oldChannel = event.getOldAfkChannel();
        VoiceChannel newChannel = event.getNewAfkChannel();
        String oldChannelName = "[No AFK Channel]";
        String newChannelName = "[No AFK Channel]";
        if(oldChannel != null) {
            oldChannelName = oldChannel.getName();
        }
        if(newChannel != null) {
            newChannelName = newChannel.getName();
        }
        EmbedBuilder embed = new EmbedBuilder();
        embed.setAuthor("AFK Channel Updated!", null, event.getGuild().getIconUrl());
        embed.setColor(EmbedColor.GREEN);
        embed.addField("Old Channel", oldChannelName, true);
        embed.addField("New Channel", newChannelName, true);
        Logger.log(embed.build(), event.getGuild());
    }

    @Override
    public void onGuildUpdateOwner(@NotNull GuildUpdateOwnerEvent event) {
        Member newOwner = event.getNewOwner();
        Member oldOwner = event.getOldOwner();

        if(newOwner == null) {
            return;
        }
        if(oldOwner == null) {
            return;
        }

        EmbedBuilder embed = new EmbedBuilder();
        embed.setAuthor("\uD83D\uDD12" + oldOwner.getUser().getAsTag() + " has transferred ownership! \uD83D\uDD12", null, oldOwner.getUser().getEffectiveAvatarUrl());
        embed.setFooter("New Owner: " + newOwner.getUser().getAsTag(), oldOwner.getUser().getEffectiveAvatarUrl());
        embed.setColor(EmbedColor.YELLOW);
        Logger.log(embed.build(), event.getGuild());
    }
}























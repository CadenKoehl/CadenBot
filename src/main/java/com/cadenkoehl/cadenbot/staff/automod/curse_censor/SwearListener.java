package com.cadenkoehl.cadenbot.staff.automod.curse_censor;

import com.cadenkoehl.cadenbot.staff.commands.warn.WarningManager;
import com.cadenkoehl.cadenbot.util.data.Data;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SwearListener extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {

        if(event.isWebhookMessage()) return;
        if(event.getAuthor().isBot()) return;

        String guildId = event.getGuild().getId();

        List<String> badWords = Data.getStringsFromFile("bad_words", guildId);

        if(event.getMember().hasPermission(Permission.MANAGE_SERVER)) return;

        if(badWords == null) return;

        for(String word : badWords) {
            if(event.getMessage().getContentRaw().toLowerCase().contains(word)) {
                event.getMessage().delete().queue();
                WarningManager manager = new WarningManager();
                manager.warn(event.getMember(), event.getChannel(), "Use of a blacklisted word", event.getGuild().getSelfMember());
                break;
            }
        }
    }
}

package com.cadenkoehl.cadenbot.music;

import com.cadenkoehl.cadenbot.music.lavaplayer.MusicManager;
import com.cadenkoehl.cadenbot.music.lavaplayer.PlayerManager;
import com.cadenkoehl.cadenbot.util.Constants;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class Loop extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        Guild guild = event.getGuild();
        String prefix = Constants.getPrefix(guild.getId());
        if(args[0].equalsIgnoreCase(prefix + "loop") || args[0].equalsIgnoreCase(prefix + "repeat")) {
            if(event.isWebhookMessage()) {
                return;
            }
            Member member = event.getMember();
            User user = event.getAuthor();
            Member selfMember = event.getGuild().getSelfMember();
            if(user.isBot()) {
                return;
            }
            GuildVoiceState memberVoiceState = member.getVoiceState();
            GuildVoiceState selfVoiceState = selfMember.getVoiceState();
            if(!memberVoiceState.inVoiceChannel()) {
                event.getChannel().sendMessage(":x: You must be in a voice channel to use this command!").queue();
                return;
            }
            if(!selfVoiceState.inVoiceChannel()) {
                event.getChannel().sendMessage(":x: I must be in a voice channel to use this command! (use `" + prefix + "join`)").queue();
                return;
            }
            if(!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
                event.getChannel().sendMessage(":x: We must be in the same voice channel!").queue();
                return;
            }
            MusicManager musicManager = PlayerManager.getInstance().getMusicManager(guild);
            boolean newRepeating = !musicManager.scheduler.repeating;
            musicManager.scheduler.repeating = newRepeating;
            if(newRepeating) {
                event.getChannel().sendMessage(":white_check_mark: I will now repeat the current song!").queue();
            }

            if(!newRepeating) {
                event.getChannel().sendMessage(":white_check_mark: Repeating was turned off!").queue();
            }
        }
    }
}
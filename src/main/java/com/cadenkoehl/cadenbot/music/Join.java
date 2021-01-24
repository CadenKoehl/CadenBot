package com.cadenkoehl.cadenbot.music;

import com.cadenkoehl.cadenbot.Constants;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

public class Join extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        String prefix = Constants.getPrefix(event.getGuild().getId());
        if(args[0].equalsIgnoreCase(prefix + "join") || args[0].equalsIgnoreCase(prefix + "jn")) {
            Member bot = event.getGuild().getSelfMember();
            Member member = event.getMember();
            GuildVoiceState selfVoiceState = bot.getVoiceState();
            if(!selfVoiceState.inVoiceChannel()) {
                if(event.getMember().getVoiceState().inVoiceChannel()) {
                    if(bot.hasPermission(Permission.VOICE_CONNECT)) {
                        VoiceChannel memberChannel = member.getVoiceState().getChannel();
                        AudioManager audioManager = event.getGuild().getAudioManager();
                        audioManager.openAudioConnection(memberChannel);
                        event.getChannel().sendMessage("🔊 Joined **" + memberChannel.getName() + "**").queue();
                    }
                    if(!bot.hasPermission(Permission.VOICE_CONNECT)) {
                        event.getChannel().sendMessage(":x: You have not granted me permission to connect to the voice channel!").queue();
                    }

                }
                if(!event.getMember().getVoiceState().inVoiceChannel()) {
                    event.getChannel().sendMessage(":x: You must be in a voice channel to use this command!").queue();
                }
            }
            if(selfVoiceState.inVoiceChannel()) {
                event.getChannel().sendMessage(":x: I am already in another voice channel!").queue();
            }
        }
    }
}
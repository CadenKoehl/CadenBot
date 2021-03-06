package com.cadenkoehl.cadenbot.music;

import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandCategory;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandEvent;
import com.cadenkoehl.cadenbot.music.lavaplayer.MusicManager;
import com.cadenkoehl.cadenbot.util.exceptions.IncorrectUsageException;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

public class Join extends Command {

    @Override
    public void execute(CommandEvent event) throws IncorrectUsageException {
        Member bot = event.getGuild().getSelfMember();
        Member member = event.getMember();
        GuildVoiceState selfVoiceState = bot.getVoiceState();
        if(!selfVoiceState.inVoiceChannel()) {
            if(event.getMember().getVoiceState().inVoiceChannel()) {
                if(bot.hasPermission(Permission.VOICE_CONNECT)) {
                    VoiceChannel memberChannel = member.getVoiceState().getChannel();
                    AudioManager audioManager = event.getGuild().getAudioManager();
                    audioManager.openAudioConnection(memberChannel);
                    event.getChannel().sendMessage("🔊 Joined **" + memberChannel.getName() + "** and bound to " + event.getChannel().getAsMention()).queue();
                    MusicManager.musicTextChannel.put(event.getGuild(), event.getChannel());
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

    @Override
    public String getName() {
        return "join";
    }

    @Override
    public String getDescription() {
        return "Joins your voice channel!";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.MUSIC;
    }

    @Override
    public Permission getRequiredPermission() {
        return null;
    }

    @Override
    public String getUsage(String prefix) {
        return "join`";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"jn"};
    }
}
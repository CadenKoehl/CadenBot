package com.cadenkoehl.cadenbot.music;

import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandCategory;
import com.cadenkoehl.cadenbot.util.Constants;
import com.cadenkoehl.cadenbot.music.lavaplayer.MusicManager;
import com.cadenkoehl.cadenbot.music.lavaplayer.PlayerManager;
import com.cadenkoehl.cadenbot.util.exceptions.IncorrectUsageException;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;


public class Leave extends Command {

    @Override
    public void execute(GuildMessageReceivedEvent event) throws IncorrectUsageException {
        Member bot = event.getGuild().getSelfMember();
        Member member = event.getMember();
        GuildVoiceState selfVoiceState = bot.getVoiceState();
        if(selfVoiceState.inVoiceChannel()) {
            VoiceChannel botChannel = selfVoiceState.getChannel();
            if(member.getVoiceState().inVoiceChannel()) {
                VoiceChannel memberChannel = member.getVoiceState().getChannel();
                if(botChannel == memberChannel) {
                    MusicManager musicManager = PlayerManager.getInstance().getMusicManager(event.getGuild());
                    if(musicManager.scheduler.player.getPlayingTrack() != null) {
                        musicManager.scheduler.repeating = false;
                        musicManager.scheduler.player.stopTrack();
                        musicManager.scheduler.queue.clear();
                    }
                    AudioManager audioManager = event.getGuild().getAudioManager();
                    audioManager.closeAudioConnection();
                    event.getChannel().sendMessage(":white_check_mark: Left **" + botChannel.getName() + "**").queue();
                }
                if(botChannel != memberChannel) {
                    event.getChannel().sendMessage(":x: You must be in the same voice channel as me to use this command!").queue();
                }
            }
            if(!member.getVoiceState().inVoiceChannel()) {
                event.getChannel().sendMessage(":x: You must be in a voice channel to use this command!").queue();
            }
        }
        if(!bot.getVoiceState().inVoiceChannel()) {
            event.getChannel().sendMessage(":x: I am not in a voice channel!").queue();
        }
    }

    @Override
    public String getName() {
        return "leave";
    }

    @Override
    public String getDescription() {
        return "Leaves the current voice channel!";
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
        return "leave`";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"dc", "disconnect"};
    }
}

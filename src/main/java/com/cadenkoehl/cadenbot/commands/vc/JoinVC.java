package com.cadenkoehl.cadenbot.commands.vc;

import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;
public class JoinVC extends ListenerAdapter {
	
	VoiceChannel channel;
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String[] args = event.getMessage().getContentRaw().split("\\s+");
		if (args[0].equalsIgnoreCase("-join")) {
			
			try {
				channel = event.getMember().getVoiceState().getChannel();
				AudioManager audioManager = event.getGuild().getAudioManager();
				audioManager.openAudioConnection(channel);
				
				}
			
			catch(java.lang.IllegalArgumentException e) {
				event.getChannel().sendMessage("You must be in a voice channel to use this command").queue();
				
			}
		}
	}
}

package com.cadenkoehl.cadenbot.joinleave;

import com.cadenkoehl.cadenbot.util.Constants;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class JoinServer extends ListenerAdapter {
	
	public void onGuildJoin(GuildJoinEvent event) {
		String prefix = Constants.getPrefix(event.getGuild().getId());
		TextChannel systemChannel = event.getGuild().getSystemChannel();
		Member self = event.getGuild().getSelfMember();
		if(systemChannel == null) {
			event.getGuild().getDefaultChannel().sendMessage("Hello! Thanks for inviting me to your server! If you have any questions, you can use \"" + prefix + "help\"").queue();
		}
		else {
			event.getGuild().getSystemChannel().sendMessage("Hello! Thanks for inviting me to your server! If you have any questions, you can use \"" + prefix + "help\"\n" +
					"This is your system channel, so I will send fun, random join/leave messages here! If you want to change this, type" + prefix + "help welcomemsgs").queue();
			if(!self.hasPermission(Permission.ADMINISTRATOR)) {
				event.getGuild().getSystemChannel().sendMessage(":x: **Error**! Since you have not granted me the `administrator` permission, most of my features and commands won't work!").queue();
			}
		}
	}
}
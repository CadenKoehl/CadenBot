package com.cadenkoehl.cadenbot.joinleave;

import com.cadenkoehl.cadenbot.CadenBot;
import net.dv8tion.jda.api.entities.Icon;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;

public class JoinServer extends ListenerAdapter {
	
	public void onGuildJoin(GuildJoinEvent event) {
		if(event.getGuild().getSystemChannel() != null) {
			event.getGuild().getSystemChannel().sendMessage("Hello! Thanks for inviting me to your server! If you have any questions, you can use \"-help\"").queue();
			event.getGuild().getSystemChannel().sendMessage("This is your system channel, so I will send a random welcome message here when someone joins the server!\n" +
					"If you want to turn this off, type `-help welcomemsgs`! :)").queue();
		}
		else {
			event.getGuild().getDefaultChannel().sendMessage("Hello! Thanks for inviting me to your server! If you have any questions, you can use \"-help\"").queue();
		}
	}
}
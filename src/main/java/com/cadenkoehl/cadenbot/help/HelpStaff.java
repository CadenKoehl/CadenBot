package com.cadenkoehl.cadenbot.help;

import java.util.concurrent.TimeUnit;

import com.cadenkoehl.cadenbot.util.Constants;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class HelpStaff extends ListenerAdapter {

	private static String id;
	private static String prefix = "-";
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String[] args = event.getMessage().getContentRaw().split(" ");
		id = event.getGuild().getId();
		String prefix = Constants.getPrefix(id);
		if (args[0].equalsIgnoreCase(prefix + "help")) {
			if(args.length < 2) {
				return;
			}
			if(!args[1].equalsIgnoreCase("staff")) {
				return;
			}
			if(!event.getGuild().getSelfMember().hasPermission(Permission.ADMINISTRATOR)) {
				event.getChannel().sendMessage(":x: My staff commands will not work because I lack the `administrator` permission!").queue();
				return;
			}
			if(!event.getMember().hasPermission(Permission.BAN_MEMBERS)) {
				event.getChannel().sendMessage(":x: You can't use that!").queue();
				return;
			}
			event.getChannel().sendTyping().delay(500L, TimeUnit.MILLISECONDS).complete();
			
			EmbedBuilder help = new EmbedBuilder();
			help.setTitle("Staff Commands");
			help.setDescription("Here are some useful commands to make moderation easier on your server!\n**--------**\n**Commands**\n`"
				 	+ prefix + "mute` `<@member>` `[reason]` - Mutes a member\n`"
					+ prefix + "unmute` `<@Member>` - Unmutes a member\n`"
					+ prefix + "ban` `<@Member>` `[reason]` - Bans a member\n`"
					+ prefix + "unban` `<@Member>` - Unbans a member\n`"
					+ prefix + "warn` `<@Member>` `[reason]` - Warns a member (3 warnings = automatic mute)\n`"
					+ prefix + "clearwarns` `<@Member>` - Clear a member's warnings!\n`"
					+ prefix + "logchannel` - Find out the current logging channel\n`"
					+ prefix + "logchannel` `<#channel>` - Sets the audit log channel\n`"
					+ prefix + "logging` `off` - Turns logging off");
			help.setColor((int) Math.round(Math.random() * 999999));

			
			event.getChannel().sendMessage(help.build()).queue();
			
			System.out.println((String.format("==========\n[COMMAND] %s ran command \"%s\", on server \"%s\"", event.getAuthor().getAsTag(), event.getMessage().getContentDisplay(), event.getGuild().getName())));
			
		}
	}
}

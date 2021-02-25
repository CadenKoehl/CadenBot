package com.cadenkoehl.cadenbot.staff.commands.mute;

import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandCategory;
import com.cadenkoehl.cadenbot.util.Constants;
import com.cadenkoehl.cadenbot.util.exceptions.IncorrectUsageException;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class TempMute extends Command {
    @Override
    public void execute(GuildMessageReceivedEvent event) throws IncorrectUsageException {

        String[] args = this.getArgs(event);
        String prefix = this.getPrefix(event);

        Member mod = event.getMember();

        List<Member> mentionedMembers = event.getMessage().getMentionedMembers();

        if(mentionedMembers.size() == 0) {
            event.getChannel().sendMessage(":x: Please specify a member to tempmute!").queue();
            return;
        }

        Member member = mentionedMembers.get(0);

        String reason = Arrays.stream(args).skip(3).collect(Collectors.joining(" "));

        if(reason.isEmpty()) reason = "Unspecified";

        if(member.hasPermission(Permission.BAN_MEMBERS)) {
            event.getChannel().sendMessage(":x: You can't mute a moderator!").queue();
            return;
        }

        if(args.length < 3) {
            event.getChannel().sendMessage(":x: **Incorrect Usage!** (type " + prefix + "help staff)").queue();
            return;
        }

        int time;

        try {
            time = Integer.parseInt(args[2]
                    .replace("m", "")
                    .replace("M", "")
                    .replace("h", "")
                    .replace("H", "")
                    .replace("d", "")
                    .replace("D", "")
            );
        }
        catch (NumberFormatException ex) {
            event.getChannel().sendMessage(":x: **Invalid Time!**\nUsage Example: `" + prefix + "mute` " + event.getGuild().getSelfMember().getAsMention() + " `10m` `spam`\n**Time Units:**\n" +
                    "m = Minutes\n" +
                    "h = Hours\n" +
                    "d = Days\n").queue();
            return;
        }

        TimeUnit unit;

        if(args[2].contains("m") || args[2].contains("M")) {
            unit = TimeUnit.MINUTES;
        }

        else if(args[2].contains("h") || args[2].contains("H")) {
            unit = TimeUnit.HOURS;
        }

        else if(args[2].contains("d") || args[2].contains("D")) {
            unit = TimeUnit.DAYS;
        }

        else {
            event.getChannel().sendMessage(":x: **Invalid Time!**\nUsage Example: `" + prefix + "mute` " + event.getGuild().getSelfMember().getAsMention() + " `10m` `spam`\n**Time Units:**\n" +
                    "m = Minutes\n" +
                    "h = Hours\n" +
                    "d = Days\n").queue();
            return;
        }

        MuteManager.tempMute(member, event.getChannel(), reason, time, unit, mod);
    }

    @Override
    public String getName() {
        return "tempmute";
    }

    @Override
    public String getDescription() {
        return "Mute a member for a given amount of time!";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.STAFF;
    }

    @Override
    public Permission getRequiredPermission() {
        return Permission.VOICE_MUTE_OTHERS;
    }

    @Override
    public String getUsage(String prefix) {
        return "tempmute` `<@member>` `[time]` `[reason]`";
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }
}























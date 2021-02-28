package com.cadenkoehl.cadenbot.commands;

import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandCategory;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandEvent;
import com.cadenkoehl.cadenbot.util.exceptions.IncorrectUsageException;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;

import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class UserInfo extends Command {

    @Override
    public void execute(CommandEvent event) throws IncorrectUsageException {
        List<Member> mentionedMembers = event.getMessage().getMentionedMembers();
        if(mentionedMembers.size() == 0) throw new IncorrectUsageException(this, event);
        Member member = mentionedMembers.get(0);
        User user = member.getUser();
        String name = user.getName();
        String dayOfWeekCreated = user.getTimeCreated().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.US);
        String monthCreated = user.getTimeCreated().getMonth().getDisplayName(TextStyle.FULL, Locale.US);
        int dayCreated = user.getTimeCreated().getDayOfMonth();
        int yearCreated = user.getTimeCreated().getYear();
        String id = user.getId();
        String avatarURL = user.getEffectiveAvatarUrl();

        EmbedBuilder embed = new EmbedBuilder();
        embed.setAuthor(member.getUser().getAsTag(), null, member.getUser().getEffectiveAvatarUrl());
        if(user.isBot()) {
            embed.setTitle(name + " **[BOT]**'s profile:\n------------------");
        }
        if(!user.isBot()) {
            embed.setTitle(name + "'s profile:\n------------------");
        }
        embed.addField("**Account Created**: *" + dayOfWeekCreated + ", " + monthCreated + " " + dayCreated + ", " + yearCreated + "*","", false);
        embed.addField("**User ID**: *" + id + "*","", false);
        embed.addField("**Avatar URL**: ","[Click Here](" + avatarURL + " \"Click Here\")", false);
        embed.setColor((int) Math.round(Math.random() * 999999));
        event.getChannel().sendMessage(embed.build()).queue();
    }

    @Override
    public String getName() {
        return "profile";
    }

    @Override
    public String getDescription() {
        return "See a user's profile!";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.COMMAND;
    }

    @Override
    public Permission getRequiredPermission() {
        return null;
    }

    @Override
    public String getUsage(String prefix) {
        return "profile` `<@member>`";
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }
}
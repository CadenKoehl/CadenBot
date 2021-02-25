package com.cadenkoehl.cadenbot.commands;

import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandCategory;
import com.cadenkoehl.cadenbot.util.ExceptionHandler;
import com.cadenkoehl.cadenbot.util.exceptions.IncorrectUsageException;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class FakeUser extends Command {

    @Override
    public void execute(GuildMessageReceivedEvent event) throws IncorrectUsageException {
        List<Member> mentionedMembers = event.getMessage().getMentionedMembers();
        if(mentionedMembers.size() == 0) {
            throw new IncorrectUsageException(this, event);
        }
        Member member = mentionedMembers.get(0);
        String content = Arrays.stream(this.getArgs(event)).skip(2).collect(Collectors.joining(" "));
        if(content.isEmpty()) {
            throw new IncorrectUsageException(this, event);
        }
        String url = event.getChannel().createWebhook("Fake User: " + member.getUser().getAsTag() + " Created by " + event.getAuthor().getAsTag()).complete().getUrl();
        Webhooks webhook = new Webhooks(url);
        webhook.setAvatarUrl(member.getUser().getEffectiveAvatarUrl());
        webhook.setUsername(member.getEffectiveName());
        webhook.setContent(content);
        try {
            webhook.execute();
        }
        catch (IOException ex) {
            ExceptionHandler.sendStackTrace(ex);
        }
        event.getMessage().delete().queue();
        event.getChannel().retrieveWebhooks().complete().get(event.getChannel().retrieveWebhooks().complete().size() - 1).delete().queueAfter(4, TimeUnit.SECONDS);
    }

    @Override
    public String getName() {
        return "fakemsg";
    }

    @Override
    public String getDescription() {
        return "Create a fake message sent from the user of your choice!";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.FUN;
    }

    @Override
    public Permission getRequiredPermission() {
        return Permission.MANAGE_WEBHOOKS;
    }

    @Override
    public String getUsage(String prefix) {
        return "fakemsg` `<@member>` `[message]`";
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }
}

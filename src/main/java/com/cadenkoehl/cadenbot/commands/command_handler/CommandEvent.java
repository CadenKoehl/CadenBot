package com.cadenkoehl.cadenbot.commands.command_handler;

import com.cadenkoehl.cadenbot.support_tickets.config.TicketConfig;
import com.cadenkoehl.cadenbot.util.Constants;
import com.cadenkoehl.cadenbot.util.exceptions.IncorrectUsageException;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.stream.Collectors;

public class CommandEvent {

    private final GuildMessageReceivedEvent event;
    private final Guild guild;
    private final Member member;
    private final Message message;
    private final TextChannel channel;
    private final User author;
    private final JDA jda;
    private final String messageId;
    private final long messageIdLong;
    private final boolean isWebhookMessage;
    private final Command command;
    private final TicketConfig ticketConfig;

    public CommandEvent(GuildMessageReceivedEvent event, Command command) {
        this.event = event;
        this.guild = event.getGuild();
        this.message = event.getMessage();
        this.channel = event.getChannel();
        this.member = event.getMember();
        this.author = event.getAuthor();
        this.jda = event.getJDA();
        this.messageId = event.getMessageId();
        this.messageIdLong = event.getMessageIdLong();
        this.isWebhookMessage = event.isWebhookMessage();
        this.command = command;
        this.ticketConfig = TicketConfig.getInstance(guild);
    }

    public void success(String message) {
        channel.sendMessage(":white_check_mark: **Success**! " + message).queue();
    }

    public void error(String message) {
        channel.sendMessage(":x: **Error**! " + message).queue();
    }

    public void incorrectUsage() throws IncorrectUsageException {
        throw new IncorrectUsageException(this);
    }

    public void incorrectUsage(String message) throws IncorrectUsageException {
        throw new IncorrectUsageException(message, this);
    }

    public String getPrefix() {
        return Constants.getPrefix(guild);
    }

    public String[] getArgs() {
        return message.getContentRaw().split("\\s+");
    }

    public String skipArgs(int amount) {
        String skip = Arrays.stream(getArgs()).skip(amount).collect(Collectors.joining(" "));
        if (skip.isEmpty()) {
            return null;
        }
        return skip;
    }

    public Member getSelfMember() {
        return guild.getSelfMember();
    }

    public User getSelfUser() {
        return getSelfMember().getUser();
    }

    public Guild getGuild() {
        return guild;
    }

    public Member getMember() {
        return member;
    }

    public boolean isWebhookMessage() {
        return isWebhookMessage;
    }

    public Message getMessage() {
        return message;
    }

    public TextChannel getChannel() {
        return channel;
    }

    public User getAuthor() {
        return author;
    }

    public JDA getJDA() {
        return jda;
    }

    public String getMessageId() {
        return messageId;
    }

    public long getMessageIdLong() {
        return messageIdLong;
    }

    public GuildMessageReceivedEvent getEvent() {
        return event;
    }

    public Command getCommand() {
        return command;
    }

    public TicketConfig getTicketConfig() {
        return ticketConfig;
    }
}

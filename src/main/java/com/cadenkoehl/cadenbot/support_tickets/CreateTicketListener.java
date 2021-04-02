package com.cadenkoehl.cadenbot.support_tickets;

import com.cadenkoehl.cadenbot.support_tickets.config.TicketConfig;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class CreateTicketListener extends ListenerAdapter {
    @Override
    public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event) {
        if(event.getUser().isBot()) return;

        Guild guild = event.getGuild();

        TicketConfig config = TicketConfig.getInstance(guild);
        if (config == null) return;

        if(!event.getMessageId().equals(config.getTicketMessage().getId())) return;

        event.getReaction().removeReaction(event.getUser()).queue();

        if(config.getTicket(event.getMember()) != null) {
            event.getUser().openPrivateChannel().queue(channel -> channel.sendMessage(":x: You already created a ticket!").queue());
            return;
        }

        SupportTicket ticket = config.createTicket(event.getMember());
        event.getUser().openPrivateChannel().queue(channel -> {
            channel.sendMessage("**Success**! Ticket " + ticket.getChannel().getAsMention() + " was created!").queue();
        });
    }
}

package com.cadenkoehl.cadenbot.support_tickets;

import com.cadenkoehl.cadenbot.support_tickets.config.TicketConfig;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.List;

public class SupportTicket {

    private final Member member;
    private final Guild guild;
    private TextChannel channel;
    private final int number;
    private String name;

    public SupportTicket(Member member, int number) {
        this.channel = null;
        this.member = member;
        this.guild = member.getGuild();
        this.number = number;
        this.name = "ticket-" + number;
    }

    private SupportTicket(Member member, TextChannel channel, int number, String name) {
        this.channel = channel;
        this.member = member;
        this.guild = member.getGuild();
        this.number = number;
        this.name = name;
    }

    public static SupportTicket fromExisting(Member member, int number, TextChannel channel, String name) {
        return new SupportTicket(member, channel, number, name);
    }

    public void open() {
        this.channel = guild.createTextChannel(this.name)
                .addMemberPermissionOverride(member.getIdLong(), List.of(Permission.VIEW_CHANNEL), List.of(Permission.CREATE_INSTANT_INVITE))
                .addRolePermissionOverride(guild.getPublicRole().getIdLong(), List.of(), List.of(Permission.VIEW_CHANNEL))
                .setParent(TicketConfig.getInstance(guild).getTicketCategory())
                .complete();
    }

    public void close(Member closer, String reason) {

        TicketConfig config = TicketConfig.getInstance(guild);

        String finalMessage = reason == null ? "Support ticket \"" + name + "\" closed by " + closer.getAsMention() : "Support ticket \"" + name + "\" closed by " + closer.getAsMention() + ": " + reason;

        channel.delete().queue();
        member.getUser().openPrivateChannel().queue(channel -> {
            channel.sendMessage(finalMessage).queue();
        });

        if (config == null) return;
        config.deleteTicket(this);
    }

    public Member getMember() {
        return member;
    }

    public TextChannel getChannel() {
        return channel;
    }

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public void rename(String name) {
        this.name = name;
        this.channel.getManager().setName(name).queue();
    }
}
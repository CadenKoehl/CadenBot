package com.cadenkoehl.cadenbot.support_tickets.config;

import com.cadenkoehl.cadenbot.CadenBot;
import com.cadenkoehl.cadenbot.support_tickets.SupportTicket;
import com.cadenkoehl.cadenbot.util.EmbedColor;
import com.cadenkoehl.cadenbot.util.Utils;
import com.cadenkoehl.cadenbot.util.data.Data;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TicketConfig {

    private static final List<TicketConfig> CONFIGS = new ArrayList<>();

    private final Guild guild;
    private TextChannel ticketChannel;
    private Category ticketCategory;
    private EmbedBuilder ticketEmbedBuilder;
    private Message ticketMessage;
    private final List<SupportTicket> tickets;
    private int ticketNumber;

    public TicketConfig(Guild guild) {
        this.guild = guild;
        this.tickets = new ArrayList<>();
        CONFIGS.add(this);
    }

    public void createTicketChannel() {

        if(ticketChannel != null) {
            ticketChannel.delete().queue();
        }
        TextChannel channel = guild.createTextChannel("🎟-support-tickets").addRolePermissionOverride(guild.getPublicRole().getIdLong(), List.of(Permission.VIEW_CHANNEL), List.of(Permission.MESSAGE_WRITE)).complete();
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(EmbedColor.BLUE);
        embed.setTitle("🎟 Support Tickets!");
        embed.setDescription("React with the 🎟 emote to create a support ticket! The staff will be with you ASAP!");
        Message msg = channel.sendMessage(embed.build()).complete();
        msg.addReaction("🎟").queue();
        this.ticketEmbedBuilder = embed;
        this.ticketMessage = msg;
        this.ticketChannel = channel;

        Data.saveKeyAndValue("tickets/channel", guild.getId(), channel.getId());
        Data.saveKeyAndValue("tickets/message", guild.getId(), msg.getId());
    }

    public SupportTicket createTicket(Member member) {
        if(ticketCategory == null || !Utils.exists(ticketCategory)) {
            ticketCategory = guild.createCategory("🎟 Support Tickets").complete();
        }
        ticketNumber++;
        SupportTicket ticket = new SupportTicket(member, ticketNumber);
        ticket.open();
        tickets.add(ticket);

        Data.saveKeyAndValue("tickets/" + guild.getId() + "/" + ticket.getName(), "channel", ticket.getChannel().getId());
        Data.saveKeyAndValue("tickets/" + guild.getId() + "/" + ticket.getName(), "member", ticket.getMember().getId());
        Data.saveKeyAndValue("tickets/" + guild.getId() + "/" + ticket.getName(), "number", String.valueOf(ticket.getNumber()));

        return ticket;
    }

    private void addTicket(SupportTicket ticket) {
        tickets.add(ticket);
    }

    public SupportTicket getTicket(Member member) {
        for(SupportTicket ticket : tickets) {
            if(ticket.getMember().getId().equals(member.getId()) && ticket.getMember().getGuild().getId().equals(guild.getId())) return ticket;
        }
        return null;
    }

    public SupportTicket getTicket(TextChannel channel) {
        for(SupportTicket ticket : tickets) {
            if(ticket.getChannel().getId().equals(channel.getId())) return ticket;
        }
        return null;
    }

    public void deleteTicket(SupportTicket ticket) {
        tickets.remove(ticket);
    }

    public List<SupportTicket> getTickets() {
        return tickets;
    }

    public Guild getGuild() {
        return guild;
    }

    public TextChannel getTicketChannel() {
        return ticketChannel;
    }

    public Category getTicketCategory() {
        return ticketCategory;
    }

    public Message getTicketMessage() {
        return ticketMessage;
    }

    public EmbedBuilder getTicketEmbedBuilder() {
        return ticketEmbedBuilder;
    }

    private void setTicketChannel(TextChannel ticketChannel) {
        this.ticketChannel = ticketChannel;
    }

    private void setTicketMessage(Message ticketMessage) {
        this.ticketMessage = ticketMessage;
    }

    private void setTicketEmbedBuilder(EmbedBuilder ticketEmbedBuilder) {
        this.ticketEmbedBuilder = ticketEmbedBuilder;
    }

    public static TicketConfig getInstance(Guild guild) {
        for(TicketConfig config : CONFIGS) {
            if(config.getGuild().getId().equals(guild.getId())) return config;
        }
        return null;
    }

    public static boolean isTicketChannel(TextChannel channel) {
        Guild guild = channel.getGuild();
        TicketConfig config = TicketConfig.getInstance(guild);
        if(config == null) {
            return false;
        }

        SupportTicket ticket = config.getTicket(channel);
        return ticket != null;
    }

    public static void loadInstances() {
        for(Guild guild : CadenBot.jda.getGuilds()) {
            loadChannel(guild);
            loadMessage(guild);
        }
    }
    private static void loadChannel(Guild guild) {
        List<String> channelID = Data.getStringsFromFile("tickets/channel", guild.getId());
        if(channelID == null || channelID.size() == 0) {
            return;
        }

        TextChannel channel = CadenBot.jda.getTextChannelById(channelID.get(0));
        TicketConfig config = getInstance(guild);
        if (config == null) return;

        config.setTicketChannel(channel);
    }
    private static void loadMessage(Guild guild) {
        List<String> messageID = Data.getStringsFromFile("tickets/message", guild.getId());
        if(messageID == null || messageID.size() == 0) {
            return;
        }
        TicketConfig config = getInstance(guild);
        if (config == null) return;

        TextChannel ticketChannel = config.getTicketChannel();
        if (ticketChannel == null) return;

        Message msg = ticketChannel.getHistory().getMessageById(messageID.get(0));
        if (msg == null) return;

        if(Utils.exists(msg)) {
            config.setTicketMessage(msg);
        }
    }
    private static void loadTickets(Guild guild) {
        File dir = new File("tickets/" + guild.getId() + "/");
        dir.mkdirs();

        for(String ticketName : dir.list()) {
            loadTicket(guild, ticketName);
        }
    }
    private static void loadTicket(Guild guild, String name) {

        String channelID = Data.getValueByKey("tickets/" + guild.getId() + "/" + name, "channel");
        String memberID = Data.getValueByKey("tickets/" + guild.getId() + "/" + name, "member");
        int number = Integer.parseInt(Data.getValueByKey("tickets/" + guild.getId() + "/" + name, "number"));

        TicketConfig config = getInstance(guild);
        if (config == null) return;

        TextChannel channel = CadenBot.jda.getTextChannelById(channelID);
        Member member = guild.getMemberById(memberID);

        config.addTicket(SupportTicket.fromExisting(member, number, channel, name));
    }
}

































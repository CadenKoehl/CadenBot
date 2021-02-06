package com.cadenkoehl.cadenbot.staff.commands.warn;

import com.cadenkoehl.cadenbot.CadenBot;
import com.cadenkoehl.cadenbot.util.Constants;
import com.cadenkoehl.cadenbot.util.EmbedColor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;

public class ClearWarns extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        String guildId = event.getGuild().getId();
        String prefix = Constants.getPrefix(guildId);
        if(args[0].equalsIgnoreCase(prefix + "clearwarns") || args[0].equalsIgnoreCase(prefix + "clearwarn")) {
            if (event.isWebhookMessage()) {
                return;
            }
            if (event.getAuthor().isBot()) {
                return;
            }
            Member mod = event.getMember();
            if (!mod.hasPermission(Permission.BAN_MEMBERS)) {
                event.getChannel().sendMessage(":x: You can't use that!").queue();
                return;
            }
            if (args.length == 1) {
                event.getChannel().sendMessage("**Incomplete Command!**\nUsage: `" + prefix + "clearwarns` `@user`").queue();
                return;
            }
            List<Member> mentionedMembers = event.getMessage().getMentionedMembers();
            if (mentionedMembers.size() == 0) {
                event.getChannel().sendMessage("**Incomplete Command!**\nUsage: `" + prefix + "clearwarns` `@user`").queue();
                return;
            }
            Member member = mentionedMembers.get(0);
            String memberId = member.getId();
            File dir = new File(CadenBot.dataDirectory + "warns/members/");
            if(dir.mkdirs()) {
                System.out.println(dir.getPath() + " was successfully created");
            }
            File file = new File(dir, memberId + " " + guildId + ".txt");
            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(EmbedColor.GREEN);
            embed.setAuthor(member.getUser().getAsTag() + "'s warnings were cleared");
            if(file.delete()) {
                event.getChannel().sendMessage(embed.build()).queue();
            }
            else {
                event.getChannel().sendMessage(":x: **" + member.getUser().getAsTag() + "** does not have any warnings!").queue();
            }
        }
    }
}

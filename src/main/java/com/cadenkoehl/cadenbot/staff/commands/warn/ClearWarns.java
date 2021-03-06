package com.cadenkoehl.cadenbot.staff.commands.warn;

import com.cadenkoehl.cadenbot.CadenBot;
import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandCategory;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandEvent;
import com.cadenkoehl.cadenbot.util.EmbedColor;
import com.cadenkoehl.cadenbot.util.exceptions.IncorrectUsageException;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;

import java.io.File;
import java.util.List;

public class ClearWarns extends Command {
    @Override
    public void execute(CommandEvent event) throws IncorrectUsageException {
        String[] args = event.getArgs();
        String prefix = event.getPrefix();
        String guildId = event.getGuild().getId();
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

    @Override
    public String getName() {
        return "clearwarns";
    }

    @Override
    public String getDescription() {
        return "ClearQueue a member's warnings!";
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
        return "clearwarns` `<@member>`";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"clearwarn", "warnclear", "clearwarnings"};
    }
}

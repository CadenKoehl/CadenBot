package com.cadenkoehl.cadenbot.reactionroles;

import com.cadenkoehl.cadenbot.CadenBot;
import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandCategory;
import com.cadenkoehl.cadenbot.util.ExceptionHandler;
import com.cadenkoehl.cadenbot.util.exceptions.IncorrectUsageException;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ReactionRoles extends Command {
    @Override
    public void execute(GuildMessageReceivedEvent event) throws IncorrectUsageException {
        String prefix = this.getPrefix(event);
        String[] args = this.getArgs(event);
        List<TextChannel> channels = event.getMessage().getMentionedChannels();
        List<Role> roles = event.getMessage().getMentionedRoles();

        if(channels.size() == 0) {
            throw new IncorrectUsageException("You must specify a channel!", this, event);
        }

        if(roles.size() == 0) {
            throw new IncorrectUsageException("You must specify a role!", this, event);
        }

        TextChannel channel = channels.get(0);
        Role role = roles.get(0);

        String content = Arrays.stream(args).skip(4).collect(Collectors.joining(" "));

        if(content.isEmpty()) {
            throw new IncorrectUsageException("Please specify the message content!", this, event);
        }

        if(event.getMessage().getEmotes().size() != 0) {
            Emote emote = event.getMessage().getEmotes().get(0);
            channel.sendMessage(content).queue((message -> {
                try {
                    message.addReaction(emote).queue();
                }
                catch (IllegalArgumentException ex) {
                    message.delete().queue();
                    event.getChannel().sendMessage(":x: The emoji must either be a default Discord emoji, or a custom emoji from this server! If you need help, please join the support server! (type " + prefix + "help)").queue();
                }

                File file = new File(CadenBot.dataDirectory + "reactionroles/" + message.getId() + ".txt");
                if(!file.exists()) {
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        ExceptionHandler.sendStackTrace(e);
                    }
                }
                FileWriter write;
                try {
                    write = new FileWriter(file);
                    write.write(role.getId() + " " + emote.getId());
                    write.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }));
        }
        if(event.getMessage().getEmotes().size() == 0) {
            String emote = args[3];
            channel.sendMessage(content).queue((message -> {
                message.addReaction(emote).queue();

                File file = new File(CadenBot.dataDirectory + "reactionroles/" + message.getId() + ".txt");
                if(!file.exists()) {
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        ExceptionHandler.sendStackTrace(e);
                    }
                }
                FileWriter write;
                try {
                    write = new FileWriter(file);
                    write.write(role.getId() + " " + emote);
                    write.close();
                } catch (IOException e) {
                    ExceptionHandler.sendStackTrace(e);
                }
            }));
        }

        event.getMessage().reply("**Success!** Your reaction role was saved in " + channel.getAsMention()).mentionRepliedUser(false).queue();
    }

    @Override
    public String getName() {
        return "reactionrole";
    }

    @Override
    public String getDescription() {
        return "Create unlimited, fully customizable reaction roles for your server!";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.COMMAND;
    }

    @Override
    public Permission getRequiredPermission() {
        return Permission.MANAGE_ROLES;
    }

    @Override
    public String getUsage(String prefix) {
        return "reactionrole` `<#channel>` `<@role>` `<emoji>` `[message content]`";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"rr"};
    }
}

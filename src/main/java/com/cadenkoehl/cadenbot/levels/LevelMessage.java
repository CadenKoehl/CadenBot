package com.cadenkoehl.cadenbot.levels;

import com.cadenkoehl.cadenbot.CadenBot;
import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandCategory;
import com.cadenkoehl.cadenbot.util.Constants;
import com.cadenkoehl.cadenbot.util.exceptions.IncorrectUsageException;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class LevelMessage extends Command {

    public static String getMsg(Guild guild) {
        String msg;
        try {
            File file = new File(CadenBot.dataDirectory + "levels/message/" + guild.getId() + ".txt");
            Scanner scan = new Scanner(file);
            msg = scan.nextLine();
        } catch (Exception ex) {
            return null;
        }
        return msg;
    }

    @Override
    public void execute(GuildMessageReceivedEvent event) throws IncorrectUsageException {
        String[] args = this.getArgs(event);
        Member member = event.getMember();
        if(args.length == 1) {
            event.getChannel().sendMessage(":x: Please specify a message!").queue();
            return;
        }
        String message = Arrays.stream(args).skip(1).collect(Collectors.joining(" "));
        if(!message.contains("{user}")) {
            event.getChannel().sendMessage("Please put {user} in your message! (It must be lower case!) It will be replaced by the name of the user who leveled up!").queue();
            return;
        }
        if(!message.contains("{lvl}")) {
            event.getChannel().sendMessage("Please put {lvl} in your message! (It must be lower case!) It will be replaced by the new level of the user!").queue();
            return;
        }
        try {
            File dir = new File(CadenBot.dataDirectory + "levels/message");
            if(dir.mkdirs()) {
                System.out.println("New directory was created");
            }
            File file = new File(CadenBot.dataDirectory + "levels/message/" + event.getGuild().getId() + ".txt");
            FileWriter write = new FileWriter(file);
            write.write(message);
            write.close();
            event.getChannel().sendMessage(":white_check_mark: **Success**! Your custom leveling message was saved! Here's an example of what it will look like: " +
                    "\n" + message.replace("{user}", member.getAsMention()).replace("{lvl}", "1")).queue();
        } catch (IOException e) {
            event.getChannel().sendMessage(Constants.ERROR_MESSAGE).queue();
            e.printStackTrace();
        }
    }

    @Override
    public String getName() {
        return "levelmsg";
    }

    @Override
    public String getDescription() {
        return "Set a custom leveling message!";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.LEVELS;
    }

    @Override
    public Permission getRequiredPermission() {
        return Permission.MANAGE_SERVER;
    }

    @Override
    public String getUsage(String prefix) {
        return "levelmsg` `[custom message]`";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"lvlmsg", "levelmessage"};
    }
}
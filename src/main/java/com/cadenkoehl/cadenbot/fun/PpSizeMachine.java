package com.cadenkoehl.cadenbot.fun;

import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandCategory;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandEvent;
import com.cadenkoehl.cadenbot.util.exceptions.IncorrectUsageException;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;

import java.util.Random;

public class PpSizeMachine extends Command {
    @Override
    public void execute(CommandEvent event) throws IncorrectUsageException {
        String name;
        try {
            name = event.getMessage().getMentionedMembers().get(0).getEffectiveName();
        }
        catch (IndexOutOfBoundsException ex) {
            name = event.getMember().getEffectiveName();
        }
        Random random = new Random();
        String[] pps = {
                "8=D",
                "8==D",
                "8===D",
                "8====D",
                "8====D",
                "8=====D",
                "8======D",
                "8=======D",
                "8========D",
                "8==========D",
                "8=============D",
                "8==============D",
                "8===============D",
                "A fatal error has occurred: `Cannot calculate PeePee size because " + name + "'s Pee Pee is too small!`",
                "A fatal error has occurred: `Cannot calculate PeePee size because PeePee is null (" + name + " is not a guy)`"
        };
        int pp = random.nextInt(pps.length);
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle(name + "'s PeePee");
        embed.setDescription(pps[pp]);
        embed.setColor((int) Math.round(Math.random() * 999999));
        event.getChannel().sendMessage(embed.build()).queue();
    }

    @Override
    public String getName() {
        return "pp";
    }

    @Override
    public String getDescription() {
        return "See you or your friend's pp size!";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.FUN;
    }

    @Override
    public Permission getRequiredPermission() {
        return null;
    }

    @Override
    public String getUsage(String prefix) {
        return "pp` `<@member>`";
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }
}

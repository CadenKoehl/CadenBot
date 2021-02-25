package com.cadenkoehl.cadenbot.fun.hangman;

import com.cadenkoehl.cadenbot.CadenBot;
import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandCategory;
import com.cadenkoehl.cadenbot.util.Constants;
import com.cadenkoehl.cadenbot.util.exceptions.IncorrectUsageException;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Hangman extends Command {

    @Override
    public void execute(GuildMessageReceivedEvent event) throws IncorrectUsageException {
        String[] words = Constants.getHangmanWords();

        Random random = new Random();
        int word = random.nextInt(words.length);

        String channelId = event.getChannel().getId();

        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Hangman!");
        StringBuilder desc = new StringBuilder("Word: ");
        desc.append("-".repeat(Math.max(0, words[word].length())));
        embed.setDescription(desc);
        embed.setColor((int) Math.round(Math.random() * 999999));
        embed.setImage(Constants.HANGMAN[0]);
        embed.addField("How to play", "There is a random word up above, and you have to guess what it is! " +
                "You can guess a letter by replying to this message with your guess! " +
                "If the letter is correct, I fill it in in the word. If it is incorrect, I draw one body part of the hangman. If he is fully completed, I win, " +
                "if you fill in the word first, you win. Good luck.", false);
        event.getChannel().sendMessage(embed.build()).queue((message -> {
            try {
                File channelFile = new File(CadenBot.dataDirectory + "hangman/" + channelId + " " + message.getId() + ".txt");
                FileWriter write = new FileWriter(channelFile);
                write.write("0");
                write.close();

                File wordFile = new File(CadenBot.dataDirectory + "hangman/word/" + channelId + " " + message.getId() + ".txt");
                FileWriter wordWriter = new FileWriter(wordFile);
                wordWriter.write(words[word]);
                wordWriter.close();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }));
    }

    @Override
    public String getName() {
        return "hangman";
    }

    @Override
    public String getDescription() {
        return "Play hangman!";
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
        return "hangman`";
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }
}

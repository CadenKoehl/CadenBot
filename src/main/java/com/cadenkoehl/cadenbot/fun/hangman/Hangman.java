package com.cadenkoehl.cadenbot.fun.hangman;

import com.cadenkoehl.cadenbot.CadenBot;
import com.cadenkoehl.cadenbot.util.Constants;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Hangman extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        String prefix = Constants.getPrefix(event.getGuild().getId());
        if(args[0].equalsIgnoreCase(prefix + "hangman")) {
            String channelId = event.getChannel().getId();
            if(event.isWebhookMessage()) {
                return;
            }
            Member member = event.getMember();
            User user = event.getAuthor();
            if(user.isBot()) {
                return;
            }
            String[] words = Constants.getHangmanWords(member.getEffectiveName());

            Random random = new Random();
            int word = random.nextInt(words.length);

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
    }
}

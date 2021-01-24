package com.cadenkoehl.cadenbot.fun.hangman;

import com.cadenkoehl.cadenbot.CadenBot;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Guess extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        if(event.getMessage().getReferencedMessage() == null) {
            return;
        }
        Message referencedMessage = event.getMessage().getReferencedMessage();
        String messageId = referencedMessage.getId();
        String channelId = event.getChannel().getId();
        File file = new File(CadenBot.dataDirectory + "hangman/word/" + channelId + " " + messageId + ".txt");
        if(!file.exists()) {
            return;
        }
        if(args[0].length() != 1) {
            event.getChannel().sendMessage("Please only guess one letter at a time!").queue();
            return;
        }
        try {
            Scanner scan = new Scanner(file);
            String word = scan.nextLine();
            if(word.contains(args[0].toLowerCase())) {
                HangmanUtil.fillInLetter(referencedMessage, args[0], word);
                event.getMessage().delete().queue();
            }
            if(!word.contains(args[0].toLowerCase())) {
                HangmanUtil.incorrectGuess(referencedMessage, args[0]);
                event.getMessage().delete().queue();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
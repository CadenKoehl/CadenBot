package com.cadenkoehl.cadenbot.fun.hangman;

import com.cadenkoehl.cadenbot.CadenBot;
import com.cadenkoehl.cadenbot.util.Constants;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class HangmanUtil {
    public static void incorrectGuess(Message hangmanMessage, String letter) {
        String messageId = hangmanMessage.getId();
        String channelId = hangmanMessage.getChannel().getId();
        try {
            File file = new File(CadenBot.dataDirectory + "hangman/" + channelId + " " + messageId + ".txt");
            Scanner scan = new Scanner(file);
            String phaseRaw = scan.nextLine();
            int phase = Integer.parseInt(phaseRaw);
            hangmanMessage.getChannel().sendMessage("Nice try, but the word does not contain \"" + letter + "\", so I drew a part of the hangman!").queue();
            phase++;
            FileWriter write = new FileWriter(file);
            write.write(String.valueOf(phase));
            write.close();
            MessageEmbed oldEmbed = hangmanMessage.getEmbeds().get(0);
            String title = oldEmbed.getTitle();
            String desc = oldEmbed.getDescription();
            Color color = oldEmbed.getColor();
            String fieldName = oldEmbed.getFields().get(0).getName();
            String fieldValue = oldEmbed.getFields().get(0).getValue();

            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle(title);
            embed.setDescription(desc);
            embed.addField(fieldName, fieldValue, false);
            embed.setImage(Constants.HANGMAN[phase]);
            embed.setColor(color);
            hangmanMessage.editMessage(embed.build()).queue();
            if (phase == 6) {
                if(file.delete()) {
                    File wordFile = new File(CadenBot.dataDirectory + "hangman/word/" + channelId + " " + messageId + ".txt");
                    Scanner wordScan = new Scanner(wordFile);
                    String word = wordScan.nextLine();
                    hangmanMessage.getChannel().sendMessage("The hangman was completed and I won! gg! The word was \"" + word + "\"! Better luck next time :sweat_smile:").queue();
                    wordFile.delete();
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void fillInLetter(Message hangmanMsg, String letter, String word) {
        letter = letter.toLowerCase();
        MessageEmbed oldEmbed = hangmanMsg.getEmbeds().get(0);
        String title = oldEmbed.getTitle();
        String oldDesc = oldEmbed.getDescription();
        String image = oldEmbed.getImage().getUrl();
        String fieldName = oldEmbed.getFields().get(0).getName();
        String fieldValue = oldEmbed.getFields().get(0).getValue();

        String[] desc = oldDesc.split("\\s+");

        int index = word.indexOf(letter);
        char[] wordChars = desc[1].toCharArray();

        if(wordChars[index] != letter.toCharArray()[0]) {
            wordChars[index] = letter.toCharArray()[0];
        }
        if(wordChars[index] == letter.toCharArray()[0]) {
            index = word.lastIndexOf(letter);
            wordChars[index] = letter.toCharArray()[0];
        }
        desc[1] = String.valueOf(wordChars);

        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle(title);
        embed.setImage(image);
        embed.setDescription("Word: " + desc[1]);
        embed.addField(fieldName, fieldValue, false);
        hangmanMsg.editMessage(embed.build()).queue();
        hangmanMsg.getChannel().sendMessage("Good job! \"" + letter + "\" is correct!").queue();
        if(!desc[1].contains("-")) {
            hangmanMsg.getChannel().sendMessage("Yay you won! gg! :partying_face:").queue();
        }
    }
}

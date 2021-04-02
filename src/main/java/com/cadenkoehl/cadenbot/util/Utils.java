package com.cadenkoehl.cadenbot.util;

import com.cadenkoehl.cadenbot.CadenBot;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

public class Utils {

    /**
     * @param string Takes in a string
     * @return The given string, but with a capitalized first letter
     */
    public static String capFirstLetter(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }
    public static void dmUser(User user, String message) {
        user.openPrivateChannel().queue(channel -> channel.sendMessage(message).queue());
    }
    public static boolean exists(Category category) {
        return CadenBot.jda.getCategoryById(category.getId()) != null;
    }
    public static boolean exists(TextChannel channel) {
        return CadenBot.jda.getTextChannelById(channel.getId()) != null;
    }
    public static boolean exists(Message message) {
        return CadenBot.jda.getTextChannelById(message.getChannel().getId()).getHistory().getMessageById(message.getId()) != null;
    }
}
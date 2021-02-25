package com.cadenkoehl.cadenbot.util;

public class Utils {

    /**
     * @param string Takes in a string
     * @return The given string, but with a capitalized first letter
     */
    public static String capFirstLetter(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }
}
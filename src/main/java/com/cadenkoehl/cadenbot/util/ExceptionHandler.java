package com.cadenkoehl.cadenbot.util;

import com.cadenkoehl.cadenbot.CadenBot;

public class ExceptionHandler {

    public static void sendStackTrace(Throwable throwable) {
        throwable.printStackTrace();
        StackTraceElement[] stackTrace = throwable.getStackTrace();
        String stringTrace = throwable.toString() + "\n";

        for(StackTraceElement element : stackTrace) {
            if(!element.getClassName().contains("com.cadenkoehl")) continue;
            String elementString = element.getClassName() + "." + element.getMethodName() + " " + element.getLineNumber() + "\n";
            stringTrace = stringTrace + elementString;
        }

        CadenBot.jda.getTextChannelById(Constants.CADENBOTBUGSCHANNEL).sendMessage(String.format(":x: **An Uncaught Exception Has Occurred**\n```java\n%s\n```\n[<@%s>]", stringTrace, Constants.CadenID)).queue();
    }
}

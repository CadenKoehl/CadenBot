package com.cadenkoehl.cadenbot.util.logging;

public class Logger {

    private Logger() {}

    public static void info(String message) {
        log("INFO", message);
    }

    public static <T> void info(String message, Class<T> clazz) {
        log("INFO", message, clazz);
    }

    public static void warn(String message) {
        log("WARNING", message);
    }

    public static <T> void warn(String message, Class<T> clazz) {
        log("WARNING", message, clazz);
    }

    public static void error(String message) {
        log("ERROR", message);
    }

    public static <T> void error(String message, Class<T> clazz) {
        log("ERROR", message, clazz);
    }

    public static <T> void error(Throwable ex) {
        log("ERROR", ex.toString());
    }

    private static void log(String level, String message) {
        String thread = Thread.currentThread().getName();
        System.err.printf("[%s/%s]: %s %s", thread, level, message, "\n");
    }
    private static <T> void log(String level, String message, Class<T> clazz) {
        String thread = Thread.currentThread().getName();
        System.err.printf("[%s/%s] %s - %s %s", thread, clazz.getSimpleName(), level, message, "\n");
    }
}

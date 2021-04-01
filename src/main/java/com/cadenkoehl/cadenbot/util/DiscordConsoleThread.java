package com.cadenkoehl.cadenbot.util;

import com.cadenkoehl.cadenbot.util.logging.Logger;

public class DiscordConsoleThread extends Thread {
    @Override
    public void run() {
        this.setName("Discord console thread");
        Logger.info("Finished loading!");

    }
}

package com.cadenkoehl.cadenbot.staff.commands.suggest;

import net.dv8tion.jda.api.entities.Member;

public interface Suggestion {
    String getContent();
    Member getMember();
    String getMessageURL();
    int getNumber();
}
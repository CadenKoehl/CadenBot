package com.cadenkoehl.cadenbot.rpg.util;

import java.util.List;

public interface Player {
    String getName();
    long getMoney();
    double getHealth();
    List<String> getInventory();
    int getInventorySize();
}
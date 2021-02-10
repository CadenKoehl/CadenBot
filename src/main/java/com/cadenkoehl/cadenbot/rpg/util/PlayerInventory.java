package com.cadenkoehl.cadenbot.rpg.util;

import java.util.List;

public interface PlayerInventory {
    List<String> getItems();
    int getMaxSize();

    default int getSize() {
        return this.getItems().size();
    }
}
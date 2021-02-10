package com.cadenkoehl.cadenbot.dms;

import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class DmReactions extends ListenerAdapter {
    //Fun easter egg!
    @Override
    public void onPrivateMessageReactionAdd(@NotNull PrivateMessageReactionAddEvent event) {
        MessageReaction.ReactionEmote reactionEmote = event.getReaction().getReactionEmote();
        if(reactionEmote.isEmoji()) {
            String emoji = reactionEmote.getEmoji();
            for(int i = 1; i <= 3; i++) {
                event.getChannel().sendMessage(emoji).queue();
            }
        }
    }
}

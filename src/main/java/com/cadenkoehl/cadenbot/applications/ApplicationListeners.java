package com.cadenkoehl.cadenbot.applications;

import com.cadenkoehl.cadenbot.util.Emoji;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class ApplicationListeners extends ListenerAdapter {

    @Override
    public void onPrivateMessageReactionAdd(@NotNull PrivateMessageReactionAddEvent event) {
        ApplicationManager manager = new ApplicationManager();
        Application application = manager.getApplicationByApplicant(event.getUser());
        if(application == null) return;
        if (!event.getReactionEmote().isEmoji()) return;

        String emoji = event.getReactionEmote().getEmoji();

        int state = manager.getApplicationState(event.getUser());

        if(state != -1) return;

        if(emoji.equals(Emoji.WHITE_CHECK_MARK)) {
            manager.sendQuestion(event.getUser());
        }
        if(emoji.equals(Emoji.X)) {
            event.getChannel().sendMessage("Application was cancelled.").queue();
            application.close(event.getUser());
        }
    }

    @Override
    public void onPrivateMessageReceived(@NotNull PrivateMessageReceivedEvent event) {
        ApplicationManager manager = new ApplicationManager();
        User user = event.getAuthor();
        if(event.getJDA().getSelfUser() == user) return;
        Application application = manager.getApplicationByApplicant(user);
        int state = manager.getApplicationState(user);

        if(application == null) return;

        if(state < 0) return;

        manager.recordResponse(user, event.getMessage().getContentRaw());
        manager.sendQuestion(user);
    }
}
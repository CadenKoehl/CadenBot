package com.cadenkoehl.cadenbot.commands.custom_commands;

import com.cadenkoehl.cadenbot.CadenBot;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandHandler;
import com.cadenkoehl.cadenbot.util.data.Data;
import com.cadenkoehl.cadenbot.util.logging.Logger;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CustomCommandFactory {

    private static final ArrayList<CustomCommandFactory> CURRENT_INSTANCES = new ArrayList<>();

    private final TextChannel channel;
    private final Member member;
    private final CustomCommand command;
    private State state;

    public CustomCommandFactory(TextChannel channel, Member member) {
        this.channel = channel;
        this.member = member;
        this.command = new CustomCommand(channel.getGuild());
        this.state = State.NAME;
        CURRENT_INSTANCES.add(this);
    }

    public static CustomCommandFactory get(TextChannel channel, Member member) {
        for(CustomCommandFactory factory : CURRENT_INSTANCES) {
            if(factory.getChannel().getId().equals(channel.getId()) && factory.getMember().getId().equals(member.getId())) {
                return factory;
            }
        }
        return null;
    }

    public static void loadCommands() {

        int loadedCommands = 0;

        for(Guild guild : CadenBot.jda.getGuilds()) {
            File dir = new File(CadenBot.dataDirectory + "custom_commands/" + guild.getId());
            dir.mkdirs();

            for(String fileName : dir.list()) {
                String cmdName = fileName.replace(".txt", "");
                List<String> cmdElements = Data.getStringsFromFile(dir, cmdName);

                if(cmdElements == null) continue;

                CustomCommand command = new CustomCommand(guild, cmdName, cmdElements.get(0), cmdElements.get(1));
                CommandHandler.commands.add(command);
                loadedCommands++;
            }
        }

        Logger.info(loadedCommands + " custom commands were successfully loaded", CustomCommandFactory.class);
    }

    public TextChannel getChannel() {
        return channel;
    }

    public Member getMember() {
        return member;
    }

    public State getState() {
        return state;
    }

    public State nextState(String lastResponse) {

        if(this.state == State.NAME) command.setName(lastResponse);
        if(this.state == State.DESC) command.setDescription(lastResponse + " (custom command)");
        if(this.state == State.RESPONSE) command.setCommandResponse(lastResponse);

        State state = State.NONE;

        switch (this.state) {
            case NAME -> state = State.DESC;
            case DESC -> state = State.RESPONSE;
        }

        this.setState(state);
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public CustomCommand build() {
        if(command.getName() == null || command.getDescription() == null || command.getResponse() == null) {
            throw new IllegalArgumentException("Command elements must not be null!");
        }
        command.save();
        return command;
    }

    public void delete() {
        CURRENT_INSTANCES.remove(this);
    }

    /**
     * Setting that the CustomCommandFactory is listening for
     */
    public enum State {
        NONE,
        NAME,
        DESC,
        RESPONSE
    }
}

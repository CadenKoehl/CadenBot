package com.cadenkoehl.cadenbot.commands.custom_commands;

import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandCategory;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandEvent;
import com.cadenkoehl.cadenbot.util.data.Data;
import com.cadenkoehl.cadenbot.util.exceptions.IncorrectUsageException;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;

public class CustomCommand extends Command {

    private final Guild guild;
    private String name;
    private String description;
    private String response;


    public CustomCommand(Guild guild) {
        this.guild = guild;
        this.name = null;
        this.description = null;
        this.response = null;
    }

    public CustomCommand(Guild guild, String name, String description, String response) {
        this.guild = guild;
        this.name = name;
        this.description = description;
        this.response = response;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCommandResponse(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    public Guild getGuild() {
        return guild;
    }

    public void save() {
        Data.appendToFile("custom_commands/" + guild.getId(), name, description, response);
    }

    @Override
    public void execute(CommandEvent event) throws IncorrectUsageException {
        if(!event.getGuild().getId().equals(guild.getId())) {
            return;
        }
        event.getChannel().sendMessage(response).queue();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.COMMAND;
    }

    @Override
    public Permission getRequiredPermission() {
        return null;
    }

    @Override
    public String getUsage(String prefix) {
        return name + "`";
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }
}

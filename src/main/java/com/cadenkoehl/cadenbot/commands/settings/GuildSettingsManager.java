package com.cadenkoehl.cadenbot.commands.settings;

import com.cadenkoehl.cadenbot.CadenBot;
import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.util.ExceptionHandler;
import net.dv8tion.jda.api.entities.Guild;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class GuildSettingsManager {

    private final Guild guild;

    public GuildSettingsManager(Guild guild) {
        this.guild = guild;
    }

    public void toggleCommandOff(Command cmd) {
        File dir = new File(CadenBot.dataDirectory + "toggled_commands");

        if(dir.mkdirs()) System.out.println("Successfully created directory " + dir.getName());

        File file = new File(dir, guild.getId() + ".txt");

        try {
            FileWriter write = new FileWriter(file, true);
            write.write("\n" + cmd.getName());
            write.close();

        } catch (IOException e) {
            ExceptionHandler.sendStackTrace(e);
        }
    }

    public void toggleCommandOn(Command cmd) {
        File dir = new File(CadenBot.dataDirectory + "toggled_commands");

        if(dir.mkdirs()) System.out.println("Successfully created directory " + dir.getName());

        File file = new File(dir, guild.getId() + ".txt");

        List<String> cmdNames = this.getToggledCommandNames();

        cmdNames.remove(cmd.getName());

        try {
            FileWriter write = new FileWriter(file);
            for(String cmdName : cmdNames) write.write(cmdName);
            write.close();

        } catch (IOException e) {
            ExceptionHandler.sendStackTrace(e);
        }
    }

    public List<Command> getToggledCommands() {
        List<Command> toggledCmds = new ArrayList<>();

        for(String cmdName : this.getToggledCommandNames()) {
            Command cmd = Command.getByName(cmdName);
            if(cmd == null) continue;

            toggledCmds.add(cmd);
        }
        return toggledCmds;
    }

    public List<String> getToggledCommandNames() {
        File dir = new File(CadenBot.dataDirectory + "toggled_commands");

        if(dir.mkdirs()) System.out.println("Successfully created directory " + dir.getName());

        File file = new File(dir, guild.getId() + ".txt");

        Scanner scan;

        try {
            scan = new Scanner(file);
        } catch (FileNotFoundException e) {
            return Collections.emptyList();
        }

        List<String> toggledCmds = new ArrayList<>();

        while(scan.hasNextLine()) toggledCmds.add(scan.nextLine());

        return toggledCmds;

    }

    public boolean isToggledOff(Command cmd) {
        List<String> cmdNames = this.getToggledCommandNames();
        return cmdNames.contains(cmd.getName());
    }
}

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

public class SettingsManager {

    public void toggleCommandOff(Command cmd, Guild guild) {
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

    public void toggleCommandOn(Command cmd, Guild guild) {
        File dir = new File(CadenBot.dataDirectory + "toggled_commands");

        if(dir.mkdirs()) System.out.println("Successfully created directory " + dir.getName());

        File file = new File(dir, guild.getId() + ".txt");

        List<String> cmdNames = this.getToggledCommandNames(guild);

        cmdNames.remove(cmd.getName());

        try {
            FileWriter write = new FileWriter(file);
            for(String cmdName : cmdNames) write.write(cmdName);
            write.close();

        } catch (IOException e) {
            ExceptionHandler.sendStackTrace(e);
        }
    }

    public List<Command> getToggledCommands(Guild guild) {
        List<Command> toggledCmds = new ArrayList<>();

        for(String cmdName : this.getToggledCommandNames(guild)) {
            Command cmd = Command.getByName(cmdName);
            if(cmd == null) continue;

            toggledCmds.add(cmd);
        }
        return toggledCmds;
    }

    public List<String> getToggledCommandNames(Guild guild) {
        File dir = new File(CadenBot.dataDirectory + "toggled_commands");

        if(dir.mkdirs()) System.out.println("Successfully created directory " + dir.getName());

        File file = new File(dir, guild.getId() + ".txt");

        Scanner scan;

        try {
            scan = new Scanner(file);
        } catch (FileNotFoundException e) {
            ExceptionHandler.sendStackTrace(e);
            return Collections.emptyList();
        }

        List<String> toggledCmds = new ArrayList<>();

        while(scan.hasNextLine()) toggledCmds.add(scan.nextLine());

        return toggledCmds;

    }

    public boolean isToggled(Command cmd, Guild guild) {
        List<String> cmdNames = this.getToggledCommandNames(guild);
        return cmdNames.contains(cmd.getName());
    }
}

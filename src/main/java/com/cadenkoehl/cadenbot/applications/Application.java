package com.cadenkoehl.cadenbot.applications;

import com.cadenkoehl.cadenbot.CadenBot;
import com.cadenkoehl.cadenbot.util.ExceptionHandler;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public interface Application {

    String getName();
    Guild getGuild();
    File getFile();
    List<String> getQuestions();

    /**
     *
     * @param member The applicant to start the application with
     * @return true if it begins successfully, false if the bot cannot message the user
     */
    default boolean start(Member member) {
        File guildDir = new File(CadenBot.dataDirectory + "applications/applicants/" + member.getGuild().getId() + "/");
        if(guildDir.mkdirs()) System.out.println("Successfully created directory " + guildDir.getPath());

        File memberDir = new File(CadenBot.dataDirectory + "applications/applicants/" + member.getId() + "/");
        if(memberDir.mkdirs()) System.out.println("Successfully created directory " + memberDir.getPath());

        File guildFile = new File(guildDir, member.getId() + ".json");
        File memberFile = new File(memberDir, member.getId() + ".json");
        try {
            FileWriter guildWrite = new FileWriter(guildFile);
            JSONObject guildJsonObject = new JSONObject();
            guildJsonObject.put("app_name", this.getName());
            guildJsonObject.put("guild", member.getGuild().getId());
            guildJsonObject.put("state", -1);
            guildWrite.write(guildJsonObject.toString(4));
            guildWrite.close();

            FileWriter memberWrite = new FileWriter(memberFile);
            JSONObject memberJsonObject = new JSONObject();
            memberJsonObject.put("app_name", this.getName());
            memberJsonObject.put("guild", member.getGuild().getId());
            memberWrite.write(memberJsonObject.toString(4));
            memberWrite.close();
        }
        catch (IOException ex) {
            ExceptionHandler.sendStackTrace(ex);
        }
        try {
            member.getUser().openPrivateChannel().queue(channel ->
                    channel.sendMessage("Are you sure you want to apply for the \"" + this.getName() + "\" application on **" +  member.getGuild().getName() + "**?").queue(message -> {
                message.addReaction("✅").queue();
                message.addReaction("❌").queue();
            }));
        }
        catch (Exception ex) {
            return false;
        }
        return true;
    }

    default void close(User user) {

        Member member = this.getGuild().getMember(user);

        File guildDir = new File(CadenBot.dataDirectory + "applications/applicants/" + this.getGuild().getId() + "/");
        File memberDir = new File(CadenBot.dataDirectory + "applications/applicants/" + member.getId() + "/");
        if (guildDir.delete()) System.out.println("Deleted directory");
        if (memberDir.delete()) System.out.println("Deleted directory");
    }
}
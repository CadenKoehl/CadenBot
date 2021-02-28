package com.cadenkoehl.cadenbot.applications;

import com.cadenkoehl.cadenbot.CadenBot;
import com.cadenkoehl.cadenbot.util.EmbedColor;
import com.cadenkoehl.cadenbot.util.ExceptionHandler;
import com.cadenkoehl.cadenbot.util.data.Data;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

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

    default void finish(User user) {

        ApplicationManager manager = new ApplicationManager();
        Guild guild = this.getGuild();
        TextChannel channel = manager.getApplicationChannel(guild);
        Member member = guild.getMember(user);
        List<String> questions = this.getQuestions();
        Data data = new Data();

        File guildFile = new File(CadenBot.dataDirectory + "applications/applicants/" + guild.getId() + "/" + member.getId() + ".json");
        File memberFile = new File(CadenBot.dataDirectory + "applications/applicants/" + member.getId() + "/" + member.getId() + ".json");
        if (guildFile.delete()) System.out.println("Deleted directory");
        if (memberFile.delete()) System.out.println("Deleted directory");



        JSONObject jsonObject = data.getJSONObjectFromFile(CadenBot.dataDirectory + "applications/responses/" + user.getId() + ".json");
        Object[] responses = jsonObject.getJSONArray("responses").toList().toArray();


        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(EmbedColor.GREEN);
        embed.setAuthor(user.getAsTag() + " submitted an application! (" + this.getName() + ")", null, user.getEffectiveAvatarUrl());

        for(int i = 0, j = 0; i < responses.length && j < questions.size(); i++, j++) {
            embed.addField(questions.get(j), responses[i].toString(), false);
        }

        Role pingRole = manager.getPingRole(guild);
        String roleMention = "";

        if(pingRole != null) roleMention = "[" + pingRole.getAsMention() + "]";

        channel.sendMessage(embed.build()).append("\n" + roleMention).queue(message -> {
            message.addReaction("✅").queue();
            message.addReaction("❌").queue();
        });

        File file = new File(CadenBot.dataDirectory + "applications/responses/" + user.getId() + ".json");
        if(file.delete()) System.out.println("File was deleted");
    }
}
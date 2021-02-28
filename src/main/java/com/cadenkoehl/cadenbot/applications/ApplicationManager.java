package com.cadenkoehl.cadenbot.applications;

import com.cadenkoehl.cadenbot.CadenBot;
import com.cadenkoehl.cadenbot.util.EmbedColor;
import com.cadenkoehl.cadenbot.util.ExceptionHandler;
import com.cadenkoehl.cadenbot.util.data.Data;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ApplicationManager {

    /**
     *
     * @param guild Guild of the application
     * @param appName Name of the application
     * @return true if the application was created successfully, false if an application with the same name already exists
     */
    public boolean createApplication(Guild guild, String appName) {
        Data data = new Data();
        File dir = new File(CadenBot.dataDirectory + "applications/" + guild.getId());
        if(dir.mkdirs()) System.out.println("Successfully created directory " + dir.getPath());
        File file = new File(dir, appName + ".json");
        if(file.exists()) return false;
        data.saveDataToJSONFile(file, new JSONObject("{\n" +
                "  \"name\": " + appName + ",\n" +
                "  \"questions\": [\n" +
                "    \n" +
                "  ]\n" +
                "}"));
        return true;
    }

    /**
     * Adds another question to an existing application
     * @param guild Guild of the application
     * @param appName Name of the application
     * @param content Content of the question
     */
    public void addQuestion(Guild guild, String appName, String content) {
        Application app = this.getApplication(guild, appName);
        Data data = new Data();
        JSONObject jsonObject = data.getJSONObjectFromFile(app.getFile().getPath());
        JSONArray questions = jsonObject.getJSONArray("questions");
        questions.put(content);
        String questionsString = questions.toString();

        String jsonString = "{\n" +
                "  \"name\": " + appName + ",\n" +
                "  \"questions\": " + questionsString + "\n" +
                "}";

        try {
            FileWriter write = new FileWriter(app.getFile());
            write.write(jsonString);
            write.close();
        }
        catch (IOException ex) {
            ExceptionHandler.sendStackTrace(ex);
            ex.printStackTrace();
        }

    }

    public void removeQuestion(Guild guild, String appName, int index) {
        Application app = this.getApplication(guild, appName);
        Data data = new Data();
        JSONObject jsonObject = data.getJSONObjectFromFile(app.getFile().getPath());
        JSONArray questions = jsonObject.getJSONArray("questions");
        questions.remove(index);
        String questionsString = questions.toString();

        String jsonString = "{\n" +
                "  \"name\": " + appName + ",\n" +
                "  \"questions\": " + questionsString + "\n" +
                "}";

        try {
            FileWriter write = new FileWriter(app.getFile());
            write.write(jsonString);
            write.close();
        }
        catch (IOException ex) {
            ExceptionHandler.sendStackTrace(ex);
            ex.printStackTrace();
        }
    }

    public Application getApplication(Guild guild, String name) {
        Data data = new Data();
        File file = new File(CadenBot.dataDirectory + "applications/" + guild.getId() + "/" + name + ".json");
        if(!file.exists()) return null;
        JSONObject jsonObject = data.getJSONObjectFromFile(file.getPath());
        if(jsonObject.toString().equals("{}")) return null;
        return new Application() {
            @Override
            public String getName() {
                return jsonObject.getString("name");
            }

            @Override
            public Guild getGuild() {
                return guild;
            }

            @Override
            public File getFile() {
                return file;
            }

            @Override
            public List<String> getQuestions() {
                JSONArray questions = jsonObject.getJSONArray("questions");
                List<String> finalQuestions = new ArrayList<>();
                List<Object> objects = questions.toList();
                for (Object object : objects) {
                    finalQuestions.add(object.toString());
                }
                return finalQuestions;
            }
        };
    }

    public int getApplicationState(User user) {
        Application application = this.getApplicationByApplicant(user);
        if(application == null) return -2;

        File dir = new File(CadenBot.dataDirectory + "applications/applicants/" + application.getGuild().getId() + "/");
        if(dir.mkdirs()) System.out.println("Successfully created directory " + dir.getPath());

        File file = new File(dir, user.getId() + ".json");
        if(!file.exists()) return -2;

        Data data = new Data();
        JSONObject jsonObject = data.getJSONObjectFromFile(file.getPath());
        return jsonObject.getInt("state");
    }

    private void incrementApplicationState(User user) {
        Application application = this.getApplicationByApplicant(user);
        if(application == null) return;

        File dir = new File(CadenBot.dataDirectory + "applications/applicants/" + application.getGuild().getId() + "/");
        if(dir.mkdirs()) System.out.println("Successfully created directory " + dir.getPath());

        File file = new File(dir, user.getId() + ".json");
        if(!file.exists()) return;

        Data data = new Data();
        JSONObject jsonObject = data.getJSONObjectFromFile(file.getPath());
        JSONObject incrementedState = jsonObject.increment("state");

        try {
            FileWriter write = new FileWriter(file);
            write.write(incrementedState.toString(4));
            write.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param applicant The applicant
     * @return The application of the applicant, null if the user is not currently taking an application
     */
    public Application getApplicationByApplicant(User applicant) {

        Data data = new Data();

        File dir = new File(CadenBot.dataDirectory + "applications/applicants/" + applicant.getId() + "/");
        if(dir.mkdirs()) System.out.println("Successfully created directory " + dir.getPath());

        File file = new File(dir, applicant.getId() + ".json");
        if(!file.exists()) return null;

        JSONObject applicantData = data.getJSONObjectFromFile(file.getPath());

        String appName = applicantData.getString("app_name");
        Guild guild = CadenBot.jda.getGuildById(applicantData.getString("guild"));

        return this.getApplication(guild, appName);
    }

    public TextChannel getApplicationChannel(Guild guild) {
        File dir = new File(CadenBot.dataDirectory + "applications/channel/");
        if(dir.mkdirs()) System.out.println(dir.getPath() + " was created!");

        File file = new File(dir, guild.getId() + ".txt");

        Scanner scan;

        try {
            scan = new Scanner(file);
        }
        catch (FileNotFoundException ex) {
            System.out.println("File not found: returning null");
            return null;
        }
        return guild.getTextChannelById(scan.nextLine());
    }

    public void setApplicationChannel(TextChannel channel) {
        Guild guild = channel.getGuild();
        File dir = new File(CadenBot.dataDirectory + "applications/channel/");
        if(dir.mkdirs()) System.out.println(dir.getPath() + " was created!");

        File file = new File(dir, guild.getId() + ".txt");

        try {
            FileWriter write = new FileWriter(file);
            write.write(channel.getId());
            write.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendQuestion(User applicant) {
        Application application = this.getApplicationByApplicant(applicant);
        this.incrementApplicationState(applicant);
        String question;
        try {
            question = application.getQuestions().get(this.getApplicationState(applicant));
        }
        catch (IndexOutOfBoundsException ex) {
            applicant.openPrivateChannel().queue(channel -> {
                channel.sendMessage(":white_check_mark: **Done**! Your response has been recorded!").queue();
                application.finish(applicant);
            });
            return;
        }
        try {
            applicant.openPrivateChannel().queue(channel -> {
                EmbedBuilder embed = new EmbedBuilder();
                embed.setAuthor(question, null, application.getGuild().getIconUrl());
                embed.setColor(EmbedColor.GREEN);
                channel.sendMessage(embed.build()).queue();
            });
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void recordResponse(User user, String response) {
        Data data = new Data();
        Application application = this.getApplicationByApplicant(user);
        Guild guild = application.getGuild();

        File dir = new File(CadenBot.dataDirectory + "applications/responses/");
        if(dir.mkdirs()) System.out.println("Created directory " + dir.getPath());

        File file = new File(dir, user.getId() + ".json");

        try {
            if(file.createNewFile()) {
                FileWriter write = new FileWriter(file);
                write.write("{\n" +
                        "  \"name\": \"" + application.getName() + "\",\n" +
                        "  \"guild\": \"" + application.getGuild().getId() +"\",\n" +
                        "  \"responses\": []\n" +
                        "}");
                write.close();
                System.out.println("test");
            }

            JSONObject jsonObject = data.getJSONObjectFromFile(file.getPath());
            JSONArray responses = jsonObject.getJSONArray("responses");
            responses.put(response);

            FileWriter write = new FileWriter(file);
            write.write("{\n" +
                    "  \"name\": \"" + application.getName() + "\",\n" +
                    "  \"guild\": \"" + application.getGuild().getId() +"\",\n" +
                    "  \"responses\": " + responses.toString() + "\n" +
                    "}");
            write.close();
        }
        catch (IOException e) {
            ExceptionHandler.sendStackTrace(e);
        }
    }
    public Role getPingRole(Guild guild) {
        File file = new File(CadenBot.dataDirectory + "applications/rolemention/" + guild.getId() + ".txt");
        try {
            Scanner scan = new Scanner(file);
            return guild.getRoleById(scan.nextLine());
        }
        catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage() + ": Returning null");
            return null;
        }
    }
}
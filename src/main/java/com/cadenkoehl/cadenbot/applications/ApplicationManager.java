package com.cadenkoehl.cadenbot.applications;

import com.cadenkoehl.cadenbot.CadenBot;
import com.cadenkoehl.cadenbot.util.ExceptionHandler;
import com.cadenkoehl.cadenbot.util.data.Data;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

        File dir = new File(CadenBot.dataDirectory + "applications/applicants/" + application.getGuild() + "/");
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

        File dir = new File(CadenBot.dataDirectory + "applications/applicants/" + application.getGuild() + "/");
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

    public void sendQuestion(User applicant) {
        Application application = this.getApplicationByApplicant(applicant);

        String question;
        try {
            question = application.getQuestions().get(this.getApplicationState(applicant));
        }
        catch (IndexOutOfBoundsException ex) {
            return;
        }
        try {
            applicant.openPrivateChannel().queue(channel -> {
                channel.sendMessage(question).queue();
                this.incrementApplicationState(applicant);
            });
        }
        catch (Exception ex) {
            System.out.println();
        }
    }

    public void recordResponse(User user, String response) {
        Data data = new Data();
        Application application = this.getApplicationByApplicant(user);
        Guild guild = application.getGuild();

        File dir = new File(CadenBot.dataDirectory + "applications/responses/");
        if(dir.mkdirs()) System.out.println("Created directory " + dir.getPath());

        File file = new File(dir, user.getId() + ".json");
        JSONObject jsonObject = data.getJSONObjectFromFile(file.getPath());
        if(jsonObject.isNull("responses")) {
            try {
                FileWriter write = new FileWriter(file);
                write.write("{\n" +
                        "  \"name\": " + application.getName() + ",\n" +
                        "  \"guild\": " + guild.getId() + ",\n" +
                        "  \"responses\": [\n\"" +
                        response + "\"" +
                        "    \n" +
                        "  ]\n" +
                        "}");
                return;
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        JSONArray responses = jsonObject.getJSONArray("responses");
        responses.put(responses);
        try {
            FileWriter write = new FileWriter(file);
            write.write("{\n" +
                    "  \"name\": \"" + application.getName() + "\",\n" +
                    "  \"guild\": \"" + application.getGuild().getId() +"\",\n" +
                    "  \"responses\": " + responses.toString() + "\n" +
                    "}");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}























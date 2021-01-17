package com.cadenkoehl.cadenbot.levels;

import com.cadenkoehl.cadenbot.CadenBot;
import com.cadenkoehl.cadenbot.Constants;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class SetLevel extends ListenerAdapter {
    private static String prefix;
    private static String id;
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        id = event.getGuild().getId();
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        if(args[0].equalsIgnoreCase(getPrefix() + "setlevel")) {
            if(!event.getMember().getUser().isBot()) {
                if(event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                    try {
                        Member member = event.getMessage().getMentionedMembers().get(0);
                        if(!member.getUser().isBot()) {
                            String memberId = member.getId();
                            String amount = Arrays.stream(args).skip(2).collect(Collectors.joining(" "));
                            int amountInt = Integer.parseInt(amount);
                            File file = new File(CadenBot.dataDirectory + "levels/" + id + " " + memberId + ".txt");
                            if(!file.exists()) {
                                file.createNewFile();
                            }
                            FileWriter write = new FileWriter(file);
                            write.write(String.valueOf(amountInt));
                            write.close();
                            event.getChannel().sendMessage("Set " + member.getEffectiveName() + "'s level to " + amount).queue();
                        }
                        if(member.getUser().isBot()) {
                            event.getChannel().sendMessage("Sorry, **" + member.getEffectiveName() + "** is a bot, and isn't invited to the super cool rank party").queue();
                        }
                    }
                    catch (IndexOutOfBoundsException ex) {
                        event.getChannel().sendMessage("Please specify a member!").queue();
                    }
                    catch (IOException ex) {
                        event.getChannel().sendMessage("A fatal error has occurred! If this issue persists, please join the support server! (type " + getPrefix() + "help").queue();
                    }
                    catch (NumberFormatException ex) {
                        event.getChannel().sendMessage("Level must be a number!").queue();
                    }
                }

                if(!event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                    event.getChannel().sendMessage("You must have the `administrator` permission to use that command!").queue();
                }
            }
        }
    }
    private static String getPrefix() {
        File file;
        try {
            file = new File(CadenBot.dataDirectory + "prefix/" + id + ".txt");
            Scanner scan = new Scanner(file);
            prefix = scan.nextLine();
        }
        catch (FileNotFoundException ex) {
            prefix = "-";
        }
        catch (IOException ex) {
            ex.printStackTrace();
            CadenBot.jda.getTextChannelById(Constants.CADENBOTBUGSCHANNEL).sendMessage(CadenBot.jda.getUserById(Constants.CadenID).getAsMention() + " help! There is a huge bug in my code!! Someone tried to run a command, and this happened: " +
                    ex).queue();
        }
        return prefix;
    }
}

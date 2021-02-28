package com.cadenkoehl.cadenbot.fun;

import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandCategory;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandEvent;
import com.cadenkoehl.cadenbot.util.exceptions.IncorrectUsageException;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Hack extends Command {

    @Override
    public void execute(CommandEvent event) throws IncorrectUsageException {

        List<Member> mentionedMembers = event.getMessage().getMentionedMembers();
        if(event.getArgs().length == 1) {
            event.getChannel().sendMessage("Hold on, who are we hacking?").queue();
            return;
        }
        if(mentionedMembers.size() == 0) {
            event.getChannel().sendMessage("Hold on, who are we hacking?").queue();
            return;
        }

        Member member = mentionedMembers.get(0);
        String name = member.getUser().getName();

        String tag = member.getUser().getDiscriminator();
        String[] emails = {"playzmc@yahoo.com", "playzfortnite@gmail.com", "yt@gmail.com", "playzmc@pornhub.com", "playzmc" + tag + "@yahoo.com"};
        String[] phrases = {"I hate my life", "My new wife already took the kids again", "I'm just built different", "Why do I exist", "Can I have your number?", "WHY DID SHE LEAVE ME", "Sweet Home Alabama"};
        String[] states = {"China", "North Korea", "the United States", "a colony on mars", "Sweet Home Alabama"};
        String[] addresses = {"42069 drive", "Sesame Street", "69th Street", "420 Maple Road", "420, Wife Took the Kids Street", "69, I Hate My Life Road", "666, I Want To Die Drive"};
        String[] hackMessages = {"Found all passwords, medical records and credit card numbers on one of " + name + "'s google docs lmao", "Found " + name + "'s google doc titled \"Reasons to Live\"... It's completely empty"};


        Random random = new Random();
        int hackMessage = random.nextInt(hackMessages.length);
        int email = random.nextInt(emails.length);
        int phrase = random.nextInt(phrases.length);
        int state = random.nextInt(states.length);
        int address = random.nextInt(addresses.length);

        if (member.getUser() == event.getJDA().getSelfUser()) {
            event.getChannel().sendMessage("Hahaha! You really think I would hack myself?").queue();
            return;
        }

        event.getChannel().sendMessage("Hacking " + name + "...").queue((message -> {

            message.editMessage("Searching for email address...").queueAfter(2, TimeUnit.SECONDS);
            message.editMessage("Found email address: `" + name.replace(" ", "").toLowerCase() + emails[email] + "`").queueAfter(4, TimeUnit.SECONDS);
            message.editMessage("Hacking email address...").queueAfter(6, TimeUnit.SECONDS);
            message.editMessage("Finding password...").queueAfter(8, TimeUnit.SECONDS);
            message.editMessage("Searching for most common phrase...").queueAfter(10, TimeUnit.SECONDS);
            message.editMessage("Most common phrase: \"" + phrases[phrase] + "\"").queueAfter(12, TimeUnit.SECONDS);
            message.editMessage("Finding password...").queueAfter(14, TimeUnit.SECONDS);
            message.editMessage("Password found: " + phrases[phrase].toLowerCase().replace(" ", "") + tag).queueAfter(16, TimeUnit.SECONDS);
            message.editMessage("Finished hacking email address...").queueAfter(18, TimeUnit.SECONDS);
            message.editMessage(hackMessages[hackMessage]).queueAfter(20, TimeUnit.SECONDS);
            message.editMessage("Hacking Steam account...").queueAfter(24, TimeUnit.SECONDS);
            message.editMessage("Finished hacking steam account...").queueAfter(26, TimeUnit.SECONDS);
            message.editMessage("Doxing " + name + "...").queueAfter(28, TimeUnit.SECONDS);
            message.editMessage("Finding address...").queueAfter(30, TimeUnit.SECONDS);
            message.editMessage("Found that " + name + " is located somewhere in " + states[state]).queueAfter(32, TimeUnit.SECONDS);
            message.editMessage("Searching google maps...").queueAfter(34, TimeUnit.SECONDS);
            message.editMessage("Found that " + name + " lives on " + addresses[address]).queueAfter(36, TimeUnit.SECONDS);
            message.editMessage("Uploading data to the dark web...").queueAfter(38, TimeUnit.SECONDS);
            message.editMessage("Finished doxing " + name + "...").queueAfter(40, TimeUnit.SECONDS);
            message.editMessage("Finished hacking " + name).queueAfter(42, TimeUnit.SECONDS);
            message.reply("The hack is complete.").queueAfter(43, TimeUnit.SECONDS);

        }));

    }

    @Override
    public String getName() {
        return "hack";
    }

    @Override
    public String getDescription() {
        return "Totally legitimately hack your friends!";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.FUN;
    }

    @Override
    public Permission getRequiredPermission() {
        return null;
    }

    @Override
    public String getUsage(String prefix) {
        return "hack` `<@member>`";
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }
}
package com.cadenkoehl.cadenbot.commands.command_handler;

public enum CommandCategory {

    COMMAND("General Commands", false,"commands", "command", "cmds", "cmd"),
    STAFF("Staff", true,"staff", "moderation", "mod"),
    MUSIC(":musical_note: Music :notes:", false,"music"),
    LEVELS(":sparkles: Levels :sparkles:", false,"levels", "level", "leveling", "xp"),
    FUN(":rofl: Fun Commands :joy:", false,"fun", "lmao", "lol"),
    SETTINGS(":gear: Settings! :gear: ", true, "settings"),
    AUTO_MOD("Auto Moderation", true,"automod", "automoderation"),
    WELCOME_MESSAGES("Join/Leave Messages :wave:",true, "joinleave", "welcomemsgs", "joinleavemsgs"),
    SUGGESTIONS("Suggestions", false, "suggestions", "sug", "suggest"),
    APPLICATIONS("Applications", false, "applications", "apps", "app");

    private final String displayName;
    private final String[] aliases;
    private final boolean isStaff;

    CommandCategory(String displayName, boolean isStaff, String... aliases) {
        this.displayName = displayName;
        this.aliases = aliases;
        this.isStaff = isStaff;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public String[] getAliases() {
        return this.aliases;
    }

    public boolean isStaff() {
        return this.isStaff;
    }
}

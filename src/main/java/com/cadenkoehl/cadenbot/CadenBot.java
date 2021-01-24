package com.cadenkoehl.cadenbot;

import javax.security.auth.login.LoginException;

import com.cadenkoehl.cadenbot.joinleave.config.JoinChannel;
import com.cadenkoehl.cadenbot.staff.commands.*;
import com.cadenkoehl.cadenbot.staff.logging.*;
import com.cadenkoehl.cadenbot.commands.*;
import com.cadenkoehl.cadenbot.commands.help.*;

import com.cadenkoehl.cadenbot.fun.Hack;
import com.cadenkoehl.cadenbot.fun.hangman.Guess;
import com.cadenkoehl.cadenbot.fun.hangman.Hangman;
import com.cadenkoehl.cadenbot.fun.PpSizeMachine;
import com.cadenkoehl.cadenbot.joinleave.*;
import com.cadenkoehl.cadenbot.levels.*;
import com.cadenkoehl.cadenbot.music.*;
import com.cadenkoehl.cadenbot.reactionroles.ReactionRoles;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

/*
 * The Main Class of CadenBot
 */
public class CadenBot {

	public static JDA jda;
	public static String prefix = "-";
	private static boolean isTest = true;
	public static String dataDirectory = "";

  public static void main(String[] args) throws LoginException {

    if (isTest) {
      dataDirectory = "/users/cadenkoehl/CadenBot/data/";
    }
    if(!isTest) {
      dataDirectory = "/root/CadenBot/data/";
    }

    String token = "";
    if(isTest) {
      token = Constants.getTestToken();
    }
    if(!isTest) {
      token = Constants.getToken();
    }
    JDABuilder jda = JDABuilder.createDefault(token);
     
     //status
    jda.setStatus(OnlineStatus.ONLINE);
    jda.setActivity(Activity.watching("for -help"));

    //listeners

    jda.addEventListeners(new Help());
    jda.addEventListeners(new Info());
    jda.addEventListeners(new ReactionVote());
    jda.addEventListeners(new JoinMsgs());
    jda.addEventListeners(new LeaveMsgs());
    jda.addEventListeners(new Invite());
    jda.addEventListeners(new JoinServer());
    jda.addEventListeners(new YesOrNo());
    jda.addEventListeners(new HelpCommands());
    jda.addEventListeners(new HelpFun());
    jda.addEventListeners(new HelpStaff());
    jda.addEventListeners(new Ban());
    jda.addEventListeners(new Kick());
    jda.addEventListeners(new Mute());
    jda.addEventListeners(new Test());
    jda.addEventListeners(new ChannelUpdateLog());
    jda.addEventListeners(new RoleLog());
    jda.addEventListeners(new KickAndBanLog());
    jda.addEventListeners(new OtherLogs());
    jda.addEventListeners(new MuteCmd());
    jda.addEventListeners(new Suggest());
    jda.addEventListeners(new EmbedCmd());
    jda.addEventListeners(new HelpEmbeds());
    jda.addEventListeners(new FakeUser());
    //jda.addEventListeners(new TempMuteCmd());
    jda.addEventListeners(new JoinLeaveLog());
    jda.addEventListeners(new UserInfo());
    jda.addEventListeners(new ServerInfo());
    jda.addEventListeners(new CategoryLog());
    jda.addEventListeners(new PpSizeMachine());
    jda.addEventListeners(new Prefix());
    jda.addEventListeners(new Levels());
    jda.addEventListeners(new RankCard());
    jda.addEventListeners(new SetLevel());
    jda.addEventListeners(new HelpLevels());
    jda.addEventListeners(new CustomChannel());
    jda.addEventListeners(new IgnoreChannel());
    jda.addEventListeners(new ReactionRoles());
    jda.addEventListeners(new HelpReactionRoles());
    jda.addEventListeners(new Hack());
    jda.addEventListeners(new Leaderboard());
    jda.addEventListeners(new Xp());
    jda.addEventListeners(new Join());
    jda.addEventListeners(new Leave());
    jda.addEventListeners(new Play());
    jda.addEventListeners(new Stop());
    jda.addEventListeners(new Skip());
    jda.addEventListeners(new CurrentSong());
    jda.addEventListeners(new Queue());
    jda.addEventListeners(new TimeOut());
    jda.addEventListeners(new HelpMusic());
    jda.addEventListeners(new Hangman());
    jda.addEventListeners(new Guess());
    jda.addEventListeners(new LogChannel());
    jda.addEventListeners(new HelpWelcomeMsgs());
    jda.addEventListeners(new JoinChannel());
    jda.enableIntents(GatewayIntent.GUILD_MEMBERS);
    jda.enableCache(CacheFlag.VOICE_STATE);
    jda.setChunkingFilter(ChunkingFilter.ALL);
    jda.setMemberCachePolicy(MemberCachePolicy.ALL);
    jda.build();
  }
}
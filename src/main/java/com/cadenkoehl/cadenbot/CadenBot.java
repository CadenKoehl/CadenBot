package com.cadenkoehl.cadenbot;

import javax.security.auth.login.LoginException;

import com.cadenkoehl.cadenbot.automod.commands.*;
import com.cadenkoehl.cadenbot.automod.logging.*;
import com.cadenkoehl.cadenbot.commands.*;
import com.cadenkoehl.cadenbot.commands.help.*;
import com.cadenkoehl.cadenbot.commands.vc.JoinVC;
import com.cadenkoehl.cadenbot.commands.vc.LeaveVC;

import com.cadenkoehl.cadenbot.fun.Hack;
import com.cadenkoehl.cadenbot.fun.PpSizeMachine;
import com.cadenkoehl.cadenbot.joinleave.JoinMsgs;
import com.cadenkoehl.cadenbot.joinleave.JoinServer;
import com.cadenkoehl.cadenbot.joinleave.LeaveMsgs;
import com.cadenkoehl.cadenbot.levels.*;
import com.cadenkoehl.cadenbot.automod.logging.VcChat;
import com.cadenkoehl.cadenbot.reactionroles.ReactionRoles;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

/*
 * The Main Class of CadenBot.
 */
public class CadenBot {

	public static JDA jda;
	public static String prefix = "-";
	private static boolean isTest = true;
	public static String dataDirectory = "";

  public static void main(String[] args) throws LoginException {
     JDABuilder jda = JDABuilder.createDefault(Constants.TOKEN);
     
     //status
    jda.setStatus(OnlineStatus.ONLINE);
    jda.setActivity(Activity.watching("for -help"));


    if (isTest) {
      dataDirectory = "/users/cadenkoehl/CadenBot/data/";
    }
    if(!isTest) {
      dataDirectory = "/root/CadenBot/data/";
    }
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
    jda.addEventListeners(new HelpSrc());
    jda.addEventListeners(new HelpFun());
    jda.addEventListeners(new HelpModeration());
    jda.addEventListeners(new Ban());
    jda.addEventListeners(new Kick());
    jda.addEventListeners(new Mute());
    jda.addEventListeners(new Test());
    jda.addEventListeners(new JoinVC());
    jda.addEventListeners(new LeaveVC());
    jda.addEventListeners(new Constants());
    jda.addEventListeners(new ChannelUpdateLog());
    jda.addEventListeners(new RoleLog());
    jda.addEventListeners(new KickAndBanLog());
    jda.addEventListeners(new OtherLogs());
    jda.addEventListeners(new VcChat());
    jda.addEventListeners(new MuteCmd());
    jda.addEventListeners(new Suggest());
    jda.addEventListeners(new Embed());
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
    jda.enableIntents(GatewayIntent.GUILD_MEMBERS);
    jda.setChunkingFilter(ChunkingFilter.ALL);
    jda.setMemberCachePolicy(MemberCachePolicy.ALL);
    jda.build();
  }
}
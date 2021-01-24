package com.cadenkoehl.cadenbot;

import javax.security.auth.login.LoginException;

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
	private static boolean isTest = false;
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
    jda.setStatus(OnlineStatus.ONLINE);
    jda.setActivity(Activity.watching("for -help"));
    jda.enableIntents(GatewayIntent.GUILD_MEMBERS);
    jda.enableCache(CacheFlag.VOICE_STATE);
    jda.setChunkingFilter(ChunkingFilter.ALL);
    jda.setMemberCachePolicy(MemberCachePolicy.ALL);
    Registry.registerListeners(jda);
    jda.build();
  }
}
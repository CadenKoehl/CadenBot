package com.cadenkoehl.cadenbot;

import javax.security.auth.login.LoginException;

import com.cadenkoehl.cadenbot.util.Constants;
import com.cadenkoehl.cadenbot.util.Registry;
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

    JDABuilder builder = JDABuilder.createDefault(token);
    builder.setStatus(OnlineStatus.ONLINE);
    builder.setActivity(Activity.watching("for -help"));
    Registry.registerListeners(builder);

    builder.enableIntents(GatewayIntent.GUILD_MEMBERS);
    builder.enableCache(CacheFlag.VOICE_STATE);
    builder.setChunkingFilter(ChunkingFilter.ALL);
    builder.setMemberCachePolicy(MemberCachePolicy.ALL);

    CadenBot.jda = builder.build();
  }
}
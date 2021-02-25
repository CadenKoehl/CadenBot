package com.cadenkoehl.cadenbot;

import javax.security.auth.login.LoginException;

import com.cadenkoehl.cadenbot.commands.command_handler.CommandHandler;
import com.cadenkoehl.cadenbot.util.data.Config;
import com.cadenkoehl.cadenbot.util.data.Data;
import com.cadenkoehl.cadenbot.util.Registry;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

/**
 * The Main Class of CadenBot
 * @author Caden Koehl
 */
public class CadenBot {

  public static JDA jda;
  public static final boolean isTest = Config.get().getBoolean("test");
  public static final String dataDirectory = Data.getDataFolderPath();

  private CadenBot() {}

  public static void main(String[] args) throws LoginException {
    JDABuilder builder = JDABuilder.createDefault(Data.getToken());
    builder.setStatus(OnlineStatus.ONLINE);
    builder.setActivity(Activity.watching("for -help"));
    Registry.registerListeners(builder);
    builder.addEventListeners(new CommandHandler());
    Registry.registerCommands();
    builder.enableIntents(GatewayIntent.GUILD_MEMBERS);
    builder.enableCache(CacheFlag.VOICE_STATE);
    builder.setChunkingFilter(ChunkingFilter.ALL);
    builder.setMemberCachePolicy(MemberCachePolicy.ALL);

    CadenBot.jda = builder.build();

  }
}
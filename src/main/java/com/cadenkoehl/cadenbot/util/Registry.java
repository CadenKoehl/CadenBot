package com.cadenkoehl.cadenbot.util;

import com.cadenkoehl.cadenbot.applications.*;
import com.cadenkoehl.cadenbot.commands.*;
import com.cadenkoehl.cadenbot.commands.command_handler.Command;
import com.cadenkoehl.cadenbot.commands.command_handler.CommandHandler;
import com.cadenkoehl.cadenbot.commands.settings.Prefix;
import com.cadenkoehl.cadenbot.commands.settings.Toggle;
import com.cadenkoehl.cadenbot.fun.ImageGenTest;
import com.cadenkoehl.cadenbot.fun.YesOrNo;
import com.cadenkoehl.cadenbot.fun.Hack;
import com.cadenkoehl.cadenbot.fun.PpSizeMachine;
import com.cadenkoehl.cadenbot.fun.hangman.Guess;
import com.cadenkoehl.cadenbot.fun.hangman.Hangman;
import com.cadenkoehl.cadenbot.joinleave.JoinMsgs;
import com.cadenkoehl.cadenbot.joinleave.JoinServer;
import com.cadenkoehl.cadenbot.joinleave.LeaveMsgs;
import com.cadenkoehl.cadenbot.joinleave.config.*;
import com.cadenkoehl.cadenbot.levels.*;
import com.cadenkoehl.cadenbot.music.*;
import com.cadenkoehl.cadenbot.music.extra_help_info.FavoriteAdd;
import com.cadenkoehl.cadenbot.music.extra_help_info.FavoriteList;
import com.cadenkoehl.cadenbot.music.extra_help_info.FavoritePlay;
import com.cadenkoehl.cadenbot.music.extra_help_info.FavoriteRemove;
import com.cadenkoehl.cadenbot.reactionroles.ReactionRoleListener;
import com.cadenkoehl.cadenbot.reactionroles.ReactionRoles;
import com.cadenkoehl.cadenbot.staff.commands.*;
import com.cadenkoehl.cadenbot.staff.commands.mute.MuteCmd;
import com.cadenkoehl.cadenbot.staff.commands.mute.MuteManager;
import com.cadenkoehl.cadenbot.staff.commands.mute.TempMute;
import com.cadenkoehl.cadenbot.staff.commands.mute.UnmuteCmd;
import com.cadenkoehl.cadenbot.staff.commands.suggest.*;
import com.cadenkoehl.cadenbot.staff.commands.warn.ClearWarns;
import com.cadenkoehl.cadenbot.staff.commands.warn.Unban;
import com.cadenkoehl.cadenbot.staff.commands.warn.WarnCmd;
import com.cadenkoehl.cadenbot.staff.automod.logging.*;
import com.cadenkoehl.cadenbot.util.owner_commands.Shutdown;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Arrays;


public class Registry {

        public static void registerCommands() {
                register(

                        //General Commands
                        new Info(),
                        new Help(),
                        new EmbedCmd(),
                        new ReactionRoles(),
                        new FakeUser(),
                        new Invite(),
                        new UserInfo(),
                        new ReactionVote(),
                        new ServerInfo(),

                        //Settings
                        new Prefix(),
                        new Toggle(),

                        //Fun Commands
                        new Hangman(),
                        new Hack(),
                        new ImageGenTest(),
                        new PpSizeMachine(),
                        new YesOrNo(),

                        //Join-leave Messages
                        new JoinChannel(),
                        new JoinMsg(),
                        new LeaveChannel(),
                        new LeaveMsg(),
                        new RandomMsgs(),

                        //Levels
                        new RankCard(),
                        new Leaderboard(),
                        new LevelMessage(),
                        new LevelChannel(),
                        new SetLevel(),
                        new IgnoreChannel(),
                        new UnignoreChannel(),
                        new IgnoredChannelList(),

                        //Music
                        new Join(),
                        new Play(),
                        new CurrentSong(),
                        new Queue(),
                        new Skip(),
                        new Repeat(),
                        new Pause(),
                        new Resume(),
                        new Favorite(),
                        new FavoriteAdd(),
                        new FavoriteList(),
                        new FavoritePlay(),
                        new FavoriteRemove(),
                        new ClearQueue(),
                        new Leave(),

                        //Staff
                        new MuteCmd(),
                        new UnmuteCmd(),
                        new TempMute(),
                        new WarnCmd(),
                        new ClearWarns(),
                        new Ban(),
                        new Unban(),
                        new Kick(),
                        new Clear(),

                        //Applications
                        new ApplyCommand(),
                        new CreateApp(),
                        new DeleteApp(),
                        new ListApps(),
                        new ViewApp(),
                        new AddQuestion(),
                        new RemoveQuestion(),
                        new AppChannel(),
                        new SetPingRole(),
                        new RemovePingRole(),

                        //AutoMod
                        new LogChannel(),
                        new LoggingOff(),
                        new LoggingOff.Help(),

                        //Suggestions
                        new SuggestCmd(),
                        new SuggestApprove(),
                        new SuggestConsider(),
                        new SuggestDeny(),
                        new SuggestChannel()
                );
        }

        private static void register(Command... commands) {
                CommandHandler.commands.addAll(Arrays.asList(commands));
        }


        public static void registerListeners(JDABuilder jda) {
                register(new CommandHandler(), jda);
                register(new ApplicationListeners(), jda);
                register(new JoinMsgs(), jda);
                register(new LeaveMsgs(), jda);
                register(new JoinServer(), jda);
                register(new MuteManager(), jda);
                register(new Levels(), jda);
                register(new ReactionRoleListener(), jda);
                register(new Xp(), jda);
                register(new TimeOut(), jda);
                register(new Guess(), jda);
                register(new GuildUpdates(), jda);
                register(new MemberUpdates(), jda);
                register(new RoleUpdates(), jda);
                register(new TextChannelUpdates(), jda);
                register(new VoiceChannelUpdates(), jda);
                register(new CategoryUpdates(), jda);
                register(new Shutdown(), jda);
                register(new Ready(), jda);

        }
        private static void register(ListenerAdapter listener, JDABuilder jda) {
                jda.addEventListeners(listener);
        }
}
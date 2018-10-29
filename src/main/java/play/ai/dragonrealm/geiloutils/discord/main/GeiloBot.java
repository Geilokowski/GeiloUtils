package play.ai.dragonrealm.geiloutils.discord.main;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.TextChannel;
import play.ai.dragonrealm.geiloutils.config.ConfigurationManager;
import play.ai.dragonrealm.geiloutils.discord.listener.MessageListener;
import play.ai.dragonrealm.geiloutils.discord.listener.ReadyListener;

public class GeiloBot {
	public static JDA jda;
	public static TextChannel channelCommands;
	public static TextChannel channelIRC;
	public static void initBot() {
		JDABuilder builder = new JDABuilder(AccountType.BOT);
		builder.setToken(ConfigurationManager.getDiscordConfig().getToken());
		builder.setAutoReconnect(true);
		builder.setStatus(OnlineStatus.ONLINE);
		builder.addEventListener(new ReadyListener());
		builder.addEventListener(new MessageListener());
		try {
			jda = builder.build();
		  } catch (LoginException e) {
			e.printStackTrace();
		}
	}
}

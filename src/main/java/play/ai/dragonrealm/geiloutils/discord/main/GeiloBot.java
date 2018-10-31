package play.ai.dragonrealm.geiloutils.discord.main;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import play.ai.dragonrealm.geiloutils.config.ConfigurationManager;
import play.ai.dragonrealm.geiloutils.discord.listener.MessageListener;
import play.ai.dragonrealm.geiloutils.discord.listener.ReadyListener;

import java.util.ArrayList;
import java.util.List;

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

	public static ArrayList<String> getBotsInGuild() {
		List<Member> memberList = jda.getTextChannelById(ConfigurationManager.getDiscordConfig().getChannelIDRelay()).getMembers();

		ArrayList<String> bots = new ArrayList<>();

		for(Member member: memberList) {
			if(member.getUser().isBot()){
				bots.add(member.getUser().getName());
			}
		}

		return bots;
	}

	public static ArrayList<String> getBotsInGuildSingle() {
		List<Member> memberList = jda.getTextChannelById(ConfigurationManager.getDiscordConfig().getChannelIDRelay()).getMembers();

		ArrayList<String> bots = new ArrayList<>();

		for(Member member: memberList) {
			if(member.getUser().isBot()){
				bots.add(member.getUser().getName().replace(" ", "_"));
			}
		}

		return bots;
	}
}

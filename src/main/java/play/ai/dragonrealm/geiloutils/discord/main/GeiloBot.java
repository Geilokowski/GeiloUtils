package play.ai.dragonrealm.geiloutils.discord.main;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import net.minecraftforge.fml.common.FMLCommonHandler;
import play.ai.dragonrealm.geiloutils.config.ConfigurationManager;
import play.ai.dragonrealm.geiloutils.discord.command.BotSender;
import play.ai.dragonrealm.geiloutils.discord.listener.MessageListener;
import play.ai.dragonrealm.geiloutils.discord.listener.ReadyListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

@Deprecated
public class GeiloBot {
	public static JDA jda;
	public static TextChannel channelCommands;
	public static TextChannel channelIRC;
	private static Long patron = null;

	/*public static void initBot() {
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
	}*/

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

	public static Role getPatronRole() {
	    if(patron != null) {
	        return jda.getRoleById(patron);
        } else {
            String patronGlobal = ConfigurationManager.getDiscordConfig().getPatronGlobalRank();
            Guild guild = jda.getTextChannelById(ConfigurationManager.getDiscordConfig().getChannelIDRelay()).getGuild();
            List<Role> roles = guild.getRolesByName(patronGlobal, true);
            if(!roles.isEmpty()) {
                patron = roles.get(0).getIdLong();
                return jda.getRoleById(patron);
            }
        }
        return null;
    }

	public static void getRankFromDiscord(Long discordVerifiedID, String userName){
		String patronGlobal = ConfigurationManager.getDiscordConfig().getPatronGlobalRank();
		if(!patronGlobal.isEmpty()) {
			Guild guild = jda.getTextChannelById(ConfigurationManager.getDiscordConfig().getChannelIDRelay()).getGuild();
			List<Role> roles = guild.getRolesByName(patronGlobal, true);
			if(!roles.isEmpty()) {
				Role role = roles.get(0);
				List<Member> allPatrons = guild.getMembersWithRoles(role);

				Member selectedPatron = null;
				for(Member patron : allPatrons){
					if(patron.getUser().getIdLong() == discordVerifiedID) {
						selectedPatron = patron;
						break;
					}
				}

				if (selectedPatron != null) {
					List<Role> patronRoles = selectedPatron.getRoles();

					String teirRole = "";
					Set<String> teiredRoles = ConfigurationManager.getDiscordConfig().getPatronRanks().keySet();
					for (Role memberRole : patronRoles) {
						if(teiredRoles.contains(memberRole.getName().toLowerCase().replace(" ", "_"))) {
							teirRole = memberRole.getName().toLowerCase().replace(" ", "_");
							break;
						}
					}

					if(!teirRole.isEmpty()) {
						List<String> commands = ConfigurationManager.getDiscordConfig().getPatronRanks().get(teirRole);
						FMLCommonHandler.instance().getMinecraftServerInstance().callFromMainThread(new Callable<Object>() {
							@Override
							public Object call() throws Exception {
								for (String command : commands) {
									String entry = String.format(command, userName);
									FMLCommonHandler.instance().getMinecraftServerInstance().getCommandManager().executeCommand(BotSender.SILENT_BOT, entry);
								}
								return null;
							}
						});
					}

				}
			}
		}
	}
}

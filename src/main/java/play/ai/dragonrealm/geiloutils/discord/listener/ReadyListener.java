package play.ai.dragonrealm.geiloutils.discord.listener;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import play.ai.dragonrealm.geiloutils.GeiloUtils;
import play.ai.dragonrealm.geiloutils.discord.main.DiscordBotMain;

public class ReadyListener extends ListenerAdapter{

	public void onReady(ReadyEvent event) {
		GeiloUtils.getLogger().info("The bot is running on the following servers: ");
		for (Guild g : event.getJDA().getGuilds()) {
			GeiloUtils.getLogger().info(("Name: " + g.getName() + " ID: " + g.getId()));

			DiscordBotMain.getInstance().sendMessageDiscord("Server Starting!");
		}
	}
}

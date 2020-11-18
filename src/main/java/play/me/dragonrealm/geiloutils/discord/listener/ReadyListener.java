package play.me.dragonrealm.geiloutils.discord.listener;


import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import play.me.dragonrealm.geiloutils.GeiloUtils;
import play.me.dragonrealm.geiloutils.configs.ConfigAccess;
import play.me.dragonrealm.geiloutils.discord.main.DiscordBotMain;

public class ReadyListener extends ListenerAdapter {

	public void onReady(ReadyEvent event) {
		GeiloUtils.getLog().info("The bot is running on the following servers: ");
		for (Guild g : event.getJDA().getGuilds()) {
			GeiloUtils.getLog().info(("Name: " + g.getName() + " ID: " + g.getId()));
		}

		if(!ConfigAccess.getDiscordConfig().getChannelIDRelay().isEmpty()){
			DiscordBotMain.getInstance().sendMessageDiscord("Server Starting!");
		}
	}
}

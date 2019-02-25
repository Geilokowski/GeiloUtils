package play.ai.dragonrealm.geiloutils.events;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import play.ai.dragonrealm.geiloutils.config.ConfigurationManager;
import play.ai.dragonrealm.geiloutils.config.playerstats.Playerstat;
import play.ai.dragonrealm.geiloutils.discord.main.DiscordBotMain;
import play.ai.dragonrealm.geiloutils.new_configs.ConfigManager;
import play.ai.dragonrealm.geiloutils.utils.PlayerUtils;

public class EventHandlerPlayer {
	@SubscribeEvent
	public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event){
		boolean firstJoin = false;
		if (PlayerUtils.isFirstJoin(event.player)){
			Playerstat ps = new Playerstat();
			ps.setName(event.player.getDisplayNameString());
			ps.setMoney(ConfigurationManager.getEconomyConfig().getStartingMoney());
			ps.setUuid(event.player.getCachedUniqueIdString());
			ps.setRank(ConfigurationManager.getGeneralConfig().getStandartRank());
			ps.setDirectDeposit(true);
			ps.setMutePaymentMsg(false);
			ConfigurationManager.getPlayerstats().getPlayerstats().add(ps);
			ConfigurationManager.syncFromFields();
			firstJoin = true;
		}

		if(ConfigManager.getDiscordConfig().isEnabled()){
			String message = firstJoin ? " joined for the first time!" : " joined the game";
			DiscordBotMain.getInstance().sendMessageDiscord(event.player.getDisplayNameString() + message);
		}
	}

	@SubscribeEvent
	public void onPlayerLeave(PlayerEvent.PlayerLoggedOutEvent event){
		if(ConfigManager.getDiscordConfig().isEnabled()){
			DiscordBotMain.getInstance().sendMessageDiscord(event.player.getDisplayNameString() + " left the game");
		}
	}
}

package play.ai.dragonrealm.geiloutils.events;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import play.ai.dragonrealm.geiloutils.new_configs.models.Playerstat;
import play.ai.dragonrealm.geiloutils.discord.main.DiscordBotMain;
import play.ai.dragonrealm.geiloutils.new_configs.ConfigAccess;
import play.ai.dragonrealm.geiloutils.utils.PlayerUtils;

public class EventHandlerPlayer {
	@SubscribeEvent
	public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event){
		boolean firstJoin = false;
		if (PlayerUtils.isFirstJoin(event.player)){
			Playerstat ps = new Playerstat();
			ps.setName(event.player.getDisplayNameString());
			ps.setMoney(ConfigAccess.getEconomyConfig().getStartingMoney());
			ps.setUuid(event.player.getCachedUniqueIdString());
			ps.setRank(ConfigAccess.getGeneralConfig().getStandardRank());
			ps.setDirectDeposit(true);
			ps.setMutePaymentMsg(false);
			ConfigAccess.getPlayerStatsConfig().getPlayerstats().add(ps);
			ConfigAccess.writePlayerStatsFile();
			firstJoin = true;
		}

		if(ConfigAccess.getDiscordConfig().isEnabled()){
			String message = firstJoin ? " joined for the first time!" : " joined the game";
			DiscordBotMain.getInstance().sendMessageDiscord(event.player.getDisplayNameString() + message);
		}
	}

	@SubscribeEvent
	public void onPlayerLeave(PlayerEvent.PlayerLoggedOutEvent event){
		if(ConfigAccess.getDiscordConfig().isEnabled()){
			DiscordBotMain.getInstance().sendMessageDiscord(event.player.getDisplayNameString() + " left the game");
		}
	}
}

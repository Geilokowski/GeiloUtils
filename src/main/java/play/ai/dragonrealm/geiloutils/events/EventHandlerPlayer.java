package play.ai.dragonrealm.geiloutils.events;


import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import play.ai.dragonrealm.geiloutils.GeiloUtils;
import play.ai.dragonrealm.geiloutils.new_configs.containers.PlayerStatsConfig;
import play.ai.dragonrealm.geiloutils.new_configs.models.Playerstat;
import play.ai.dragonrealm.geiloutils.discord.main.DiscordBotMain;
import play.ai.dragonrealm.geiloutils.new_configs.ConfigAccess;

public class EventHandlerPlayer {
	@SubscribeEvent
	public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event){
		boolean firstJoin = false;
		if (GeiloUtils.getManager().getConfig(PlayerStatsConfig.class).isFirstJoin(event.getPlayer().getStringUUID())){
			Playerstat ps = new Playerstat();
			ps.setName(event.getPlayer().getDisplayName().getString());
			ps.setMoney(ConfigAccess.getEconomyConfig().getStartingMoney());
			ps.setUuid(event.getPlayer().getStringUUID());
			ps.setRank(ConfigAccess.getGeneralConfig().getStandardRank());
			ps.setDirectDeposit(true);
			ps.setMutePaymentMsg(false);
			ConfigAccess.getPlayerStatsConfig().getPlayerstats().add(ps);
			ConfigAccess.writePlayerStatsFile();
			firstJoin = true;
		}

		if(ConfigAccess.getDiscordConfig().isEnabled()){
			String message = firstJoin ? " joined for the first time!" : " joined the game";
			DiscordBotMain.getInstance().sendMessageDiscord(event.getPlayer().getGameProfile().getName() + message);
		}
	}

	@SubscribeEvent
	public void onPlayerLeave(PlayerEvent.PlayerLoggedOutEvent event){
		if(ConfigAccess.getDiscordConfig().isEnabled()){
			DiscordBotMain.getInstance().sendMessageDiscord(event.getPlayer().getGameProfile().getName() + " left the game");
		}
	}
}

package play.ai.dragonrealm.geiloutils.events;

import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.registries.IForgeRegistryModifiable;
import play.ai.dragonrealm.geiloutils.config.ConfigurationManager;
import play.ai.dragonrealm.geiloutils.config.playerstats.Playerstat;
import play.ai.dragonrealm.geiloutils.discord.main.GeiloBot;
import play.ai.dragonrealm.geiloutils.utils.ArrayUtils;
import play.ai.dragonrealm.geiloutils.utils.PlayerUtils;

public class EventHandlerPlayer {
	@SubscribeEvent
	public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event){
		if (PlayerUtils.isFirstJoin(event.player)){
			Playerstat ps = new Playerstat();
			ps.setName(event.player.getDisplayNameString());
			ps.setMoney(ConfigurationManager.getEconomyConfig().getStartingMoney());
			ps.setUuid(event.player.getCachedUniqueIdString());
			ps.setRank(ConfigurationManager.getGeneralConfig().getStandartRank());
			ps.setDirectDeposit(true);
			ConfigurationManager.getPlayerstats().getPlayerstats().add(ps);
			ConfigurationManager.syncFromFields();
		}

		if(ConfigurationManager.getDiscordConfig().isEnabled()){
			GeiloBot.channelIRC.sendMessage(event.player.getDisplayNameString() + " joined the game").queue();
		}
	}

	@SubscribeEvent
	public void onPlayerLeave(PlayerEvent.PlayerLoggedOutEvent event){
		if(ConfigurationManager.getDiscordConfig().isEnabled()){
			GeiloBot.channelIRC.sendMessage(event.player.getDisplayNameString() + " left the game").queue();
		}
	}
}

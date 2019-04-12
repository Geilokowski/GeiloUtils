package play.ai.dragonrealm.geiloutils.events;

import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import play.ai.dragonrealm.geiloutils.discord.main.DiscordBotMain;
import play.ai.dragonrealm.geiloutils.new_configs.ConfigAccess;

public class ChatEvent {
	@SubscribeEvent
	public void onMessage(ServerChatEvent event) {
		if(ConfigAccess.getDiscordConfig().isEnabled()) {
			String prefix = ConfigAccess.getDiscordConfig().getDiscordChatPrefix();
			String finMsg = "";
			if(prefix.contains("%s")) {
				finMsg += String.format(prefix, event.getUsername());
			} else {
				finMsg += prefix + " " + event.getUsername() + " ";
			}
			finMsg += event.getMessage();
			DiscordBotMain.getInstance().sendMessageDiscord(finMsg);
			//GeiloUtils.getLogger().info("MSG: " + event.getMessage());
		}
	}

	@SubscribeEvent
	public void onConnect(FMLNetworkEvent.ClientConnectedToServerEvent event){
		//event.getManager().channel().pipeline().
	}

}

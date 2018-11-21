package play.ai.dragonrealm.geiloutils.events;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import play.ai.dragonrealm.geiloutils.GeiloUtils;
import play.ai.dragonrealm.geiloutils.config.ConfigurationManager;
import play.ai.dragonrealm.geiloutils.discord.main.GeiloBot;

public class ChatEvent {
	@SubscribeEvent
	public void onMessage(ServerChatEvent event) {
		if(ConfigurationManager.getDiscordConfig().isEnabled()) {
			String prefix = ConfigurationManager.getDiscordConfig().getDiscordChatPrefix();
			String finMsg = "";
			if(prefix.contains("%s")) {
				finMsg += String.format(prefix, event.getUsername());
			} else {
				finMsg += prefix + " " + event.getUsername() + " ";
			}
			finMsg += event.getMessage();
			GeiloBot.channelIRC.sendMessage(finMsg).queue();
			//GeiloUtils.getLogger().info("MSG: " + event.getMessage());
		}
	}

}

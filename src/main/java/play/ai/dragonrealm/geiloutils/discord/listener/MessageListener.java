package play.ai.dragonrealm.geiloutils.discord.listener;

import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.FMLCommonHandler;
import play.ai.dragonrealm.geiloutils.GeiloUtils;
import play.ai.dragonrealm.geiloutils.config.ConfigurationManager;
import play.ai.dragonrealm.geiloutils.discord.main.GeiloBot;

public class MessageListener extends ListenerAdapter{
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		if(event.getChannel().getId().equals(ConfigurationManager.getDiscordConfig().getChannelIDRelay()) && !(event.getAuthor().getId().equals(GeiloBot.jda.getSelfUser().getId()))) {
			String output = ConfigurationManager.getDiscordConfig().getMinecraftChatPrefix() +
					event.getMessage().getAuthor().getName() + " §6\u00BB §r" +
					event.getMessage().getContentDisplay();
			try {
				FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().sendMessage(new TextComponentString(output));
			}catch(Exception e) {
				
			}
		}
	}
}

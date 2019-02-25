package play.ai.dragonrealm.geiloutils.discord.listener;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.common.FMLCommonHandler;
import play.ai.dragonrealm.geiloutils.config.ConfigurationManager;
import play.ai.dragonrealm.geiloutils.config.playerstats.Playerstat;
import play.ai.dragonrealm.geiloutils.discord.command.CommandProcessor;
import play.ai.dragonrealm.geiloutils.discord.main.DiscordBotMain;
import play.ai.dragonrealm.geiloutils.discord.utils.DiscordRank;
import play.ai.dragonrealm.geiloutils.discord.utils.DiscordUtils;
import play.ai.dragonrealm.geiloutils.new_configs.ConfigManager;
import play.ai.dragonrealm.geiloutils.utils.PlayerUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageListener extends ListenerAdapter{

	private static Map<String, String> colorMap = new HashMap<>();
	private static List<String> colors = ConfigManager.getDiscordConfig().getValidColors();

	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		if(event.getChannel().getId().equals(ConfigManager.getDiscordConfig().getChannelIDRelay()) && !(event.getAuthor().getId().equals(DiscordBotMain.getInstance().getBotID()))) {

			Message msg = event.getMessage();

			if(msg.getContentDisplay().startsWith(ConfigManager.getDiscordConfig().getDiscordCommandPrefix())){
				boolean deleteMessage = CommandProcessor.processCommand(event.getAuthor(), msg.getContentDisplay());
				if(deleteMessage){
					msg.delete().queue();
				}
				return;
            }


			String output = getPrefix(msg.getAuthor().isBot(), msg.getAuthor().getName()) + " " + "\u00A76" + "\u00BB " + "\u00A7r" +msg.getContentDisplay();
			ITextComponent formattedMessage = ForgeHooks.newChatWithLinks(output);
			// Multi-server chat clogs up the readers view. This allows us to mute any chat network we don't like
			if (ConfigManager.getDiscordConfig().isSingleToMulti()){
				for (EntityPlayerMP ep : FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers()) {
					Playerstat ps = PlayerUtils.getPlayerstatByUUID(ep.getCachedUniqueIdString());
					if(ps != null && !ps.getMutedChats().contains(msg.getAuthor().getName())) {
						ep.sendMessage(formattedMessage);
					}
				}
				// But if not in multi-mode, eh, just launch it server wide!
			} else {
				FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().sendMessage(formattedMessage);
			}

			checkIfUserNeedsSpaming(event);
		}
	}

	private static String getPrefix(boolean isBot, String authorName) {
		if(isBot){
			// If the message is coming from a bot, make it look prettier -> [SERVER] >> <Player> message
			return getBotPrefix(authorName);
		} else {
			// Not a bot, this is from a discord user, so [DISCORD] <User> >> msg
			return ConfigManager.getDiscordConfig().getMinecraftChatPrefix() + authorName;
		}
	}

	public static String getBotPrefix(String botName) {
		String color;
		if(colorMap.containsKey(botName)){
			color = colorMap.get(botName);
		} else {
			colorMap.put(botName, colors.get(colorMap.size() % colors.size()));
			color = colorMap.get(botName);
		}
		return "[" + color + botName + "§r]";
	}

	public static void checkIfUserNeedsSpaming(GuildMessageReceivedEvent event){
		DiscordRank rank = DiscordUtils.getAuthForUser(event.getAuthor());
		if(rank == DiscordRank.UNUSED) {
			event.getMessage().addReaction("\uD83D\uDEAF").queue();
		}
	}
}

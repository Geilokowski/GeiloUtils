package play.ai.dragonrealm.geiloutils.discord.listener;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.ForgeHooks;
import play.ai.dragonrealm.geiloutils.discord.main.DiscordUser;
import play.ai.dragonrealm.geiloutils.utils.McFacade;
import play.ai.dragonrealm.geiloutils.new_configs.models.Playerstat;
import play.ai.dragonrealm.geiloutils.discord.command.CommandProcessor;
import play.ai.dragonrealm.geiloutils.discord.main.DiscordBotMain;
import play.ai.dragonrealm.geiloutils.new_configs.ConfigAccess;
import play.ai.dragonrealm.geiloutils.utils.PlayerUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageListener extends ListenerAdapter {

	private static Map<String, String> colorMap = new HashMap<>();
	private static List<String> colors = ConfigAccess.getDiscordConfig().getValidColors();

	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		if(ConfigAccess.getDiscordConfig().getChannelIDRelay().contains(event.getChannel().getId()) && !(event.getAuthor().getId().equals(DiscordBotMain.getInstance().getBotID()))) {

			Message msg = event.getMessage();
			String userName = event.getMember() == null ? event.getAuthor().getName() : event.getMember().getEffectiveName();

			if(msg.getContentDisplay().startsWith(ConfigAccess.getDiscordConfig().getDiscordCommandPrefix())){
				boolean deleteMessage = CommandProcessor.processCommand(new DiscordUser(event.getAuthor()), msg.getContentDisplay(), event.getChannel().getId());
				if(deleteMessage){
					msg.delete().queue();
				}
				return;
			}

			String secondHalf = msg.getContentDisplay();
			if(secondHalf.equals("") && msg.getAttachments().size() > 0) {
				secondHalf = "Uploaded Media in Discord Channel";
			}

			String output = getPrefix(msg.getAuthor().isBot(), userName) + " " + "\u00A76" + "\u00BB " + "\u00A7r" + secondHalf;
			ITextComponent formattedMessage = ForgeHooks.newChatWithLinks(output);
			// Multi-server chat clogs up the readers view. This allows us to mute any chat network we don't like
			if (ConfigAccess.getDiscordConfig().isSingleToMulti()){
				for (ServerPlayerEntity ep : McFacade.getOnlinePlayers()) {
					Playerstat ps = PlayerUtils.getPlayerstatByUUID(ep.getStringUUID());
					if(ps != null && !ps.getMutedChats().contains(msg.getAuthor().getName())) {
						ep.sendMessage(formattedMessage, Util.NIL_UUID);
					}
				}
				// But if not in multi-mode, eh, just launch it server wide!
			} else {
				McFacade.messageAllPlayers(formattedMessage);
			}

			//checkIfUserNeedsSpaming(event);
		}
	}

	private static String getPrefix(boolean isBot, String authorName) {
		if(isBot){
			// If the message is coming from a bot, make it look prettier -> [SERVER] >> <Player> message
			return getBotPrefix(authorName);
		} else {
			// Not a bot, this is from a discord user, so [DISCORD] <User> >> msg
			return ConfigAccess.getDiscordConfig().getMinecraftChatPrefix() + authorName;
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

	/*public static void checkIfUserNeedsSpaming(GuildMessageReceivedEvent event){
		DiscordRank rank = DiscordUtils.getAuthForUser(event.getAuthor());
		if(rank == DiscordRank.UNUSED) {
			event.getMessage().addReaction("\uD83D\uDEAF").queue();
		}
	}*/
}

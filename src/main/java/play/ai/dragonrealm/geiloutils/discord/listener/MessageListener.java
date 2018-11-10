package play.ai.dragonrealm.geiloutils.discord.listener;

import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.world.NoteBlockEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import play.ai.dragonrealm.geiloutils.GeiloUtils;
import play.ai.dragonrealm.geiloutils.config.ConfigurationManager;
import play.ai.dragonrealm.geiloutils.config.playerstats.Playerstat;
import play.ai.dragonrealm.geiloutils.discord.main.GeiloBot;
import play.ai.dragonrealm.geiloutils.utils.PlayerUtils;
import scala.actors.threadpool.Arrays;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MessageListener extends ListenerAdapter{

	private static Map<String, String> colorMap = new HashMap<>();
	private static List<String> colors = ConfigurationManager.getDiscordConfig().getValidColors();

	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		if(event.getChannel().getId().equals(ConfigurationManager.getDiscordConfig().getChannelIDRelay()) && !(event.getAuthor().getId().equals(GeiloBot.jda.getSelfUser().getId()))) {
			if(event.getMessage().getContentDisplay().startsWith(ConfigurationManager.getDiscordConfig().getDiscordCommandPrefix())){
			    if(event.getMessage().getContentDisplay().substring(1).equals("online")){
			        if(FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers().size() == 0) {
                        GeiloBot.channelIRC.sendMessage("No players online!").queue();
                    }else{

			            if(PlayerUtils.getOnlinePlayerNames().length == 1){
                            GeiloBot.channelIRC.sendMessage("1 Player online: " + PlayerUtils.getOnlinePlayerNames()[0]).queue();
			                return;
                        }

                        String output = "";
                        for(String s : PlayerUtils.getOnlinePlayerNames()){
                            output = output + s + ", ";
                        }
                        output = output.substring(0, output.length() - 2);

                        GeiloBot.channelIRC.sendMessage(PlayerUtils.getOnlinePlayerNames().length + " Players online: " + output).queue();
                    }
                }
            }
			// Var that stores location prefix
			String prefix;
			// If the message is coming from a bot, make it look prettier -> [SERVER] >> <Player> message
			if(event.getMessage().getAuthor().isBot()){
				String botName = event.getMessage().getAuthor().getName();
				String color;
				if(colorMap.containsKey(botName)){
					color = colorMap.get(botName);
				} else {
					colorMap.put(botName, colors.get(colorMap.size() % colors.size()));
					color = colorMap.get(botName);
				}
				prefix = "[" + color + event.getMessage().getAuthor().getName() + "§r]";
			}else {
				// Not a bot, this is from a discord user, so [DISCORD] <User> >> msg
				prefix = ConfigurationManager.getDiscordConfig().getMinecraftChatPrefix() + event.getMessage().getAuthor().getName();
			}
			String output = prefix + " §6\u00BB §r" + event.getMessage().getContentDisplay();
			try {
				// Multi-server chat clogs up the readers view. This allows us to mute any chat network we don't like
				if (ConfigurationManager.getDiscordConfig().isSingleToMulti()){
					for (EntityPlayerMP ep : FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers()) {
						Playerstat ps = PlayerUtils.getPlayerstatByUUID(ep.getCachedUniqueIdString());
						if(ps != null && !ps.getMutedChats().contains(event.getMessage().getAuthor().getName())) {
							ep.sendMessage(new TextComponentString(output));
						}
					}
					// But if not in multi-mode, eh, just launch it server wide!
				} else {
					FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().sendMessage(new TextComponentString(output));
				}

			}catch(Exception e) {
				
			}
		}
	}
}

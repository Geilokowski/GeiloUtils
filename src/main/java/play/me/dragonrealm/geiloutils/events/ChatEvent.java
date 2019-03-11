package play.me.dragonrealm.geiloutils.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import play.me.dragonrealm.geiloutils.configs.ConfigManager;
import play.me.dragonrealm.geiloutils.discord.main.DiscordBotMain;

public class ChatEvent implements Listener {


    @EventHandler
    public void playerToDiscordEvent(AsyncPlayerChatEvent event){
        if(ConfigManager.getDiscordConfig().isEnabled()) {
            String prefix = ConfigManager.getDiscordConfig().getDiscordChatPrefix();
            String finMsg = "";
            if(prefix.contains("%s")) {
                finMsg += String.format(prefix, event.getPlayer().getName());
            } else {
                finMsg += prefix + " " + event.getPlayer().getName() + " ";
            }
            finMsg += event.getMessage();
            DiscordBotMain.getInstance().sendMessageDiscord(finMsg);
            //GeiloUtils.getLogger().info("MSG: " + event.getMessage());
        }
    }
}

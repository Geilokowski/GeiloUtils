package play.me.dragonrealm.geiloutils.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import play.me.dragonrealm.geiloutils.configs.ConfigManager;
import play.me.dragonrealm.geiloutils.configs.models.PlayerStats;
import play.me.dragonrealm.geiloutils.discord.main.DiscordBotMain;
import play.me.dragonrealm.geiloutils.utils.PlayerUtils;

public class LoginEvent implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        boolean firstJoin = false;
        if (PlayerUtils.isFirstJoin(event.getPlayer())){
            PlayerStats ps = new PlayerStats();
            ps.setName(event.getPlayer().getName());
            ps.setMoney(ConfigManager.getEconomyConfig().getStartingMoney());
            ps.setUuid(event.getPlayer().getUniqueId().toString());
            ps.setRank(ConfigManager.getGeneralConfig().getStandardRank());
            ps.setDirectDeposit(true);
            ps.setMutePaymentMsg(false);
            ConfigManager.getPlayerConfig().getPlayerstats().add(ps);
            ConfigManager.writePlayerStats();
            firstJoin = true;
        }

        if(ConfigManager.getDiscordConfig().isEnabled()){
            String message = firstJoin ? " joined for the first time!" : " joined the game";
            DiscordBotMain.getInstance().sendMessageDiscord(event.getPlayer().getName() + message);
        }
    }


    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        if(ConfigManager.getDiscordConfig().isEnabled()){
            DiscordBotMain.getInstance().sendMessageDiscord(event.getPlayer().getName() + " left the game");
        }
    }
}

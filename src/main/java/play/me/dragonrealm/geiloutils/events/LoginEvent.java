package play.me.dragonrealm.geiloutils.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import play.me.dragonrealm.geiloutils.configs.ConfigAccess;
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
            ps.setMoney(ConfigAccess.getEconomyConfig().getStartingMoney());
            ps.setUuid(event.getPlayer().getUniqueId().toString());
            ps.setRank(ConfigAccess.getGeneralConfig().getStandardRank());
            ps.setDirectDeposit(true);
            ps.setMutePaymentMsg(false);
            ConfigAccess.getPlayerStatsConfig().getPlayerstats().add(ps);
            ConfigAccess.writePlayerStatsFile();
            firstJoin = true;
        }

        if(ConfigAccess.getDiscordConfig().isEnabled()){
            String message = firstJoin ? " joined for the first time!" : " joined the game";
            DiscordBotMain.getInstance().sendMessageDiscord(event.getPlayer().getName() + message);
        }
    }


    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        if(ConfigAccess.getDiscordConfig().isEnabled()){
            DiscordBotMain.getInstance().sendMessageDiscord(event.getPlayer().getName() + " left the game");
        }
    }
}

package play.me.dragonrealm.geiloutils.economy;

import org.bukkit.entity.Player;
import play.me.dragonrealm.geiloutils.GeiloUtils;
import play.me.dragonrealm.geiloutils.configs.ConfigAccess;
import play.me.dragonrealm.geiloutils.configs.models.PlayerStats;
import play.me.dragonrealm.geiloutils.utils.PlayerUtils;


public class PayUsers implements Runnable {

    @Override
    public void run() {
        GeiloUtils.getLog().info("Payments Starting");
        if(GeiloUtils.getInstanceServer().getOnlinePlayers() != null) { //DON'T TRUST INTELLIJ HERE, THIS IS NULL ON LOADING GAME!
            for (Player player : GeiloUtils.getInstanceServer().getOnlinePlayers()) {
                double baseIncome = ConfigAccess.getEconomyConfig().getBaseTierIncome() * ConfigAccess.getEconomyConfig().getBaseMultiplier();
                double rankIncome = 0;
                PlayerStats stat = PlayerUtils.getPlayerstatByUUID(player.getUniqueId().toString());
                /*if(stat != null) {
                    String rankString = stat.getRank();
                    Rank rank = PermissionUtils.getRankFromName(rankString);
                    if(rank != null){
                        List<Permission> p = rank.getPermList();
                        for(Permission perm: p){
                            if(ConfigurationManager.getEconomyConfig().getPermPaymentMap().containsKey(perm.getName())) {
                                rankIncome = ConfigurationManager.getEconomyConfig().getPermPaymentMap().get(perm.getName());
                                break;
                            }
                        }
                    }
                }*/
                double totalIncome = baseIncome + rankIncome;

                PlayerUtils.addPlayerMoney(player, totalIncome);
                if(!stat.isPaymentMsgMuted()) {
                    player.sendMessage("$" + totalIncome + " has been deposited to your bank account. You can check total amount with /balance");
                }
            }
        }
        GeiloUtils.getLog().info("Payments Completed");
    }
}

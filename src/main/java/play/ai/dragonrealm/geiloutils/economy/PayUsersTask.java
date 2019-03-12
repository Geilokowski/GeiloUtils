package play.ai.dragonrealm.geiloutils.economy;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.FMLCommonHandler;
import play.ai.dragonrealm.geiloutils.GeiloUtils;
import play.ai.dragonrealm.geiloutils.new_configs.models.Permission;
import play.ai.dragonrealm.geiloutils.new_configs.models.Playerstat;
import play.ai.dragonrealm.geiloutils.new_configs.models.Rank;
import play.ai.dragonrealm.geiloutils.new_configs.ConfigAccess;
import play.ai.dragonrealm.geiloutils.utils.PermissionUtils;
import play.ai.dragonrealm.geiloutils.utils.PlayerUtils;

import java.util.List;
import java.util.TimerTask;

public class PayUsersTask extends TimerTask {

    @Override
    public void run() {
        //TODO: Can this be done in less than O(n^2)? Too much complexity!
        GeiloUtils.getLogger().info("Payments Starting");
        if(FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList() != null) { //DON'T TRUST INTELLIJ HERE, THIS IS NULL ON LOADING GAME!
            for (EntityPlayerMP player : FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers()) {
                double baseIncome = ConfigAccess.getEconomyConfig().getBaseTierIncome() * ConfigAccess.getEconomyConfig().getBaseMultiplier();
                double rankIncome = 0;
                Playerstat stat = PlayerUtils.getPlayerstatByUUID(player.getUniqueID().toString());
                if(stat != null) {
                    String rankString = stat.getRank();
                    Rank rank = PermissionUtils.getRankFromName(rankString);
                    if(rank != null){
                        List<Permission> p = rank.getPermList();
                        for(Permission perm: p){
                            if(ConfigAccess.getEconomyConfig().getPermPaymentMap().containsKey(perm.getName())) {
                                rankIncome = ConfigAccess.getEconomyConfig().getPermPaymentMap().get(perm.getName());
                                break;
                            }
                        }
                    }
                }
                double totalIncome = baseIncome + rankIncome;

                if(shouldDirectDeposit(stat)) {
                    PlayerUtils.addPlayerMoney(player, totalIncome);
                    if(!stat.isPaymentMsgMuted()) {
                        player.sendMessage(new TextComponentString("$" + totalIncome + " has been deposited to your bank account. You can check total amount with /balance"));
                    }
                }else {
                    payment(player, totalIncome);
                }
            }
        }
        GeiloUtils.getLogger().info("Payments Completed");
    }

    private boolean shouldDirectDeposit(Playerstat stat) {
        if(stat == null) {
            return false;
        }
        return stat.shouldDirectDeposit();
    }


    private void payment(EntityPlayerMP player, double cash) {
        double money = cash;
        while (money > 0) {

            if (money >= 100.0) {
                PlayerUtils.addItemByName(player, "modcurrency:banknote", 1, 5);
                money -= 100.0;
            } else if (money >= 50.0) {
                PlayerUtils.addItemByName(player, "modcurrency:banknote", 1, 4);
                money -= 50.0;
            } else if (money >= 20.0) {
                PlayerUtils.addItemByName(player, "modcurrency:banknote", 1, 3);
                money -= 20.0;
            } else if (money >= 10.0) {
                PlayerUtils.addItemByName(player, "modcurrency:banknote", 1, 2);
                money -= 10.0;
            } else if (money >= 5.0) {
                PlayerUtils.addItemByName(player, "modcurrency:banknote", 1, 1);
                money -= 5.0;
            } else if (money >= 2.0) {
                PlayerUtils.addItemByName(player, "modcurrency:coin", 1, 5);
                money -= 2.0;
            } else if (money >= 1.0) {
                PlayerUtils.addItemByName(player, "modcurrency:banknote", 1, 0);
                money -= 1.0;
            } else if (money >= 0.25) {
                PlayerUtils.addItemByName(player, "modcurrency:coin", 1, 3);
                money -= 0.25;
            } else if (money >= 0.1) {
                PlayerUtils.addItemByName(player, "modcurrency:coin", 1, 2);
                money -= 0.1;
            } else if (money >= 0.05) {
                PlayerUtils.addItemByName(player, "modcurrency:coin", 1, 1);
                money -= 0.05;
            } else if (money >= 0.01) {
                PlayerUtils.addItemByName(player, "modcurrency:coin", 1, 0);
                money -= 0.01;
            } else if (money > 0.00 && money < 0.01) {
                PlayerUtils.addItemByName(player, "modcurrency:coin", 1, 0);
                money -= 0.01;
            }
        }
    }
}
package play.ai.dragonrealm.geiloutils.economy;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.FMLCommonHandler;
import play.ai.dragonrealm.geiloutils.GeiloUtils;
import play.ai.dragonrealm.geiloutils.config.ConfigurationManager;
import play.ai.dragonrealm.geiloutils.utils.PlayerUtils;

import java.util.TimerTask;

public class PayUsersTask extends TimerTask {

    @Override
    public void run() {
        GeiloUtils.getLogger().info("Payments Starting");
        if(FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList() != null) {
            for (EntityPlayerMP player : FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers()) {
                double baseIncome = ConfigurationManager.getEconomyConfig().getBaseTierIncome() * ConfigurationManager.getEconomyConfig().getBaseMultiplier();
                double rankIncome = 0;
                double totalIncome = baseIncome + rankIncome;

                PlayerUtils.addPlayerMoney(player, totalIncome);
                player.sendMessage(new TextComponentString("Added " + totalIncome + " to your bank account"));
            }
        }
        GeiloUtils.getLogger().info("Payments Completed");
    }
}
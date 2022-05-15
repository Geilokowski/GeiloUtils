package play.ai.dragonrealm.geiloutils.commands.economy;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import play.ai.dragonrealm.geiloutils.GeiloUtils;
import play.ai.dragonrealm.geiloutils.new_configs.containers.PlayerStatsConfig;

public abstract class EconomyBaseCommand extends CommandBase {

    public Double getPlayerBalance(EntityPlayer player){
        return GeiloUtils.getManager().getConfig(PlayerStatsConfig.class).getPlayerBalance(player.getCachedUniqueIdString());
    }

    public void addPlayerBalance(EntityPlayer player, Double value) {
        GeiloUtils.getManager().getConfig(PlayerStatsConfig.class).addPlayerMoney(player.getCachedUniqueIdString(), value);
    }

    public void removePlayerBalance(EntityPlayer player, Double value) {
        GeiloUtils.getManager().getConfig(PlayerStatsConfig.class).removePlayerMoney(player.getCachedUniqueIdString(), value);
    }

    public void messageSender(ICommandSender sender, String message, Object... args){
        String stringMsg = String.format(message, args);
        ITextComponent msg = new TextComponentString("[GeiloEconomy] " + stringMsg);
        sender.sendMessage(msg);
    }

}

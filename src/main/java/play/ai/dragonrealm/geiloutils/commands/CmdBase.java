package play.ai.dragonrealm.geiloutils.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import play.ai.dragonrealm.geiloutils.GeiloUtils;
import play.ai.dragonrealm.geiloutils.new_configs.ConfigAccess;
import play.ai.dragonrealm.geiloutils.new_configs.containers.PlayerStatsConfig;

public abstract class CmdBase extends CommandBase {
    public void sendMsg(ICommandSender receiver, String msg, Object... args){
        String stringMsg = String.format(msg, args);
        ITextComponent textComponent = new TextComponentString(ConfigAccess.getGeneralConfig().getCommandPrefix() + stringMsg);
        receiver.sendMessage(textComponent);
    }

    public void sendMsg(ICommandSender receiver, ITextComponent msg){
        receiver.sendMessage(msg);
    }

    public Double getPlayerBalance(EntityPlayer player){
        return GeiloUtils.getManager().getConfig(PlayerStatsConfig.class).getPlayerBalance(player.getCachedUniqueIdString());
    }

    public void addPlayerBalance(EntityPlayer player, Double value) {
        GeiloUtils.getManager().getConfig(PlayerStatsConfig.class).addPlayerMoney(player.getCachedUniqueIdString(), value);
    }

    public void removePlayerBalance(EntityPlayer player, Double value) {
        GeiloUtils.getManager().getConfig(PlayerStatsConfig.class).removePlayerMoney(player.getCachedUniqueIdString(), value);
    }
}

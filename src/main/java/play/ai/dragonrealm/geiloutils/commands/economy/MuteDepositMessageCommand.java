package play.ai.dragonrealm.geiloutils.commands.economy;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import play.ai.dragonrealm.geiloutils.GeiloUtils;
import play.ai.dragonrealm.geiloutils.commands.CmdBase;
import play.ai.dragonrealm.geiloutils.new_configs.containers.PlayerStatsConfig;
import play.ai.dragonrealm.geiloutils.new_configs.models.Playerstat;
import play.ai.dragonrealm.geiloutils.utils.PlayerUtils;

public class MuteDepositMessageCommand extends CmdBase {

    @Override
    public String getName() {
        return "paymute";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/paymute";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if(sender instanceof EntityPlayer){
            EntityPlayer player = (EntityPlayer) sender;

            Playerstat stat = PlayerUtils.getPlayerstatByUUID(player.getCachedUniqueIdString());
            if(stat != null) {
                stat.setMutePaymentMsg(!stat.isPaymentMsgMuted());
                GeiloUtils.getManager().getConfig(PlayerStatsConfig.class).updatePlayerstat(stat);
                String updated = stat.isPaymentMsgMuted() ? "muted." : "unmuted.";
                sendMsg(sender, "Payment Messages will now be %s", updated);
            }
        }

    }

    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }
}

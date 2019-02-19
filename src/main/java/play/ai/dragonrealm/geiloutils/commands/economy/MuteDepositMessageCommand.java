package play.ai.dragonrealm.geiloutils.commands.economy;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import play.ai.dragonrealm.geiloutils.config.playerstats.Playerstat;
import play.ai.dragonrealm.geiloutils.utils.PlayerUtils;

public class MuteDepositMessageCommand extends CommandBase {

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
                PlayerUtils.updatePlayerstat(stat);
                String updated = stat.isPaymentMsgMuted() ? "muted." : "unmuted.";
                sender.sendMessage(new TextComponentString("[GeiloEconomy] Payment Messages will now be " + updated));
            }
        }

    }

    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return false;
    }
}

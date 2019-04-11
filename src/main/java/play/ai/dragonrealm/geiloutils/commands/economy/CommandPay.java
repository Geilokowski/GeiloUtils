package play.ai.dragonrealm.geiloutils.commands.economy;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import play.ai.dragonrealm.geiloutils.commands.CmdBase;
import play.ai.dragonrealm.geiloutils.utils.ArrayUtils;
import play.ai.dragonrealm.geiloutils.utils.PlayerUtils;

public class CommandPay extends CmdBase {
 
	String usage = "/pay <username> <amount>";
	@Override
	public String getName() {
	    return "pay";
	}

	@Override
	public String getUsage(ICommandSender sender) {
	    return "/pay <username> <amount>";
	}
	
	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
    {
        return true;
    }
	
	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
    {
		int playerIndex = 0;
		if(args.length == playerIndex + 1) {
			if(args[playerIndex].equals("")) {
				return Arrays.asList(server.getOnlinePlayerNames());
			}else {
				return ArrayUtils.startsWith(Arrays.asList(server.getOnlinePlayerNames()), args[playerIndex]);
			}
		}
		
		System.out.println(args[0]);
        return Collections.<String>emptyList();
    }

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if ((args.length == 2) && ((sender instanceof EntityPlayer))) {
			EntityPlayer player = (EntityPlayer)sender;
			if (PlayerUtils.getPlayerByName(args[0]) == null){
				sendMsg(player, "Player not found or not in the same dimension! Try to use tab-completion");
			} else {
				try {
					double moneyToSend = Double.parseDouble(args[1]);
					String name = args[0];
					if (moneyToSend < 0.0D) {
						sendMsg(player, "This is a super advanced mod xD That aint gonna work my friend");
						return;
					}
					if (moneyToSend > getPlayerBalance(player)) {
						sendMsg(player, "You dont have enought money. Your balance is: %s", getPlayerBalance(player));
						return;
					}

					addPlayerBalance(PlayerUtils.getPlayerByName(name), moneyToSend);
					removePlayerBalance(player, moneyToSend);


					sendMsg(player, "You sent %s$ to %s", moneyToSend, name);
					sendMsg(PlayerUtils.getPlayerByName(name), "%s sent you %s$", player.getDisplayNameString(), moneyToSend);
				}
				catch (NumberFormatException e) {
					sendMsg(player, "%s is not a valid number! Please enter something like: 50.0", args[1]);
				}
			}
		}else {
			sendMsg(sender,"Wrong syntax. Usage: %s", usage);
	    }
	}

}

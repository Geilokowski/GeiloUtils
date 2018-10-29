package play.ai.dragonrealm.geiloutils.commands.economy;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.FMLCommonHandler;
import play.ai.dragonrealm.geiloutils.utils.ArrayUtils;
import play.ai.dragonrealm.geiloutils.utils.PlayerUtils;

public class CommandPay extends CommandBase{
 
	String usage = "/pay <username> <amount>";
	@Override
	public String getName() {
	    return "pay";
	}

	@Override
	public String getUsage(ICommandSender sender) {
	    return "With this command you can pay a user with the money you have on your bank account";
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
	        ITextComponent msg = new TextComponentString("[GeiloEconomy] Player not found or not in the same dimension! Try to use tab-completion");
	        player.sendMessage(msg);
	      }
	      else
	      {
	        try
	        {
	          double moneyToSend = Double.parseDouble(args[1]);
	          if (moneyToSend < 0.0D)
	          {
	            ITextComponent msg = new TextComponentString("[GeiloEconomy] This is a super advanced mod xD That aint gonna work my friend");
	            player.sendMessage(msg);
	            return;
	          }
	          if (moneyToSend > PlayerUtils.getPlayerBalance(player)) {
	            ITextComponent msg = new TextComponentString("[GeiloEconomy] You dont have enought money. Your balance is: " + PlayerUtils.getPlayerBalance(player) + "$");
	            player.sendMessage(msg);
	            return;
	          }
	          PlayerUtils.addPlayerMoney(PlayerUtils.getPlayerByName(args[0]), moneyToSend);
	          
	          PlayerUtils.removePlayerMoney(player, moneyToSend);
	          
	          ITextComponent msg = new TextComponentString("[GeiloEconomy] You send " + args[1] + "$ to " + args[0]);
	          ITextComponent msg2 = new TextComponentString("[GeiloEconomy] " + player.getDisplayNameString() + " send you " + args[1] + "$");
	          PlayerUtils.getPlayerByName(args[0]).sendMessage(msg2);
	          player.sendMessage(msg);
	        }
	        catch (NumberFormatException e)
	        {
	          ITextComponent msg = new TextComponentString("[GeiloEconomy] " + args[1] + " is not a valid number. Please enter something like 50.0");
	          player.sendMessage(msg);
	        }
	      }
	    }else {
	    	ITextComponent msg = new TextComponentString("[GeiloEconomy] Wrong sytanx. Usage: " + usage);
	        sender.sendMessage(msg);
	    }
	}

}

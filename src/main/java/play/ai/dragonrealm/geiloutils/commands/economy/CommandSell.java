package play.ai.dragonrealm.geiloutils.commands.economy;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import play.ai.dragonrealm.geiloutils.config.ConfigurationManager;
import play.ai.dragonrealm.geiloutils.utils.ArrayUtils;
import play.ai.dragonrealm.geiloutils.utils.PlayerUtils;

public class CommandSell extends CommandBase{

	//TODO: Add Error Reporting
	String usage = "/sell <daily> <item/list>";
	@Override
	public String getName() {
	    return "sell";
	}

	@Override
	public String getUsage(ICommandSender sender) {
	    return "If you want to sell your items to the server use this command";
	}
	
	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
    {
        return true;
    }

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		//TODO: Rewrite this part and re-enable it
		/**
		if ((args.length == 2) && (args[1].equals("list"))) {
	      ConfigurationManager.syncFromFiles();
	      for (String buyingItem : ConfigurationManager.dailySellItems)
	      {
	        String[] split = buyingItem.split("#");
	        ITextComponent msg = new TextComponentString("[GeiloUtils] We are buying " + split[2] + " for " + split[1] + "$ each");
	        sender.sendMessage(msg);
	      }
	      return;
	    }
		
	    if (((sender instanceof EntityPlayer)) && (args.length >= 1)) {
	      EntityPlayer player = (EntityPlayer)sender;
	      String itemName = player.getHeldItemMainhand().getUnlocalizedName();
	      if (ArrayUtils.isStringInList(ConfigurationManager.dailySellItems, itemName)) {
	        String[] item = ArrayUtils.getStringFromList(ConfigurationManager.dailySellItems, itemName).split("#");
	        
	        // TODO: Sell whole inventory
	        int count = player.getHeldItemMainhand().getCount();
	        System.out.println(count);
	        double moneyToGet = count * Double.parseDouble(item[1]);
	        
	        PlayerUtils.addPlayerMoney(player, moneyToGet);
	        if (ConfigurationManager.currencyIntegration){
	          ITextComponent msg = new TextComponentString("[GeiloEconomy] You sold " + count + " " + player.getHeldItemMainhand().getDisplayName() + " for " + moneyToGet + ". To withdraw your money type /withdraw <amount>");
	          player.sendMessage(msg);
	        }else{
	          ITextComponent msg = new TextComponentString("[GeiloEconomy] You sold " + count + " " + player.getHeldItemMainhand().getDisplayName() + " for " + moneyToGet + ". To pay someone type /withdraw <amount>");
	          player.sendMessage(msg);
	        }
	        PlayerUtils.removeItemFromMainhand(player);
	      }
	      else
	      {
	        ITextComponent msg = new TextComponentString("[GeiloEconomy] We are not buying this item currently. To get a list of what we are buying type /sell daily list");
	        player.sendMessage(msg);
	      }
	    }
	    **/
	  }
	}


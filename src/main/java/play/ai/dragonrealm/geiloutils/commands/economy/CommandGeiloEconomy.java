package play.ai.dragonrealm.geiloutils.commands.economy;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import play.ai.dragonrealm.geiloutils.config.ConfigurationManager;
import play.ai.dragonrealm.geiloutils.new_configs.ConfigManager;

public class CommandGeiloEconomy extends CommandBase{

	String usage = "geiloeconomy <daily> <add/remove> <item> <price>";
	@Override
	public String getName() {
	    return "geiloeconomy";
	}

	@Override
	public String getUsage(ICommandSender sender) {
	    return "This command can be used to add/remove items from the buy list (things the server buys)";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		//TODO: Re-add the ability to sell/buy items from the server
		if ((sender instanceof EntityPlayer))
	    {
	      EntityPlayer player = (EntityPlayer)sender;
	      if (args.length >= 2)
	      {
	    	  if (args[0].equals("daily"))
	    	  {
	          if (args[1].equals("add") && args.length == 3)
	          {
	        	  addSellItem(player.getHeldItemMainhand().getUnlocalizedName() + "#" + args[2] + "#" + player.getHeldItemMainhand().getDisplayName());
	        	  ITextComponent msg = new TextComponentString("[GeiloEconomy] " + player.getHeldItemMainhand().getDisplayName() + " is now getting bought for " + args[2] + "$!");
	        	  sender.sendMessage(msg);
	        	  return;
	          }
	          if ((args[1].equals("remove")) && (args.length == 2))
	          {
	            removeSellItem(player.getHeldItemMainhand().getUnlocalizedName());
	            ITextComponent msg = new TextComponentString("[GeiloEconomy] We are no longer buying " + player.getHeldItemMainhand().getDisplayName());
	            sender.sendMessage(msg);
	            return;
	          }
	        }
	        
	        if ((args[0].equals("config")) && (args.length == 3) && (args[1].equals("currency"))) {
	          try
	          {
	            boolean enable = Boolean.parseBoolean(args[2]);
	            if (enable)
	            {
	              ConfigManager.getEconomyConfig().setGoodOlCurrencyIntegration(true);
	              ConfigurationManager.syncFromFields();
	              ITextComponent msg = new TextComponentString("[GeiloEconomy] Enabled Good ol' Currency Integration");
	              sender.sendMessage(msg);
	              return;
	            }
	            ConfigManager.getEconomyConfig().setGoodOlCurrencyIntegration(false);
	            ConfigurationManager.syncFromFields();
	            ITextComponent msg = new TextComponentString("[GeiloEconomy] Disabled Good ol' Currency Integration");
	            sender.sendMessage(msg);
	            return;
	          }
	          catch (Exception e)
	          {
	            ITextComponent msg = new TextComponentString("[GeiloEconomy] Usage: /geiloeconomy config <setting> <true/false>");
	            sender.sendMessage(msg);
	            return;
	          }
	        }
	      }
	    }
	}
	
	private void addSellItem(String sellItem)
	  {
	    //ConfigurationManager.dailySellItems.add(sellItem);
	    
	    //ConfigurationManager.syncFromFields();
	  }
	  
	  private void removeSellItem(String removeValue)
	  {
		  /**
	    for (int i = 0; i < ConfigurationManager.dailySellItems.size(); i++) {
	      if (((String)ConfigurationManager.dailySellItems.get(i)).startsWith(removeValue)) {
	        ConfigurationManager.dailySellItems.remove(i);
	      }
	    }
	    ConfigurationManager.syncFromFields();
	    **/
	  }
}

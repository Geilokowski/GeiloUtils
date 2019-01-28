package play.ai.dragonrealm.geiloutils;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.*;
import play.ai.dragonrealm.geiloutils.commands.admin.CommandGeiloReload;
import play.ai.dragonrealm.geiloutils.commands.admin.CommandRN;
import play.ai.dragonrealm.geiloutils.commands.ban.CommandGeiloBan;
import play.ai.dragonrealm.geiloutils.commands.discord.CommandMute;
import play.ai.dragonrealm.geiloutils.commands.discord.CommandUnmute;
import play.ai.dragonrealm.geiloutils.commands.discord.CommandVerify;
import play.ai.dragonrealm.geiloutils.commands.economy.CommandBalance;
import play.ai.dragonrealm.geiloutils.commands.economy.CommandDeposit;
import play.ai.dragonrealm.geiloutils.commands.economy.CommandGeiloEconomy;
import play.ai.dragonrealm.geiloutils.commands.economy.CommandPay;
import play.ai.dragonrealm.geiloutils.commands.economy.CommandSell;
import play.ai.dragonrealm.geiloutils.commands.economy.CommandWithdraw;
import play.ai.dragonrealm.geiloutils.commands.ftblib.FTBServerClaim;
import play.ai.dragonrealm.geiloutils.commands.ftblib.FTBTempTeam;
import play.ai.dragonrealm.geiloutils.commands.geilokill.GeiloKill;
import play.ai.dragonrealm.geiloutils.commands.kits.CommandGeiloKit;
import play.ai.dragonrealm.geiloutils.commands.kits.CommandKit;
import play.ai.dragonrealm.geiloutils.commands.permissions.CommandGeiloPerm;
import play.ai.dragonrealm.geiloutils.commands.ranks.CommandGeiloRank;
import play.ai.dragonrealm.geiloutils.commands.ranks.CommandUniRank;
import play.ai.dragonrealm.geiloutils.commands.rtp.CommandRTP;
import play.ai.dragonrealm.geiloutils.config.ConfigurationManager;
import play.ai.dragonrealm.geiloutils.discord.command.CommandProcessor;
import play.ai.dragonrealm.geiloutils.discord.main.DiscordBotMain;
import play.ai.dragonrealm.geiloutils.economy.MoneyDistribution;
import play.ai.dragonrealm.geiloutils.events.ChatEvent;
import play.ai.dragonrealm.geiloutils.events.EventHandlerBlocks;
import play.ai.dragonrealm.geiloutils.events.EventHandlerPlayer;
import play.ai.dragonrealm.geiloutils.utils.CraftingUtils;
import play.ai.dragonrealm.geiloutils.utils.MoneyUtils;

import org.apache.logging.log4j.Logger;

@Mod(modid=GeiloUtils.MODID, name=GeiloUtils.NAME, version=GeiloUtils.VERSION, acceptableRemoteVersions="*", acceptedMinecraftVersions="[1.12.2]")
public class GeiloUtils
{
	  public static final String MODID = "geiloutils";
	  public static final String NAME = "GeiloUtils";
	  public static final String VERSION = "1.3.3";
	  private static Logger logger;
	  
	  @EventHandler
	  public void preInit(FMLPreInitializationEvent event) {
		  ConfigurationManager.init();
		  logger = event.getModLog();
		  MoneyUtils.init();
	  }
	  
	  @EventHandler
	  public void init(FMLInitializationEvent event) {
		  if(ConfigurationManager.getDiscordConfig().isEnabled()) {
			  DiscordBotMain.getInstance().initializeBot();
		  }
	  }
	  
	  @EventHandler
	  public static void serverInit(FMLServerStartingEvent event)
	  {
	    event.registerServerCommand(new CommandRTP());
	    event.registerServerCommand(new CommandGeiloBan());
	    event.registerServerCommand(new CommandGeiloEconomy());
	    event.registerServerCommand(new CommandSell());
	    event.registerServerCommand(new CommandBalance());
	    event.registerServerCommand(new CommandPay());
	    if (ConfigurationManager.getEconomyConfig().isEnabled())
	    {
	      event.registerServerCommand(new CommandDeposit());
	      event.registerServerCommand(new CommandWithdraw());
	    }
	    event.registerServerCommand(new CommandRN());
	    event.registerServerCommand(new CommandGeiloKit());
	    event.registerServerCommand(new CommandGeiloPerm());
	    event.registerServerCommand(new CommandUniRank());
	    event.registerServerCommand(new CommandGeiloRank());
	    event.registerServerCommand(new CommandGeiloReload());
	    event.registerServerCommand(new CommandKit());
	    event.registerServerCommand(new GeiloKill());

	    if(ConfigurationManager.getDiscordConfig().isSingleToMulti()) {
	    	event.registerServerCommand(new CommandMute());
	    	event.registerServerCommand(new CommandUnmute());
		}

		if(ConfigurationManager.getDiscordConfig().isEnabled()) {
	    	event.registerServerCommand(new CommandVerify());
			CommandProcessor.registerCommands();
		}

		//event.registerServerCommand(new FTBServerClaim());
	    //event.registerServerCommand(new FTBTempTeam());
	  }
	  
	  @EventHandler
	  public void postInit(FMLPostInitializationEvent event)
	  {
	    MinecraftForge.EVENT_BUS.register(new EventHandlerBlocks());
	    MinecraftForge.EVENT_BUS.register(new EventHandlerPlayer());
	    MinecraftForge.EVENT_BUS.register(new ChatEvent());

	    MoneyDistribution.enablePaymentTimer();
	    
	    CraftingUtils.removeAllRecipes();
	  }

	  @EventHandler
	  public void serverStop(FMLServerStoppedEvent event){
	  	if(ConfigurationManager.getDiscordConfig().isEnabled()) {
	  		DiscordBotMain.getInstance().sendMessageDiscord("Server Stopped");
		}
	  }
	  
	  public static Logger getLogger()
	  {
	    return logger;
	  }
}

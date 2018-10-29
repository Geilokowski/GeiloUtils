package play.ai.dragonrealm.geiloutils;

import net.minecraft.init.Blocks;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import play.ai.dragonrealm.geiloutils.commands.CommandDebug;
import play.ai.dragonrealm.geiloutils.commands.admin.CommandGeiloReload;
import play.ai.dragonrealm.geiloutils.commands.admin.CommandRN;
import play.ai.dragonrealm.geiloutils.commands.ban.CommandGeiloBan;
import play.ai.dragonrealm.geiloutils.commands.economy.CommandBalance;
import play.ai.dragonrealm.geiloutils.commands.economy.CommandDeposit;
import play.ai.dragonrealm.geiloutils.commands.economy.CommandGeiloEconomy;
import play.ai.dragonrealm.geiloutils.commands.economy.CommandPay;
import play.ai.dragonrealm.geiloutils.commands.economy.CommandSell;
import play.ai.dragonrealm.geiloutils.commands.economy.CommandWithdraw;
import play.ai.dragonrealm.geiloutils.commands.kits.CommandGeiloKit;
import play.ai.dragonrealm.geiloutils.commands.permissions.CommandGeiloPerm;
import play.ai.dragonrealm.geiloutils.commands.ranks.CommandGeiloRank;
import play.ai.dragonrealm.geiloutils.commands.rtp.CommandRTP;
import play.ai.dragonrealm.geiloutils.config.ConfigurationManager;
import play.ai.dragonrealm.geiloutils.config.kits.Kit;
import play.ai.dragonrealm.geiloutils.config.kits.KitItem;
import play.ai.dragonrealm.geiloutils.config.kits.Kits;
import play.ai.dragonrealm.geiloutils.config.permissions.Permission;
import play.ai.dragonrealm.geiloutils.config.playerstats.KitLastUsed;
import play.ai.dragonrealm.geiloutils.config.rtp.ConfigRTP;
import play.ai.dragonrealm.geiloutils.discord.main.GeiloBot;
import play.ai.dragonrealm.geiloutils.events.ChatEvent;
import play.ai.dragonrealm.geiloutils.events.EventHandlerBlocks;
import play.ai.dragonrealm.geiloutils.events.EventHandlerPlayer;
import play.ai.dragonrealm.geiloutils.utils.CraftingUtils;
import play.ai.dragonrealm.geiloutils.utils.MoneyUtils;

import java.util.Date;

import javax.security.auth.login.LoginException;

import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;

@Mod(modid="geiloutils", name="GeiloUtils", version="1.3.3", acceptableRemoteVersions="*", acceptedMinecraftVersions="[1.12.2]")
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
			  GeiloBot.initBot();
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
	    event.registerServerCommand(new CommandGeiloRank());
	    event.registerServerCommand(new CommandGeiloReload());
	  }
	  
	  @EventHandler
	  public void postInit(FMLPostInitializationEvent event)
	  {
	    MinecraftForge.EVENT_BUS.register(new EventHandlerBlocks());
	    MinecraftForge.EVENT_BUS.register(new EventHandlerPlayer());
	    MinecraftForge.EVENT_BUS.register(new ChatEvent());
	    
	    CraftingUtils.removeAllRecipes();
	  }
	  
	  public static Logger getLogger()
	  {
	    return logger;
	  }
}

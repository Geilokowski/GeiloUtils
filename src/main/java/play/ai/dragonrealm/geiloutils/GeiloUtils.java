package play.ai.dragonrealm.geiloutils;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.*;
import play.ai.dragonrealm.geiloutils.commands.admin.CommandGeiloReload;
import play.ai.dragonrealm.geiloutils.commands.admin.CommandRN;
import play.ai.dragonrealm.geiloutils.commands.ban.CommandGeiloBan;
import play.ai.dragonrealm.geiloutils.commands.discord.CommandMute;
import play.ai.dragonrealm.geiloutils.commands.discord.CommandUnmute;
import play.ai.dragonrealm.geiloutils.commands.discord.CommandVerify;
import play.ai.dragonrealm.geiloutils.commands.economy.*;
import play.ai.dragonrealm.geiloutils.commands.ftblib.FTBSetupTeams;
import play.ai.dragonrealm.geiloutils.commands.ftblib.FTBTempTeam;
import play.ai.dragonrealm.geiloutils.commands.ftblib.FTBTransferClaimSellable;
import play.ai.dragonrealm.geiloutils.commands.ftblib.plot.PlotTreeCommand;
import play.ai.dragonrealm.geiloutils.commands.geilokill.GeiloKill;
import play.ai.dragonrealm.geiloutils.commands.kits.CommandGeiloKit;
import play.ai.dragonrealm.geiloutils.commands.kits.CommandKit;
import play.ai.dragonrealm.geiloutils.commands.permissions.CommandGeiloPerm;
import play.ai.dragonrealm.geiloutils.commands.ranks.CommandGeiloRank;
import play.ai.dragonrealm.geiloutils.commands.ranks.CommandUniRank;
import play.ai.dragonrealm.geiloutils.commands.rtp.CommandRTP;
import play.ai.dragonrealm.geiloutils.discord.command.CommandProcessor;
import play.ai.dragonrealm.geiloutils.discord.main.DiscordBotMain;
import play.ai.dragonrealm.geiloutils.economy.MoneyDistribution;
import play.ai.dragonrealm.geiloutils.events.ChatEvent;
import play.ai.dragonrealm.geiloutils.events.EventHandlerBlocks;
import play.ai.dragonrealm.geiloutils.events.EventHandlerPlayer;
import play.ai.dragonrealm.geiloutils.new_configs.ConfigAccess;
import play.ai.dragonrealm.geiloutils.new_configs.FileEnum;
import play.ai.dragonrealm.geiloutils.new_configs.JsonManager;
import play.ai.dragonrealm.geiloutils.new_configs.containers.DiscordCommandConfig;
import play.ai.dragonrealm.geiloutils.new_configs.containers.PlayerStatsConfig;
import play.ai.dragonrealm.geiloutils.utils.CraftingUtils;
import play.ai.dragonrealm.geiloutils.utils.MoneyUtils;

import org.apache.logging.log4j.Logger;

@Mod(modid=GeiloUtils.MODID, name=GeiloUtils.NAME, version=GeiloUtils.VERSION, acceptableRemoteVersions="*", acceptedMinecraftVersions="[1.12.2]")
public class GeiloUtils
{
	/**
	 * GeiloUtils - ConfigRewrite branch
	 * */
	  public static final String MODID = "geiloutils";
	  public static final String NAME = "GeiloUtils";
	  public static final String VERSION = "@VERSION@";
	  private static Logger logger;
	  private static JsonManager manager;
	  
	  @EventHandler
	  public void preInit(FMLPreInitializationEvent event) {
		  logger = event.getModLog();

		  manager = new JsonManager();
		  manager.initializeConfigs();

		  MoneyUtils.init();
	  }
	  
	  @EventHandler
	  public void init(FMLInitializationEvent event) {
		  if(ConfigAccess.getDiscordConfig().isEnabled()) {
			  DiscordBotMain.getInstance().initializeBot();
		  }
	  }

    @EventHandler
    public static void serverInit(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandRTP());
        event.registerServerCommand(new CommandGeiloBan());
        event.registerServerCommand(new CommandGeiloEconomy());
        event.registerServerCommand(new CommandSell());
        event.registerServerCommand(new CommandBalance());
        event.registerServerCommand(new CommandPay());

        if (ConfigAccess.getEconomyConfig().isEnabled()) {
            event.registerServerCommand(new CommandDeposit());
            event.registerServerCommand(new CommandWithdraw());
        }

        event.registerServerCommand(new CommandRN());
        event.registerServerCommand(new CommandGeiloKit());
        event.registerServerCommand(new CommandGeiloPerm());
        //event.registerServerCommand(new CommandUniRank());
        event.registerServerCommand(new CommandGeiloRank());
        event.registerServerCommand(new CommandGeiloReload());
        event.registerServerCommand(new CommandKit());
        event.registerServerCommand(new GeiloKill());

        if (ConfigAccess.getDiscordConfig().isSingleToMulti()) {
            event.registerServerCommand(new CommandMute());
            event.registerServerCommand(new CommandUnmute());
        }

        if (ConfigAccess.getDiscordConfig().isEnabled()) {
            event.registerServerCommand(new CommandVerify());
            CommandProcessor.registerCommands();
            getManager().addToManager(FileEnum.DISCORD_COMMANDS, new DiscordCommandConfig());
            getManager().readFileToRuntime(FileEnum.DISCORD_COMMANDS);
        }

        if (ConfigAccess.getEconomyConfig().isPaymentTimerEnabled()) {
            event.registerServerCommand(new MuteDepositMessageCommand());
        }

        if (Loader.isModLoaded("ftblib") && Loader.isModLoaded("ftbutilities") && ConfigAccess.getFTBConfig().enabled) {
            event.registerServerCommand(new FTBSetupTeams());
            event.registerServerCommand(new FTBTempTeam());
            if (ConfigAccess.getEconomyConfig().isEnabled()) {
                event.registerServerCommand(new FTBTransferClaimSellable());
                event.registerServerCommand(new PlotTreeCommand());
            }
        }
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
	  public void serverStarted(FMLServerStartedEvent event) {
	  	if(ConfigAccess.getDiscordConfig().isEnabled()){
	  		DiscordBotMain.getInstance().sendMessageDiscord("Server Online!");
		}
	  }

	  @EventHandler
	  public void serverStop(FMLServerStoppingEvent event){
	  	if(ConfigAccess.getDiscordConfig().isEnabled()) {
	  		DiscordBotMain.getInstance().sendMessageDiscord("Server Stopping!");
		}
	  }
	  
	  public static Logger getLogger()
	  {
	    return logger;
	  }

	public static JsonManager getManager() {
		return manager;
	}
}

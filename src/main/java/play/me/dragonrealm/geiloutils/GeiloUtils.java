package play.me.dragonrealm.geiloutils;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;
import play.me.dragonrealm.geiloutils.commands.CommandBase;
import play.me.dragonrealm.geiloutils.commands.administration.ReloadConfigsCommand;
import play.me.dragonrealm.geiloutils.commands.discord.MuteCommand;
import play.me.dragonrealm.geiloutils.commands.discord.UnMuteCommand;
import play.me.dragonrealm.geiloutils.commands.discord.VerifyCommand;
import play.me.dragonrealm.geiloutils.commands.other.RandomTPCommand;
import play.me.dragonrealm.geiloutils.configs.ConfigAccess;
import play.me.dragonrealm.geiloutils.configs.FileEnum;
import play.me.dragonrealm.geiloutils.configs.JsonManager;
import play.me.dragonrealm.geiloutils.configs.containers.DiscordCommandConfig;
import play.me.dragonrealm.geiloutils.discord.command.CommandProcessor;
import play.me.dragonrealm.geiloutils.discord.main.DiscordBotMain;
import play.me.dragonrealm.geiloutils.economy.PayUsers;
import play.me.dragonrealm.geiloutils.events.ChatEvent;
import play.me.dragonrealm.geiloutils.events.LoginEvent;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Set;
import java.util.logging.Logger;

public final class GeiloUtils extends JavaPlugin {

    public static final String NAME = "GeiloUtils";
    public static final String VERSION = "@VERSION@";

    private static File configDataFolder;
    private static JsonManager manager;
    private static Logger logger;



    @Override
    public void onEnable() {
        // Plugin startup logic
        configDataFolder = getDataFolder();
        logger = getLogger();

        manager = new JsonManager();
        manager.initializeConfigs();


        registerCommandDynamically(new ReloadConfigsCommand());
        if(ConfigAccess.getRTPConfig().isEnabled()) {
            registerCommandDynamically(new RandomTPCommand());
        }
        getServer().getPluginManager().registerEvents(new LoginEvent(), this);

        if(ConfigAccess.getDiscordConfig().isEnabled()) {
            getServer().getPluginManager().registerEvents(new ChatEvent(), this);
            DiscordBotMain.getInstance().initializeBot();
            CommandProcessor.registerCommands();
            getManager().addToManager(FileEnum.DISCORD_COMMANDS, new DiscordCommandConfig());
            getManager().readFileToRuntime(FileEnum.DISCORD_COMMANDS);
            registerCommandDynamically(new VerifyCommand());

            if(ConfigAccess.getDiscordConfig().isSingleToMulti()) {
                registerCommandDynamically(new MuteCommand());
                registerCommandDynamically(new UnMuteCommand());
            }
        }

        if(ConfigAccess.getEconomyConfig().isEnabled()) {
            if(ConfigAccess.getEconomyConfig().isPaymentTimerEnabled()) {
                getServer().getScheduler().runTaskTimer(this, new PayUsers(), 0, ConfigAccess.getEconomyConfig().getPaymentTimeInSeconds() * 20);
            }
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        manager.writeToFiles();

        if (ConfigAccess.getDiscordConfig().isEnabled()){
            DiscordBotMain.getInstance().sendMessageDiscord("Server Shutting Down!");
            DiscordBotMain.getInstance().shutdown();
        }
    }

    public static File getConfigDataFolder() {
        return configDataFolder;
    }

    public static JsonManager getManager() {
        return manager;
    }

    public static Logger getLog() {
        return logger;
    }

    public static Server getInstanceServer() {
        return getPlugin(GeiloUtils.class).getServer();
    }

    public void registerCommandDynamically(CommandBase base){
        try {
            final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");

            bukkitCommandMap.setAccessible(true);
            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());

            commandMap.register(base.getName(), base);
            getLog().info("Sucessfully registered command -" + base.getName());
        } catch(IllegalAccessException | NoSuchFieldException e) {
            getLog().info("Unable to register command -" + base.getName());
        }
    }
}
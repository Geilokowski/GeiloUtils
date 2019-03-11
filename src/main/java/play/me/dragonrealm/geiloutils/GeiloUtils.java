package play.me.dragonrealm.geiloutils;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;
import play.me.dragonrealm.geiloutils.commands.CommandBase;
import play.me.dragonrealm.geiloutils.commands.discord.VerifyCommand;
import play.me.dragonrealm.geiloutils.configs.ConfigManager;
import play.me.dragonrealm.geiloutils.configs.FileEnum;
import play.me.dragonrealm.geiloutils.configs.JsonManager;
import play.me.dragonrealm.geiloutils.configs.containers.DiscordCommandConfig;
import play.me.dragonrealm.geiloutils.discord.command.CommandProcessor;
import play.me.dragonrealm.geiloutils.discord.main.DiscordBotMain;
import play.me.dragonrealm.geiloutils.events.ChatEvent;
import play.me.dragonrealm.geiloutils.events.LoginEvent;

import java.io.File;
import java.lang.reflect.Field;
import java.util.logging.Logger;

public final class GeiloUtils extends JavaPlugin {

    public static final String NAME = "GeiloUtils";
    public static final String VERSION = "@VERSION@";

    private static File configDataFolder;
    private static JsonManager manager;
    private static Logger logger;
    //private static Server server;

    @Override
    public void onEnable() {
        // Plugin startup logic
        configDataFolder = getDataFolder();
        logger = getLogger();
        //server = getServer();
        manager = new JsonManager();
        manager.initializeConfigs();

        if(ConfigManager.getDiscordConfig().isEnabled()) {
            getServer().getPluginManager().registerEvents(new ChatEvent(), this);
            getServer().getPluginManager().registerEvents(new LoginEvent(), this);
            DiscordBotMain.getInstance().initializeBot();
            CommandProcessor.registerCommands();
            getManager().addToManager(FileEnum.DISCORD_COMMANDS, new DiscordCommandConfig());
            getManager().readFileToRuntime(FileEnum.DISCORD_COMMANDS.name());
            registerCommandDynamically(new VerifyCommand());
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
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
        //return server;
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

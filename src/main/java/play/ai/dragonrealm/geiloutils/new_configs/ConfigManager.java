package play.ai.dragonrealm.geiloutils.new_configs;

import play.ai.dragonrealm.geiloutils.GeiloUtils;
import play.ai.dragonrealm.geiloutils.config.ConfigurationManager;
import play.ai.dragonrealm.geiloutils.new_configs.discord.DiscordCommandConfig;
import play.ai.dragonrealm.geiloutils.new_configs.discord.DiscordConfig;
import play.ai.dragonrealm.geiloutils.new_configs.discord.IDiscordConfigs;
import play.ai.dragonrealm.geiloutils.new_configs.economy.IEcononmyConfig;

public class ConfigManager {

    public static IDiscordConfigs getDiscordConfig(){
        return (IDiscordConfigs) GeiloUtils.getManager().getConfig(DiscordConfig.MANAGER_NAME);
    }

    public static void writeDiscordFile(){
        GeiloUtils.getManager().writeToFile(DiscordConfig.MANAGER_NAME);
    }

    public static IEcononmyConfig getEconomyConfig(){
        return ConfigurationManager.getEconomyConfig();
    }

    public static DiscordCommandConfig getCommandConfig(){
        return (DiscordCommandConfig) GeiloUtils.getManager().getConfig(DiscordCommandConfig.DISCORD_COMMAND_NAME);
    }




}

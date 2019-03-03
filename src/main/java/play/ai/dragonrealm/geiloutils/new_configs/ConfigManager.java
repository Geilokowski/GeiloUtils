package play.ai.dragonrealm.geiloutils.new_configs;

import play.ai.dragonrealm.geiloutils.GeiloUtils;
import play.ai.dragonrealm.geiloutils.config.ConfigurationManager;
import play.ai.dragonrealm.geiloutils.new_configs.containers.*;
import play.ai.dragonrealm.geiloutils.new_configs.interfaces.IDiscordConfigs;
import play.ai.dragonrealm.geiloutils.new_configs.interfaces.IEcononmyConfig;
import play.ai.dragonrealm.geiloutils.new_configs.interfaces.IGeneralConfig;
import play.ai.dragonrealm.geiloutils.new_configs.interfaces.IRandomTeleportConfig;

public class ConfigManager {

    /*
     * Discord Files
     */
    public static IDiscordConfigs getDiscordConfig(){
        return (IDiscordConfigs) GeiloUtils.getManager().getConfig(FileEnum.DISCORD_GENERAL);
    }
    public static void writeDiscordFile(){
        GeiloUtils.getManager().writeToFile(FileEnum.DISCORD_GENERAL);
    }

    /*
     * Discord Commands
     */
    public static DiscordCommandConfig getCommandConfig(){
        return (DiscordCommandConfig) GeiloUtils.getManager().getConfig(FileEnum.DISCORD_COMMANDS);
    }
    public static void writeDiscordCommandFile(){
        GeiloUtils.getManager().writeToFile(FileEnum.DISCORD_COMMANDS);
    }

    /*
     * Economy Files
     */
    public static IEcononmyConfig getEconomyConfig(){
        return (IEcononmyConfig) GeiloUtils.getManager().getConfig(FileEnum.ECONOMY);
    }
    public static void writeEconomyConfig(){
        GeiloUtils.getManager().writeToFile(FileEnum.ECONOMY);
    }

    /*
     * General Files
     */
    public static IGeneralConfig getGeneralConfig(){
        return (IGeneralConfig) GeiloUtils.getManager().getConfig(FileEnum.GENERAL);
    }
    public static void writeGeneralFile(){
        GeiloUtils.getManager().writeToFile(FileEnum.GENERAL);
    }

    /*
     * RTP Files
     */
    public static IRandomTeleportConfig getRTPConfig(){
        return (IRandomTeleportConfig) GeiloUtils.getManager().getConfig(FileEnum.RTP);
    }
    public static void writeTeleportFile(){
        GeiloUtils.getManager().writeToFile(FileEnum.RTP);
    }

    /*
     * FTB Utils Files
     */
    public static FTBUtilsConfig getFTBConfig(){
        return (FTBUtilsConfig) GeiloUtils.getManager().getConfig(FileEnum.FTB_UTILITIES);
    }
    public static void writeFTBUtilsFile(){
        GeiloUtils.getManager().writeToFile(FileEnum.FTB_UTILITIES);
    }


}

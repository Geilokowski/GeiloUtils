package play.ai.dragonrealm.geiloutils.new_configs;

import play.ai.dragonrealm.geiloutils.GeiloUtils;
import play.ai.dragonrealm.geiloutils.new_configs.containers.*;
import play.ai.dragonrealm.geiloutils.new_configs.interfaces.*;

public class ConfigAccess {
    /*
     * Multiworld Files
     */
    public static void writeMultiworldFile(){GeiloUtils.getManager().writeToFile(FileEnum.MULTIWORLD);}
    public static MultiworldConfig getMultiworldConfig(){ return (MultiworldConfig) GeiloUtils.getManager().getConfig(FileEnum.MULTIWORLD); }

    /*
     * Discord Files
     */
    public static IDiscordConfigs getDiscordConfig(){ return (IDiscordConfigs) GeiloUtils.getManager().getConfig(FileEnum.DISCORD_GENERAL); }
    public static void writeDiscordFile(){
        GeiloUtils.getManager().writeToFile(FileEnum.DISCORD_GENERAL);
    }

    /*
     * Discord Commands
     */
    public static DiscordCommandConfig getCommandConfig(){ return (DiscordCommandConfig) GeiloUtils.getManager().getConfig(FileEnum.DISCORD_COMMANDS); }
    public static void writeDiscordCommandFile(){
        GeiloUtils.getManager().writeToFile(FileEnum.DISCORD_COMMANDS);
    }

    /*
     * Economy Files
     */
    public static IEcononmyConfig getEconomyConfig(){ return (IEcononmyConfig) GeiloUtils.getManager().getConfig(FileEnum.ECONOMY); }
    public static void writeEconomyConfig(){
        GeiloUtils.getManager().writeToFile(FileEnum.ECONOMY);
    }

    /*
     * General Files
     */
    public static IGeneralConfig getGeneralConfig(){ return (IGeneralConfig) GeiloUtils.getManager().getConfig(FileEnum.GENERAL); }
    public static void writeGeneralFile(){
        GeiloUtils.getManager().writeToFile(FileEnum.GENERAL);
    }

    /*
     * RTP Files
     */
    public static IRandomTeleportConfig getRTPConfig(){ return (IRandomTeleportConfig) GeiloUtils.getManager().getConfig(FileEnum.RTP); }
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

    /*
     * Ranks Files
     */
    public static IRanksConfig getRanksConfig(){
        return (IRanksConfig) GeiloUtils.getManager().getConfig(FileEnum.RANKS);
    }
    public static void writeRanksFile(){
        GeiloUtils.getManager().writeToFile(FileEnum.RANKS);
    }

    /*
     * Banned Blocks
     */
    public static IBannedBlocksConfig getBannedBlocksConfig(){
        return (IBannedBlocksConfig) GeiloUtils.getManager().getConfig(FileEnum.BLOCK_BANS);
    }
    public static void writeBannedBlocksFile(){
        GeiloUtils.getManager().writeToFile(FileEnum.BLOCK_BANS);
    }

    /*
     * PlayerStats
     */
    public static IPlayerStatsConfig getPlayerStatsConfig(){
        return (IPlayerStatsConfig) GeiloUtils.getManager().getConfig(FileEnum.PLAYER_STATS);
    }
    public static void writePlayerStatsFile(){
        GeiloUtils.getManager().writeToFile(FileEnum.PLAYER_STATS);
    }

    /*
     * PlayerStats
     */
    public static IPermissionConfig getPermissionConfig(){
        return (IPermissionConfig) GeiloUtils.getManager().getConfig(FileEnum.PERMISSIONS);
    }
    public static void writePermissionFile(){
        GeiloUtils.getManager().writeToFile(FileEnum.PERMISSIONS);
    }

    /*
     * Kits
     */
    public static IKitConfig getKitConfig(){
        return (IKitConfig) GeiloUtils.getManager().getConfig(FileEnum.KIT);
    }
    public static void writeKitFile(){
        GeiloUtils.getManager().writeToFile(FileEnum.KIT);
    }

}

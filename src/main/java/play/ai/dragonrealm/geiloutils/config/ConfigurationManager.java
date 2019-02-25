package play.ai.dragonrealm.geiloutils.config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.minecraftforge.fml.common.Loader;
import play.ai.dragonrealm.geiloutils.GeiloUtils;
import play.ai.dragonrealm.geiloutils.config.discord.ConfigDiscord;
import play.ai.dragonrealm.geiloutils.config.economy.ConfigEconomy;
//import play.ai.dragonrealm.geiloutils.config.ftbutils.ConfigFTBUtilsInter;
import play.ai.dragonrealm.geiloutils.config.ftbutils.ConfigFTBUtilsInter;
import play.ai.dragonrealm.geiloutils.config.geiloban.BannedBlock;
import play.ai.dragonrealm.geiloutils.config.geiloban.BannedBlocks;
import play.ai.dragonrealm.geiloutils.config.general.ConfigGeneral;
import play.ai.dragonrealm.geiloutils.config.kits.Kits;
import play.ai.dragonrealm.geiloutils.config.permissions.Permissions;
import play.ai.dragonrealm.geiloutils.config.playerstats.KitLastUsed;
import play.ai.dragonrealm.geiloutils.config.playerstats.Playerstats;
import play.ai.dragonrealm.geiloutils.config.ranks.Rank;
import play.ai.dragonrealm.geiloutils.config.ranks.Ranks;
import play.ai.dragonrealm.geiloutils.config.rtp.ConfigRTP;
import play.ai.dragonrealm.geiloutils.discord.utils.DiscordRank;
import play.ai.dragonrealm.geiloutils.discord.utils.UserRanks;

public class ConfigurationManager
{
	public static String configLocation = Loader.instance().getConfigDir() + File.separator + GeiloUtils.MODID;
	static private File fileBannedBlocks = new File(Loader.instance().getConfigDir() + "/GeiloUtils", "BannedBlocks.json");
	static private File fileGenral = new File(Loader.instance().getConfigDir() + "/GeiloUtils", "General.json");
	static private File fileKits = new File(Loader.instance().getConfigDir() + "/GeiloUtils", "Kits.json");
	static private File filePermissions = new File(Loader.instance().getConfigDir() + "/GeiloUtils", "Permissions.json");
	static private File fileRanks = new File(Loader.instance().getConfigDir() + "/GeiloUtils", "Ranks.json");
	static private File filePlayerstats = new File(Loader.instance().getConfigDir() + "/GeiloUtils", "Playerstats.json");
	static private File fileRTP = new File(Loader.instance().getConfigDir() + "/GeiloUtils", "rtp.json");
	static private File fileDiscord = new File(Loader.instance().getConfigDir() + "/GeiloUtils", "Discord.json");
	static private File fileEconomy = new File(Loader.instance().getConfigDir() + "/GeiloUtils", "economy.json");
	static private File fileFTBUtils = new File(configLocation, "ftbutilities.json");
	
	private static BannedBlocks bannedBlocksConfig;
	private static Kits kitsConfig;
	private static Permissions permissionsConfig;
	private static Playerstats playerstats;
	private static Ranks rankConfig;
	private static ConfigRTP rtpConfig;
	private static ConfigDiscord discordConfig;
	private static ConfigEconomy economyConfig;
	private static ConfigGeneral generalConfig;
	private static ConfigFTBUtilsInter ftbUtilsInter;
  
	private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	public static void init() {
		File configPath = new File(Loader.instance().getConfigDir() + "/GeiloUtils");
		boolean firstStart = !configPath.exists();
		configPath.mkdirs();
		try {
			fileBannedBlocks.createNewFile();
			fileKits.createNewFile();
			filePermissions.createNewFile();
			fileRanks.createNewFile();
			filePlayerstats.createNewFile();
			fileGenral.createNewFile();
			fileRTP.createNewFile();
			fileDiscord.createNewFile();
			fileEconomy.createNewFile();

			if(fileFTBUtils.createNewFile() || fileFTBUtils.length() == 0) {
				writeToFile(fileFTBUtils, gson.toJson(ConfigFTBUtilsInter.getDefaultConfig()));
			}


		} catch (IOException e) {
			e.printStackTrace();
		}

		if(firstStart) {
			writeToFile(fileRTP, gson.toJson(defaultRTPConfig()));
			writeToFile(fileBannedBlocks, gson.toJson(defaultBannedBlocks()));
			writeToFile(fileRanks, gson.toJson(defaultRanks()));
			writeToFile(filePermissions, gson.toJson(defaultPermissions()));
			writeToFile(filePlayerstats, gson.toJson(defaultPlayerstats()));
			writeToFile(fileKits, gson.toJson(defaultKits()));
			writeToFile(fileDiscord, gson.toJson(defaultDiscordConfig()));
			writeToFile(fileEconomy, gson.toJson(defaultEconomyConfig()));
			writeToFile(fileGenral, gson.toJson(defaultGeneralConfig()));
			
			syncFromFiles();
		}else {
			syncFromFiles();
		}
	}
	
	public static void syncFromFiles()
	{
		bannedBlocksConfig = gson.fromJson(readFromFile(fileBannedBlocks), BannedBlocks.class);
		kitsConfig = gson.fromJson(readFromFile(fileKits), Kits.class);
		permissionsConfig = gson.fromJson(readFromFile(filePermissions), Permissions.class);
		playerstats= gson.fromJson(readFromFile(filePlayerstats), Playerstats.class);
		rankConfig = gson.fromJson(readFromFile(fileRanks), Ranks.class);
		rtpConfig = gson.fromJson(readFromFile(fileRTP), ConfigRTP.class);
		discordConfig = gson.fromJson(readFromFile(fileDiscord), ConfigDiscord.class);
		economyConfig  = gson.fromJson(readFromFile(fileEconomy), ConfigEconomy.class);
		generalConfig = gson.fromJson(readFromFile(fileGenral), ConfigGeneral.class);
		ftbUtilsInter = gson.fromJson(readFromFile(fileFTBUtils), ConfigFTBUtilsInter.class);
	}
	  
	public static void syncFromFields()
	{
		writeToFile(fileRTP, gson.toJson(rtpConfig));
		writeToFile(fileBannedBlocks, gson.toJson(bannedBlocksConfig));
		writeToFile(fileRanks, gson.toJson(rankConfig));
		writeToFile(filePermissions, gson.toJson(permissionsConfig));
		writeToFile(filePlayerstats, gson.toJson(playerstats));
		writeToFile(fileKits, gson.toJson(kitsConfig));
		writeToFile(fileDiscord, gson.toJson(discordConfig));
		writeToFile(fileEconomy, gson.toJson(economyConfig));
		writeToFile(fileGenral, gson.toJson(generalConfig));
		//writeToFile(fileFTBUtils, gson.toJson(ftbUtilsInter));
	}
	
	private static ConfigGeneral defaultGeneralConfig() {
		ConfigGeneral defaultGeneralConfig = new ConfigGeneral();
		defaultGeneralConfig.setCommandPrefix("[GeiloUtils] ");
		return defaultGeneralConfig;
	}
	
	private static ConfigEconomy defaultEconomyConfig() {
		ConfigEconomy defaultEconomyConfig = new ConfigEconomy();
		defaultEconomyConfig.setEnabled(true);
		defaultEconomyConfig.setGoodOlCurrencyIntegration(true);
		defaultEconomyConfig.setStartingMoney(50);
		HashMap<String, Integer> payements = new HashMap<>();
		defaultEconomyConfig.setPermPaymentMap(payements);
		return defaultEconomyConfig;
	}
	
	private static ConfigDiscord defaultDiscordConfig() {
		ConfigDiscord defaultDiscordConfig = new ConfigDiscord();
		defaultDiscordConfig.setEnabled(false);
		defaultDiscordConfig.setSingleToMulti(false);
		defaultDiscordConfig.setMinecraftChatPrefix("§3[§6Discord§3] §r");
		defaultDiscordConfig.setChannelIDCommands("");
		defaultDiscordConfig.setChannelIDRelay("");
		defaultDiscordConfig.setToken("");
		defaultDiscordConfig.setDiscordCommandPrefix("!");
		defaultDiscordConfig.setDiscordChatPrefix("[%s] >> ");
		ArrayList<String> color = new ArrayList<>();
		color.add("§c");
		color.add("§e");
		color.add("§2");
		color.add("§a");
		color.add("§b");
		color.add("§3");
		color.add("§9");
		defaultDiscordConfig.setValidColors(color);
		defaultDiscordConfig.setServerIP("dragonrealm.me");
        HashMap<Long, DiscordRank> empty = new HashMap<>();
		defaultDiscordConfig.setAdminMap(empty);
		ArrayList<UserRanks> ranks = new ArrayList<>();
		defaultDiscordConfig.setDiscordRankIntegration(ranks);
		defaultDiscordConfig.setSupporterRank("");
		return defaultDiscordConfig;
	}
	
	private static Kits defaultKits() {
		Kits kits = new Kits();
		return kits;
	}
	
	private static Ranks defaultRanks() {
		Ranks ranks = new Ranks();
		ranks.getRanks().add(new Rank(defaultGeneralConfig().getStandartRank()));
		return ranks;
	}
	
	private static Playerstats defaultPlayerstats(){
		Playerstats defaultPS = new Playerstats();
		return defaultPS;
	}
	
	private static Permissions defaultPermissions() {
		Permissions defaultPerms = new Permissions();
		return defaultPerms;
	}
	
	private static BannedBlocks defaultBannedBlocks() {
		BannedBlocks defaultBanendBlockConfig = new BannedBlocks();
		defaultBanendBlockConfig.getBannedBlocks().add(new BannedBlock("rftools:builder", "all", "0"));
		return defaultBanendBlockConfig;
	}
	
	private static ConfigRTP defaultRTPConfig() {
		ConfigRTP configRTP = new ConfigRTP();
		configRTP.setMinY(60);
		configRTP.setEnabled(true);
		configRTP.setRadius(10000);
		configRTP.setMaxTries(-1);
		
		return configRTP;
	}
	
	private static void writeToFile(File file, String content) {
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(file.getPath()));
			writer.write(content);
		     
		    writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static String readFromFile(File file) {
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));
			
			//TODO: Make this look better
			String st; 
			String tmp = "";
			while ((st = br.readLine()) != null) {
				tmp = tmp + st; 
			} 
		
			br.close();
			return tmp;
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		return null;
	}

	public static BannedBlocks getBannedBlocksConfig() {
		return bannedBlocksConfig;
	}
	
	public static void setBannedBlocksConfig(BannedBlocks bannedBlocksConfig) {
		ConfigurationManager.bannedBlocksConfig = bannedBlocksConfig;
	}
	
	public static Kits getKitsConfig() {
		return kitsConfig;
	}
	
	public static void setKitsConfig(Kits kitsConfig) {
		ConfigurationManager.kitsConfig = kitsConfig;
	}
	
	public static Permissions getPermissionsConfig() {
		return permissionsConfig;
	}
	
	public static void setPermissionsConfig(Permissions permissionsConfig) {
		ConfigurationManager.permissionsConfig = permissionsConfig;
	}
	
	public static Playerstats getPlayerstats() {
		return playerstats;
	}
	
	public static void setPlayerstats(Playerstats playerstats) {
		ConfigurationManager.playerstats = playerstats;
	}
	
	public static Ranks getRankConfig() {
		return rankConfig;
	}
	
	public static void setRankConfig(Ranks rankConfig) {
		ConfigurationManager.rankConfig = rankConfig;
	}
	
	public static ConfigRTP getRtpConfig() {
		return rtpConfig;
	}
	
	public static void setRtpConfig(ConfigRTP rtpConfig) {
		ConfigurationManager.rtpConfig = rtpConfig;
	}
	
	public static ConfigDiscord getDiscordConfig() {
		return discordConfig;
	}
	
	public static void setDiscordConfig(ConfigDiscord discordConfig) {
		ConfigurationManager.discordConfig = discordConfig;
	}
	
	public static ConfigEconomy getEconomyConfig() {
		return economyConfig;
	}
	
	public static void setEconomyConfig(ConfigEconomy economyConfig) {
		ConfigurationManager.economyConfig = economyConfig;
	}

	public static ConfigGeneral getGeneralConfig() {
		return generalConfig;
	}

	public static void setGeneralConfig(ConfigGeneral generalConfig) {
		ConfigurationManager.generalConfig = generalConfig;
	}

	public static ConfigFTBUtilsInter getFtbUtilsInter() {
		return ftbUtilsInter;
	}

	public static void setFtbUtilsInter(ConfigFTBUtilsInter ftbUtilsInter){
		ConfigurationManager.ftbUtilsInter = ftbUtilsInter;
	}
}

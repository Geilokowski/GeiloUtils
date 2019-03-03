package play.ai.dragonrealm.geiloutils.config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.minecraftforge.fml.common.Loader;
import play.ai.dragonrealm.geiloutils.config.geiloban.BannedBlock;
import play.ai.dragonrealm.geiloutils.config.geiloban.BannedBlocks;
import play.ai.dragonrealm.geiloutils.config.kits.Kits;
import play.ai.dragonrealm.geiloutils.config.permissions.Permissions;
import play.ai.dragonrealm.geiloutils.config.playerstats.Playerstats;
import play.ai.dragonrealm.geiloutils.config.ranks.Rank;
import play.ai.dragonrealm.geiloutils.config.ranks.Ranks;
import play.ai.dragonrealm.geiloutils.new_configs.ConfigManager;

public class ConfigurationManager
{
	static private File fileBannedBlocks = new File(Loader.instance().getConfigDir() + "/GeiloUtils", "BannedBlocks.json");
	static private File fileKits = new File(Loader.instance().getConfigDir() + "/GeiloUtils", "Kits.json");
	static private File filePermissions = new File(Loader.instance().getConfigDir() + "/GeiloUtils", "Permissions.json");
	static private File fileRanks = new File(Loader.instance().getConfigDir() + "/GeiloUtils", "Ranks.json");
	static private File filePlayerstats = new File(Loader.instance().getConfigDir() + "/GeiloUtils", "Playerstats.json");
	
	private static BannedBlocks bannedBlocksConfig;
	private static Kits kitsConfig;
	private static Permissions permissionsConfig;
	private static Playerstats playerstats;
	private static Ranks rankConfig;
  
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



		} catch (IOException e) {
			e.printStackTrace();
		}

		if(firstStart) {
			writeToFile(fileBannedBlocks, gson.toJson(defaultBannedBlocks()));
			writeToFile(fileRanks, gson.toJson(defaultRanks()));
			writeToFile(filePermissions, gson.toJson(defaultPermissions()));
			writeToFile(filePlayerstats, gson.toJson(defaultPlayerstats()));
			writeToFile(fileKits, gson.toJson(defaultKits()));
			
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
	}
	  
	public static void syncFromFields()
	{
		writeToFile(fileBannedBlocks, gson.toJson(bannedBlocksConfig));
		writeToFile(fileRanks, gson.toJson(rankConfig));
		writeToFile(filePermissions, gson.toJson(permissionsConfig));
		writeToFile(filePlayerstats, gson.toJson(playerstats));
		writeToFile(fileKits, gson.toJson(kitsConfig));
	}

	
	private static Kits defaultKits() {
		Kits kits = new Kits();
		return kits;
	}
	
	private static Ranks defaultRanks() {
		Ranks ranks = new Ranks();
		ranks.getRanks().add(new Rank(ConfigManager.getGeneralConfig().getStandardRank()));
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

	public static Kits getKitsConfig() {
		return kitsConfig;
	}
	
	public static Permissions getPermissionsConfig() {
		return permissionsConfig;
	}
	
	public static Playerstats getPlayerstats() {
		return playerstats;
	}
	
	public static Ranks getRankConfig() {
		return rankConfig;
	}



}

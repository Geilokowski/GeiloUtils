package play.ai.dragonrealm.geiloutils.utils;

import java.util.ArrayList;
import java.util.List;

import play.ai.dragonrealm.geiloutils.config.ConfigurationManager;
import play.ai.dragonrealm.geiloutils.config.kits.Kit;
import play.ai.dragonrealm.geiloutils.config.kits.KitItem;
import play.ai.dragonrealm.geiloutils.config.permissions.Permission;
import play.ai.dragonrealm.geiloutils.internals.Statics;

public class KitUtils {
	public static boolean doesKitExist(String kitName) {
		for(Kit kit : ConfigurationManager.getKitsConfig().getKits()) {
			if(kit.getName().equals(kitName)) {
				return true;
			}
		}
		
		return false;
	}
	
	public static List<String> getKitNameList() {
		List<String> nameList = new ArrayList<String>();
		for(Kit kit : ConfigurationManager.getKitsConfig().getKits()) {
			nameList.add(kit.getName());
		}
		return nameList;
	}
	
	public static String removeKitByName(String name) {
		for(int i = 0; i < ConfigurationManager.getKitsConfig().getKits().size(); i++) {
			if(ConfigurationManager.getKitsConfig().getKits().get(i).getName().equals(name)) {
				Kit tmp = ConfigurationManager.getKitsConfig().getKits().remove(i);
				ConfigurationManager.syncFromFields();
				return tmp.getName();
			}
		}
		
		return "";
	}
	
	public static Kit getKitByName(String name){
		for(Kit kit : ConfigurationManager.getKitsConfig().getKits()) {
			if(kit.getName().equals(name)) {
				return kit;
			}
		}
		
		return null;
	}
	
	public static int getKitCount() {
		return ConfigurationManager.getKitsConfig().getKits().size();
	}
}

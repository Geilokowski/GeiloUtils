package play.ai.dragonrealm.geiloutils.utils;

import java.util.ArrayList;
import java.util.List;

import play.ai.dragonrealm.geiloutils.config.ConfigurationManager;
import play.ai.dragonrealm.geiloutils.config.kits.Kit;
import play.ai.dragonrealm.geiloutils.config.kits.KitItem;
import play.ai.dragonrealm.geiloutils.config.permissions.Permission;
import play.ai.dragonrealm.geiloutils.internals.Statics;

public class KitUtils {
	public static boolean doesKitHaveItem(Kit kit, KitItem item){
		for(KitItem ki : kit.getItems()){
			if(item.getRegistryName().equals(ki.getRegistryName()) && item.getMetadata() == ki.getMetadata()){
				return true;
			}
		}

		return false;
	}

	public static Kit removeItemFromKit(Kit kit, KitItem item){
		for(KitItem ki : kit.getItems()){
			if(item.getRegistryName().equals(ki.getRegistryName()) && item.getMetadata() == ki.getMetadata()){
				kit.getItems().remove(ki);
			}
		}

		return kit;
	}

	public static Kit removePermissionFromKit(Kit kit, Permission permission){
		kit.getPermissionList().stream().filter(p -> p.getName().equals(permission.getName())).findFirst().ifPresent(p -> kit.getPermissionList().remove(p));

		return kit;
	}

    public static boolean doesKitHavePermission(Kit k, Permission perm){
        return k.getPermissionList().stream().anyMatch(ps -> ps.getName().equals(perm.getName()));
    }

	public static void updateKit(Kit kit){
		ConfigurationManager.getKitsConfig().getKits().stream().filter(k -> k.getName().equals(kit.getName())).findFirst().ifPresent(k -> ConfigurationManager.getKitsConfig().getKits().remove(k));
		ConfigurationManager.getKitsConfig().getKits().add(kit);
		ConfigurationManager.syncFromFields();
	}

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

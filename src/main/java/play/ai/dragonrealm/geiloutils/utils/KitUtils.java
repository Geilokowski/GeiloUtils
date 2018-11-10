package play.ai.dragonrealm.geiloutils.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import play.ai.dragonrealm.geiloutils.config.ConfigurationManager;
import play.ai.dragonrealm.geiloutils.config.kits.Kit;
import play.ai.dragonrealm.geiloutils.config.kits.KitItem;
import play.ai.dragonrealm.geiloutils.config.permissions.Permission;
import play.ai.dragonrealm.geiloutils.config.playerstats.KitLastUsed;
import play.ai.dragonrealm.geiloutils.config.playerstats.Playerstat;
import play.ai.dragonrealm.geiloutils.config.ranks.Rank;
import play.ai.dragonrealm.geiloutils.internals.Statics;

public class KitUtils {
    public static void deliverKit(EntityPlayer player, Kit kit){
        for(KitItem ki : kit.getItems()){
            PlayerUtils.addItemByName(player, ki.getRegistryName(), ki.getCount(), ki.getMetadata());
        }

        updateLastUsed(player, kit);
    }

    private static void updateLastUsed(EntityPlayer player, Kit kit){
        Date date = new Date();
        Playerstat ps = PlayerUtils.getPlayerstatByUUID(player.getCachedUniqueIdString());
        for(KitLastUsed klu : ps.getKitLastUsed()){
            if(klu.getKitname().equals(kit.getName())){
                ps.getKitLastUsed().remove(klu);
                klu.setLastUsed(date);
                ps.getKitLastUsed().add(klu);
                PlayerUtils.updatePlayerstat(ps);
                return;
            }
        }

        KitLastUsed klu = new KitLastUsed(kit.getName(), date);
        ps.getKitLastUsed().add(klu);
        PlayerUtils.updatePlayerstat(ps);

        //ConfigurationManager.syncFromFields();
    }

	public static boolean canPlayerUseKit(EntityPlayer player, Kit kit){
		Rank rank = PermissionUtils.getRankFromPlayer(player);
		if(kit.getPermissionList().isEmpty()){
			return !isCooldownStillActive(player, kit);
		}

		for(Permission perm : kit.getPermissionList()){
			if(PermissionUtils.doesRankHavePermission(rank, perm)){
				return !isCooldownStillActive(player, kit);
			}
		}

		return false;
	}
	private static boolean isCooldownStillActive(EntityPlayer player, Kit kit){
	    if(kit.getCooldown() == 0){
            //System.out.println("1");
            return false;
        }else{
	        if(PlayerUtils.getPlayerstatByUUID(player.getCachedUniqueIdString()).getKitLastUsed().isEmpty()){
                //System.out.println("2");
	            return false;
            }else{
	            for(KitLastUsed lastUsed : PlayerUtils.getPlayerstatByUUID(player.getCachedUniqueIdString()).getKitLastUsed()){
	                if(lastUsed.getKitname().equals(kit.getName())){
	                    if(kit.getCooldown() < 0){
	                        return true;
                        }else{
                            Date date = new Date();
	                        if(kit.getCooldown() <= (date.getTime() - lastUsed.getLastUsed().getTime())){
                                //System.out.println("3");
	                            return false;
                            }else{
	                            //System.out.println("Cooldown last step: " + date.getTime() + " and " + (date.getTime() - lastUsed.getLastUsed().getTime()));
	                            return true;
                            }
                        }
                    }
                }

                return false;
            }
        }
	}

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

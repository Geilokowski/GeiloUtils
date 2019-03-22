package play.ai.dragonrealm.geiloutils.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import play.ai.dragonrealm.geiloutils.GeiloUtils;
import play.ai.dragonrealm.geiloutils.new_configs.ConfigAccess;
import play.ai.dragonrealm.geiloutils.new_configs.containers.PlayerStatsConfig;
import play.ai.dragonrealm.geiloutils.new_configs.models.Kit;
import play.ai.dragonrealm.geiloutils.new_configs.models.KitItem;
import play.ai.dragonrealm.geiloutils.new_configs.models.Permission;
import play.ai.dragonrealm.geiloutils.new_configs.models.KitLastUsed;
import play.ai.dragonrealm.geiloutils.new_configs.models.Playerstat;
import play.ai.dragonrealm.geiloutils.new_configs.models.Rank;

public class KitUtils {
    public static boolean deliverKit(EntityPlayer player, Kit kit){
    	int kitSize = kit.getItems().size();
    	int inventoryFree = 0;
    	for(ItemStack stack: player.inventory.mainInventory){
    		if(stack.isEmpty() || stack.equals(ItemStack.EMPTY)){
    			inventoryFree++;
			}
		}
		if(kitSize <= inventoryFree){
			for(KitItem ki : kit.getItems()){
				NBTTagCompound compound = null;
				try {
					if(ki.getNbtMap() != null)
						compound = JsonToNBT.getTagFromJson(ki.getNbtMap());
				} catch (NBTException e) {

				}
				PlayerUtils.addItemByName(player, ki.getRegistryName(), ki.getCount(), ki.getMetadata(), compound);
			}

			updateLastUsed(player, kit);
			return true;
		}

		return false;
    }

    private static void updateLastUsed(EntityPlayer player, Kit kit){
        Date date = new Date();
        Playerstat ps = PlayerUtils.getPlayerstatByUUID(player.getCachedUniqueIdString());
        for(KitLastUsed klu : ps.getKitLastUsed()){
            if(klu.getKitname().equals(kit.getName())){
                ps.getKitLastUsed().remove(klu);
                klu.setLastUsed(date);
                ps.getKitLastUsed().add(klu);
				GeiloUtils.getManager().getConfig(PlayerStatsConfig.class).updatePlayerstat(ps);
                return;
            }
        }

        KitLastUsed klu = new KitLastUsed(kit.getName(), date);
        ps.getKitLastUsed().add(klu);
		GeiloUtils.getManager().getConfig(PlayerStatsConfig.class).updatePlayerstat(ps);

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
		ConfigAccess.getKitConfig().getKits().stream().filter(k -> k.getName().equals(kit.getName())).findFirst().ifPresent(k -> ConfigAccess.getKitConfig().getKits().remove(k));
		ConfigAccess.getKitConfig().getKits().add(kit);
		ConfigAccess.writeKitFile();
	}

	public static boolean doesKitExist(String kitName) {
		for(Kit kit : ConfigAccess.getKitConfig().getKits()) {
			if(kit.getName().equals(kitName)) {
				return true;
			}
		}
		
		return false;
	}
	
	public static List<String> getKitNameList() {
		List<String> nameList = new ArrayList<String>();
		for(Kit kit : ConfigAccess.getKitConfig().getKits()) {
			nameList.add(kit.getName());
		}
		return nameList;
	}
	
	public static String removeKitByName(String name) {
		for(int i = 0; i < ConfigAccess.getKitConfig().getKits().size(); i++) {
			if(ConfigAccess.getKitConfig().getKits().get(i).getName().equals(name)) {
				Kit tmp = ConfigAccess.getKitConfig().getKits().remove(i);
				ConfigAccess.writeKitFile();
				return tmp.getName();
			}
		}
		
		return "";
	}
	
	public static Kit getKitByName(String name){
		for(Kit kit : ConfigAccess.getKitConfig().getKits()) {
			if(kit.getName().equals(name)) {
				return kit;
			}
		}
		
		return null;
	}
	
	public static int getKitCount() {
		return ConfigAccess.getKitConfig().getKits().size();
	}
}

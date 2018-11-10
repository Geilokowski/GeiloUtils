package play.ai.dragonrealm.geiloutils.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import net.minecraft.entity.player.EntityPlayer;
import play.ai.dragonrealm.geiloutils.config.ConfigurationManager;
import play.ai.dragonrealm.geiloutils.config.permissions.Permission;
import play.ai.dragonrealm.geiloutils.config.permissions.Permissions;
import play.ai.dragonrealm.geiloutils.config.playerstats.Playerstat;
import play.ai.dragonrealm.geiloutils.config.ranks.Rank;
import play.ai.dragonrealm.geiloutils.internals.Statics;

public class PermissionUtils {
	public static Rank getRankFromPlayer(EntityPlayer player){
		for(Playerstat ps : ConfigurationManager.getPlayerstats().getPlayerstats()){
			if(ps.getUuid().equals(player.getCachedUniqueIdString())){
				return getRankFromName(ps.getRank());
			}
		}

		return null;
	}

	public static Rank getRankFromPlayer(String playerName){
		for(Playerstat ps : ConfigurationManager.getPlayerstats().getPlayerstats()){
			if(ps.getName().equals(playerName)){
				return getRankFromName(ps.getRank());
			}
		}

		return null;
	}

    public static List<String> getPermissionNamesOfRank(Rank rank){
		return rank.getPermList().stream().map(Permission::getName).collect(Collectors.toList());
    }

    public static List<String> getPermissionNamesOfRank(String rankName){
        return getRankFromName(rankName).getPermList().stream().map(Permission::getName).collect(Collectors.toList());
    }

    public static List<String> getPermissionNames(){
        return ConfigurationManager.getPermissionsConfig().getPermissions().stream().map(Permission::getName).collect(Collectors.toList());
    }

	public static String removePermission(String permName) {
		for(int i = 0; i < ConfigurationManager.getPermissionsConfig().getPermissions().size(); i++) {
			if(ConfigurationManager.getPermissionsConfig().getPermissions().get(i).getName().equals(permName)) {
				ConfigurationManager.syncFromFields();
				return ConfigurationManager.getPermissionsConfig().getPermissions().remove(i).getName();
			}
		}

		return "";
	}
	
	public static boolean doesRankHavePermission(Rank rank, Permission perm) {
        return rank.getPermList().stream().anyMatch(permIterator -> permIterator.getName().equals(perm.getName()));
	}
	
	public static String removePermission(Permission perm) {
		for(int i = 0; i < ConfigurationManager.getPermissionsConfig().getPermissions().size(); i++) {
			if(ConfigurationManager.getPermissionsConfig().getPermissions().get(i).getName().equals(perm.getName())) {
				String tmp =  ConfigurationManager.getPermissionsConfig().getPermissions().remove(i).getName();
				ConfigurationManager.syncFromFields();
				return tmp;
			}
		}

		return "";
	}
	
	public static boolean doesPermissionExist(String permName) {
        return ConfigurationManager.getPermissionsConfig().getPermissions().stream().anyMatch(s -> s.getName().equals(permName));
	}
	
	public static boolean doesPermissionExist(Permission perm) {
        return ConfigurationManager.getPermissionsConfig().getPermissions().stream().anyMatch(s -> s.getName().equals(perm.getName()));
	}
	
	public static boolean doesRankExist(String rankName) {
        return ConfigurationManager.getRankConfig().getRanks().stream().anyMatch(s -> s.getName().equals(rankName));
	}
	
	public static String removeRank(String name) {
		for(int i = 0; i < ConfigurationManager.getRankConfig().getRanks().size(); i++) {
			if(ConfigurationManager.getRankConfig().getRanks().get(i).getName().equals(name)) {
				String tmp = ConfigurationManager.getRankConfig().getRanks().remove(i).getName();
				ConfigurationManager.syncFromFields();
				return tmp;
			}
		}

		return "";
	}
	
	public static List<String> getRankNameList(){
		return ConfigurationManager.getRankConfig().getRanks().stream().map(Rank::getName).collect(Collectors.toList());
	}
	
	public static Rank getRankFromName(String name) {
        return ConfigurationManager.getRankConfig().getRanks().stream().filter(r -> r.getName().equals(name)).findFirst().orElse(null);
    }

	public static List<String> getUsersWithRank(Rank rank){
		return ConfigurationManager.getPlayerstats().getPlayerstats().stream().filter(ps -> ps.getRank().equals(rank.getName())).map(Playerstat::getName).collect(Collectors.toList());
	}
}

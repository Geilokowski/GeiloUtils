package play.ai.dragonrealm.geiloutils.utils;

import java.util.List;
import java.util.stream.Collectors;

import net.minecraft.entity.player.PlayerEntity;
import play.ai.dragonrealm.geiloutils.new_configs.models.Permission;
import play.ai.dragonrealm.geiloutils.new_configs.models.Playerstat;
import play.ai.dragonrealm.geiloutils.new_configs.ConfigAccess;
import play.ai.dragonrealm.geiloutils.new_configs.models.Rank;

public class PermissionUtils {
	public static Rank getRankFromPlayer(PlayerEntity player){
		for(Playerstat ps : ConfigAccess.getPlayerStatsConfig().getPlayerstats()){
			if(ps.getUuid().equals(player.getStringUUID())){
				return getRankFromName(ps.getRank());
			}
		}

		return null;
	}

	public static Rank getRankFromPlayer(String playerName){
		for(Playerstat ps : ConfigAccess.getPlayerStatsConfig().getPlayerstats()){
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
        return ConfigAccess.getPermissionConfig().getPermissions().stream().map(Permission::getName).collect(Collectors.toList());
    }

	public static String removePermission(String permName) {
		for(int i = 0; i < ConfigAccess.getPermissionConfig().getPermissions().size(); i++) {
			if(ConfigAccess.getPermissionConfig().getPermissions().get(i).getName().equals(permName)) {
				String removedPerm = ConfigAccess.getPermissionConfig().getPermissions().remove(i).getName();
				ConfigAccess.writePermissionFile();
				return removedPerm;
			}
		}

		return "";
	}
	
	public static boolean doesRankHavePermission(Rank rank, Permission perm) {
        return rank.getPermList().stream().anyMatch(permIterator -> permIterator.getName().equals(perm.getName()));
	}
	
	public static String removePermission(Permission perm) {
		for(int i = 0; i < ConfigAccess.getPermissionConfig().getPermissions().size(); i++) {
			if(ConfigAccess.getPermissionConfig().getPermissions().get(i).getName().equals(perm.getName())) {
				String tmp =  ConfigAccess.getPermissionConfig().getPermissions().remove(i).getName();
				ConfigAccess.writePermissionFile();
				return tmp;
			}
		}

		return "";
	}
	
	public static boolean doesPermissionExist(String permName) {
        return ConfigAccess.getPermissionConfig().getPermissions().stream().anyMatch(s -> s.getName().equals(permName));
	}
	
	public static boolean doesPermissionExist(Permission perm) {
        return ConfigAccess.getPermissionConfig().getPermissions().stream().anyMatch(s -> s.getName().equals(perm.getName()));
	}
	
	public static boolean doesRankExist(String rankName) {
        return ConfigAccess.getRanksConfig().getRanks().stream().anyMatch(s -> s.getName().equals(rankName));
	}
	
	public static String removeRank(String name) {
		for(int i = 0; i < ConfigAccess.getRanksConfig().getRanks().size(); i++) {
			if(ConfigAccess.getRanksConfig().getRanks().get(i).getName().equals(name)) {
				String tmp = ConfigAccess.getRanksConfig().getRanks().remove(i).getName();
				ConfigAccess.writeRanksFile();
				return tmp;
			}
		}

		return "";
	}
	
	public static List<String> getRankNameList(){
		return ConfigAccess.getRanksConfig().getRanks().stream().map(Rank::getName).collect(Collectors.toList());
	}
	
	public static Rank getRankFromName(String name) {
        return ConfigAccess.getRanksConfig().getRanks().stream().filter(r -> r.getName().equals(name)).findFirst().orElse(null);
    }

	public static List<String> getUsersWithRank(Rank rank){
		return ConfigAccess.getPlayerStatsConfig().getPlayerstats().stream().filter(ps -> ps.getRank().equals(rank.getName())).map(Playerstat::getName).collect(Collectors.toList());
	}
}

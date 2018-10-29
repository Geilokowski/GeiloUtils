package play.ai.dragonrealm.geiloutils.utils;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import play.ai.dragonrealm.geiloutils.config.ConfigurationManager;
import play.ai.dragonrealm.geiloutils.config.permissions.Permission;
import play.ai.dragonrealm.geiloutils.config.permissions.Permissions;
import play.ai.dragonrealm.geiloutils.config.ranks.Rank;
import play.ai.dragonrealm.geiloutils.internals.Statics;

public class PermissionUtils {
	public static String removePermission(String permName) {
		for(int i = 0; i < ConfigurationManager.getPermissionsConfig().getPermissions().size(); i++) {
			if(ConfigurationManager.getPermissionsConfig().getPermissions().get(i).getName().equals(permName)) {
				String tmp = ConfigurationManager.getPermissionsConfig().getPermissions().remove(i).getName();
				ConfigurationManager.syncFromFields();
				return tmp;
			}
		}

		return "";
	}
	
	public static boolean doesRankHavePermission(Rank rank, Permission perm) {
		for(Permission permIterator : rank.getPermList()) {
			if(permIterator.getName().equals(perm.getName()))
				return true;
		}
		
		return false;
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
		for(Permission s : ConfigurationManager.getPermissionsConfig().getPermissions()) {
			if(s.getName().equals(permName))
				return true;
		}
		
		return false;
	}
	
	public static boolean doesPermissionExist(Permission perm) {
		for(Permission s : ConfigurationManager.getPermissionsConfig().getPermissions()) {
			if(s.getName().equals(perm.getName()))
				return true;
		}
		
		return false;
	}
	
	public static Permission getPermissionFromString(String permString) {
		Permission perm = new Permission(permString);
		return perm;
	}
	
	public static boolean doesRankExist(String rankName) {
		for(Rank s : ConfigurationManager.getRankConfig().getRanks()) {
			if(s.getName().equals(rankName)) {
				return true;
			}
		}
		
		return false;
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
		List<String> nameList = new ArrayList<String>();
		for(Rank r : ConfigurationManager.getRankConfig().getRanks()) {
			nameList.add(r.getName());
		}
		
		return nameList;
	}
	
	public static Rank getRankFromString(String rankString) {
		String[] firstSplit = rankString.split(Statics.seperationKey);
		String[] permSplit = firstSplit[2].split(Statics.secondSeperationKey);
		String[] playerSplit = firstSplit[3].split(Statics.secondSeperationKey);
		
		Rank rank = new Rank();
		rank.setName(firstSplit[0]);
		
		if(!firstSplit[1].equals(Statics.noInherit)) {
			rank.setInherits(firstSplit[1]);
		}
		
		if(!firstSplit[2].equals(Statics.noPermissions)) {
			for(String s : permSplit) {
				rank.getPermList().add(new Permission(s));
			}
		}
		
		if(!firstSplit[3].equals(Statics.noMembers)) {
			for(String s : playerSplit) {
				rank.getPlayerList().add(s);
			}
		}
		
		return rank;
	}
	
	public static Rank getRankFromName(String name) {
		for(Rank r : ConfigurationManager.getRankConfig().getRanks()) {
			if(r.getName().equals(name)) {
				return r;
			}
		}
		
		return null;
	}
	
	public static boolean doesPlayerHaveRank(EntityPlayer player) {
		//TODO: Implement this function
		return false;
	}
	
	public static Rank getPlayerRank(EntityPlayer player) {
		//TODO: Implement this function
		return null;
	}
}

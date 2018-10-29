package play.ai.dragonrealm.geiloutils.config.ranks;

import java.util.ArrayList;
import java.util.List;

import play.ai.dragonrealm.geiloutils.config.permissions.Permission;
import play.ai.dragonrealm.geiloutils.internals.Statics;

public class Rank {
	private List<Permission> permList = new ArrayList<Permission>();
	private String name = "";
	private String inherits = "";
	private List<String> playerList = new ArrayList<String>();
	
	@Override
	public String toString() {
		String tmp = "";
		tmp = tmp + name + Statics.seperationKey;
		if(inherits.equals("")) {
			tmp = tmp + Statics.noInherit + Statics.seperationKey;
		}else {
			tmp = tmp + inherits + Statics.seperationKey;
		}
		
		if(permList.isEmpty()) {
			tmp = tmp + Statics.noPermissions;
		}else {
			for(Permission s : permList) {
				tmp = tmp + s.getName() + Statics.secondSeperationKey;
			}
			
			tmp = tmp.substring(0, tmp.length() - Statics.secondSeperationKey.length());
		}
		
		tmp = tmp + Statics.seperationKey;
		
		if(playerList.isEmpty()) {
			tmp = tmp + Statics.noMembers;
		}else {
			for(String s : playerList) {
				tmp = tmp + s + Statics.secondSeperationKey;
			}
			
			tmp = tmp.substring(0, tmp.length() - Statics.secondSeperationKey.length());
		}
		
		return tmp;
	}

	public List<Permission> getPermList() {
		return permList;
	}

	public void setPermList(List<Permission> permList) {
		this.permList = permList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInherits() {
		return inherits;
	}

	public void setInherits(String inherits) {
		this.inherits = inherits;
	}

	public List<String> getPlayerList() {
		return playerList;
	}

	public void setPlayerList(List<String> playerList) {
		this.playerList = playerList;
	}
}

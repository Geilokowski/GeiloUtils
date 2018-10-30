package play.ai.dragonrealm.geiloutils.config.ranks;

import java.util.ArrayList;
import java.util.List;

import play.ai.dragonrealm.geiloutils.config.permissions.Permission;
import play.ai.dragonrealm.geiloutils.internals.Statics;

public class Rank {
	private List<Permission> permList = new ArrayList<Permission>();
	private String name = "";
	private String inherits = "";

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
}

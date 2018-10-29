package play.ai.dragonrealm.geiloutils.config.permissions;

import java.util.ArrayList;
import java.util.List;

public class Permissions {
	private List<Permission> permissions = new ArrayList<Permission>();
	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}
	
	public List<Permission> getPermissions(){
		return permissions;
	}
}

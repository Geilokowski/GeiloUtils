package play.me.dragonrealm.geiloutils.configs.containers;



import play.me.dragonrealm.geiloutils.configs.IJsonFile;
import play.me.dragonrealm.geiloutils.configs.interfaces.IPermissionConfig;
import play.me.dragonrealm.geiloutils.configs.models.Permission;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class PermissionConfig implements IPermissionConfig {
	private List<Permission> permissions = new ArrayList<Permission>();


	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}
	public List<Permission> getPermissions(){
		return permissions;
	}

	@Nonnull
	@Override
	public String getFileName() {
		return "permissions.json";
	}

	@Nonnull
	@Override
	public IJsonFile getDefaultJson() {
		//PermissionConfig defaultPerms = new PermissionConfig();
		return  new PermissionConfig();
	}
}

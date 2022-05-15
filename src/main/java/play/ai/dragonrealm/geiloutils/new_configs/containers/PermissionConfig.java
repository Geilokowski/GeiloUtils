package play.ai.dragonrealm.geiloutils.new_configs.containers;

import play.ai.dragonrealm.geiloutils.new_configs.models.Permission;
import play.ai.dragonrealm.geiloutils.new_configs.IJsonFile;
import play.ai.dragonrealm.geiloutils.new_configs.interfaces.IPermissionConfig;

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

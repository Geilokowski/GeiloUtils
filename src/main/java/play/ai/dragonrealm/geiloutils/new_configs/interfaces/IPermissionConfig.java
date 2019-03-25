package play.ai.dragonrealm.geiloutils.new_configs.interfaces;

import play.ai.dragonrealm.geiloutils.new_configs.models.Permission;
import play.ai.dragonrealm.geiloutils.new_configs.IJsonFile;

import java.util.List;

public interface IPermissionConfig extends IJsonFile {

    public void setPermissions(List<Permission> permissions);
    public List<Permission> getPermissions();
}

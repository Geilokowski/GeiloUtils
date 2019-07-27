package play.me.dragonrealm.geiloutils.configs.interfaces;



import play.me.dragonrealm.geiloutils.configs.IJsonFile;
import play.me.dragonrealm.geiloutils.configs.models.Permission;

import java.util.List;

public interface IPermissionConfig extends IJsonFile {

    public void setPermissions(List<Permission> permissions);
    public List<Permission> getPermissions();
}

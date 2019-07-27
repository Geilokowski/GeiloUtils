package play.me.dragonrealm.geiloutils.configs.interfaces;

import play.me.dragonrealm.geiloutils.configs.models.Kit;
import play.me.dragonrealm.geiloutils.configs.IJsonFile;

import java.util.List;

public interface IKitConfig extends IJsonFile {

    public List<Kit> getKits();
    public void setKits(List<Kit> kits);
}

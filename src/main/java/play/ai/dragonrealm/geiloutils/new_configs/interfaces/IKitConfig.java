package play.ai.dragonrealm.geiloutils.new_configs.interfaces;

import play.ai.dragonrealm.geiloutils.new_configs.models.Kit;
import play.ai.dragonrealm.geiloutils.new_configs.IJsonFile;

import java.util.List;

public interface IKitConfig extends IJsonFile {

    public List<Kit> getKits();
    public void setKits(List<Kit> kits);
}

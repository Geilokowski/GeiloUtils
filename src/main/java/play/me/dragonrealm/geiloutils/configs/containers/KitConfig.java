package play.me.dragonrealm.geiloutils.configs.containers;


import play.me.dragonrealm.geiloutils.configs.IJsonFile;
import play.me.dragonrealm.geiloutils.configs.interfaces.IKitConfig;
import play.me.dragonrealm.geiloutils.configs.models.Kit;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class KitConfig implements IKitConfig {

	private List<Kit> kits = new ArrayList<Kit>();


	public List<Kit> getKits(){
		return kits;
	}
	public void setKits(List<Kit> kits) {
		this.kits = kits;
	}

	@Nonnull
	@Override
	public String getFileName() {
		return "kits.json";
	}

	@Nonnull
	@Override
	public IJsonFile getDefaultJson() {
		KitConfig kits = new KitConfig();
		return kits;
	}
}

package play.ai.dragonrealm.geiloutils.new_configs.containers;

import play.ai.dragonrealm.geiloutils.new_configs.models.Kit;
import play.ai.dragonrealm.geiloutils.new_configs.IJsonFile;
import play.ai.dragonrealm.geiloutils.new_configs.interfaces.IKitConfig;

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

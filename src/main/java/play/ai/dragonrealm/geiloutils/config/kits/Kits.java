package play.ai.dragonrealm.geiloutils.config.kits;

import java.util.ArrayList;
import java.util.List;

public class Kits {
	private List<Kit> kits = new ArrayList<Kit>();
	public List<Kit> getKits(){
		return kits;
	}
	
	public void setKits(List<Kit> kits) {
		this.kits = kits;
	}
}

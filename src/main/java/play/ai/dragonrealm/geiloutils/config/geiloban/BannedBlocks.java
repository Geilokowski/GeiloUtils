package play.ai.dragonrealm.geiloutils.config.geiloban;

import java.util.ArrayList;
import java.util.List;

public class BannedBlocks {
	List<BannedBlock> bannedBlocks = new ArrayList<BannedBlock>();
	
	public List<BannedBlock> getBannedBlocks() {
		return bannedBlocks;
	}
	
	public void setBannedBlocks(List<BannedBlock> bannedBlocks) {
		this.bannedBlocks = bannedBlocks;
	}
}

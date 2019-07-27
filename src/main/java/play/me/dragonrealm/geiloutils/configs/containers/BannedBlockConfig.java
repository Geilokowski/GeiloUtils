package play.me.dragonrealm.geiloutils.configs.containers;


import play.me.dragonrealm.geiloutils.configs.IJsonFile;
import play.me.dragonrealm.geiloutils.configs.interfaces.IBannedBlocksConfig;
import play.me.dragonrealm.geiloutils.configs.models.BannedBlock;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class BannedBlockConfig implements IBannedBlocksConfig {
	List<BannedBlock> bannedBlocks = new ArrayList<BannedBlock>();
	
	public List<BannedBlock> getBannedBlocks() {
		return bannedBlocks;
	}
	
	public void setBannedBlocks(List<BannedBlock> bannedBlocks) {
		this.bannedBlocks = bannedBlocks;
	}

	@Nonnull
	@Override
	public String getFileName() {
		return "banned_blocks.json";
	}

	@Nonnull
	@Override
	public IJsonFile getDefaultJson() {
		BannedBlockConfig defaultBanendBlockConfig = new BannedBlockConfig();
		defaultBanendBlockConfig.getBannedBlocks().add(new BannedBlock("rftools:builder", "all", "0"));
		return defaultBanendBlockConfig;
	}
}

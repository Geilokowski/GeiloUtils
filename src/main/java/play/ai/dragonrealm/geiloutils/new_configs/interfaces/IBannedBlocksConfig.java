package play.ai.dragonrealm.geiloutils.new_configs.interfaces;

import play.ai.dragonrealm.geiloutils.new_configs.models.BannedBlock;
import play.ai.dragonrealm.geiloutils.new_configs.IJsonFile;

import java.util.List;

public interface IBannedBlocksConfig extends IJsonFile {

    public List<BannedBlock> getBannedBlocks();

    public void setBannedBlocks(List<BannedBlock> bannedBlocks);
}

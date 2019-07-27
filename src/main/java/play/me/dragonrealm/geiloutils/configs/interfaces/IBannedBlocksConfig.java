package play.me.dragonrealm.geiloutils.configs.interfaces;

import play.me.dragonrealm.geiloutils.configs.models.BannedBlock;
import play.me.dragonrealm.geiloutils.configs.IJsonFile;
import play.me.dragonrealm.geiloutils.configs.IJsonFile;

import java.util.List;

public interface IBannedBlocksConfig extends IJsonFile {

    public List<BannedBlock> getBannedBlocks();

    public void setBannedBlocks(List<BannedBlock> bannedBlocks);
}

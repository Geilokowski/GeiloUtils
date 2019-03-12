package play.ai.dragonrealm.geiloutils.new_configs.interfaces;

import play.ai.dragonrealm.geiloutils.new_configs.IJsonFile;
import play.ai.dragonrealm.geiloutils.new_configs.models.Playerstat;

import java.util.List;

public interface IPlayerStatsConfig extends IJsonFile {

    public List<Playerstat> getPlayerstats();
    public void setPlayerstats(List<Playerstat> playerstats);
}

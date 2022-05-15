package play.ai.dragonrealm.geiloutils.new_configs.interfaces;

import play.ai.dragonrealm.geiloutils.new_configs.models.Rank;

import java.util.List;

public interface IRanksConfig {

    public void setRanks(List<Rank> ranks);
    public List<Rank> getRanks();
}

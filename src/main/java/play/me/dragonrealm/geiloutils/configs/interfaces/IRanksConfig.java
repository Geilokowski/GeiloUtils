package play.me.dragonrealm.geiloutils.configs.interfaces;


import play.me.dragonrealm.geiloutils.configs.models.Rank;

import java.util.List;

public interface IRanksConfig {

    public void setRanks(List<Rank> ranks);
    public List<Rank> getRanks();
}

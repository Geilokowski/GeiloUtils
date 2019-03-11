package play.me.dragonrealm.geiloutils.configs.interfaces;

import play.me.dragonrealm.geiloutils.configs.models.PlayerStats;
import java.util.List;

public interface IPlayerStatConfig {

    public List<PlayerStats> getPlayerstats();
    public void setPlayerstats(List<PlayerStats> playerstats);
}

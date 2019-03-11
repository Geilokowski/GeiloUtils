package play.me.dragonrealm.geiloutils.configs.containers;

import play.me.dragonrealm.geiloutils.configs.IJsonFile;
import play.me.dragonrealm.geiloutils.configs.interfaces.IPlayerStatConfig;
import play.me.dragonrealm.geiloutils.configs.models.PlayerStats;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class PlayerStatsConfig implements IJsonFile, IPlayerStatConfig {

    private List<PlayerStats> playerstats = new ArrayList<PlayerStats>();

    @Nonnull
    @Override
    public String getFileName() {
        return "player_stats.json";
    }

    @Nonnull
    @Override
    public IJsonFile getDefaultJson() {
        return new PlayerStatsConfig();
    }

    @Override
    public List<PlayerStats> getPlayerstats() {
        return playerstats;
    }

    @Override
    public void setPlayerstats(List<PlayerStats> playerstats) {
        this.playerstats = playerstats;
    }
}

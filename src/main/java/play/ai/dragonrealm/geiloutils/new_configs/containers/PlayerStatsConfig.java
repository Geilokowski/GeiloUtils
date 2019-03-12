package play.ai.dragonrealm.geiloutils.new_configs.containers;

import play.ai.dragonrealm.geiloutils.new_configs.IJsonFile;
import play.ai.dragonrealm.geiloutils.new_configs.interfaces.IPlayerStatsConfig;
import play.ai.dragonrealm.geiloutils.new_configs.models.Playerstat;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class PlayerStatsConfig implements IPlayerStatsConfig {

	private List<Playerstat> playerstats = new ArrayList<Playerstat>();

	public List<Playerstat> getPlayerstats() {
		return playerstats;
	}
	public void setPlayerstats(List<Playerstat> playerstats) {
		this.playerstats = playerstats;
	}

	@Nonnull
	@Override
	public String getFileName() {
		return "player_stats.json";
	}

	@Nonnull
	@Override
	public IJsonFile getDefaultJson() {
		PlayerStatsConfig defaultPS = new PlayerStatsConfig();
		return defaultPS;
	}
}

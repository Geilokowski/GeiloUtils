package play.ai.dragonrealm.geiloutils.config.playerstats;

import java.util.ArrayList;
import java.util.List;

public class Playerstats {
	private List<Playerstat> playerstats = new ArrayList<Playerstat>();

	public List<Playerstat> getPlayerstats() {
		return playerstats;
	}

	public void setPlayerstats(List<Playerstat> playerstats) {
		this.playerstats = playerstats;
	}
}

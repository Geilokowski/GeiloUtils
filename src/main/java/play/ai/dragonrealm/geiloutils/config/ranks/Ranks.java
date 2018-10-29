package play.ai.dragonrealm.geiloutils.config.ranks;

import java.util.ArrayList;
import java.util.List;

public class Ranks {
	private List<Rank> ranks = new ArrayList<Rank>();
	public void setRanks(List<Rank> ranks) {
		this.ranks = ranks;
	}
	
	public List<Rank> getRanks(){
		return ranks;
	}
}

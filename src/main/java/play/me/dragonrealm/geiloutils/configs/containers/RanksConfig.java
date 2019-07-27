package play.me.dragonrealm.geiloutils.configs.containers;



import play.me.dragonrealm.geiloutils.configs.ConfigAccess;
import play.me.dragonrealm.geiloutils.configs.IJsonFile;
import play.me.dragonrealm.geiloutils.configs.interfaces.IRanksConfig;
import play.me.dragonrealm.geiloutils.configs.models.Rank;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class RanksConfig implements IRanksConfig, IJsonFile {

	private List<Rank> ranks = new ArrayList<Rank>();
	public void setRanks(List<Rank> ranks) {
		this.ranks = ranks;
	}
	
	public List<Rank> getRanks(){
		return ranks;
	}

	@Nonnull
	@Override
	public String getFileName() {
		return "ranks.json";
	}

	@Nonnull
	@Override
	public IJsonFile getDefaultJson() {
		RanksConfig ranks = new RanksConfig();
		ranks.getRanks().add(new Rank(ConfigAccess.getGeneralConfig().getStandardRank()));
		return ranks;
	}
}

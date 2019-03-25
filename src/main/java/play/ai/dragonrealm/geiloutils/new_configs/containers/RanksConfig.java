package play.ai.dragonrealm.geiloutils.new_configs.containers;

import play.ai.dragonrealm.geiloutils.new_configs.models.Rank;
import play.ai.dragonrealm.geiloutils.new_configs.ConfigAccess;
import play.ai.dragonrealm.geiloutils.new_configs.IJsonFile;
import play.ai.dragonrealm.geiloutils.new_configs.interfaces.IRanksConfig;

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

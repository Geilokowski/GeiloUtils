package play.ai.dragonrealm.geiloutils.new_configs.containers;

import play.ai.dragonrealm.geiloutils.new_configs.ConfigAccess;
import play.ai.dragonrealm.geiloutils.new_configs.IJsonFile;
import play.ai.dragonrealm.geiloutils.new_configs.interfaces.IPlayerStatsConfig;
import play.ai.dragonrealm.geiloutils.new_configs.models.Playerstat;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

	public Optional<Playerstat> getPlayerStatByUUID(String uuid) {
		for(Playerstat ps : getPlayerstats()) {
			if(ps.getUuid().equals(uuid)) {
				return Optional.of(ps);
			}
		}
		return Optional.empty();
	}

	public Optional<Playerstat> getPlayerstatByDiscordID(Long id) {
		for(Playerstat ps : getPlayerstats()) {
			if(ps.getDiscordID() != null && ps.getDiscordID().equals(id)) {
				return Optional.of(ps);
			}
		}
		return Optional.empty();
	}

	public boolean isFirstJoin(String uuid) {
		return getPlayerStatByUUID(uuid).isPresent();
	}

	public void updatePlayerstat(Playerstat ps) {
		Optional<Playerstat> opstat = getPlayerStatByUUID(ps.getUuid());
		if(opstat.isPresent()) {
			getPlayerstats().remove(opstat.get());
			getPlayerstats().add(ps);
			ConfigAccess.writePlayerStatsFile();
		}
	}

	public double getPlayerBalance(String playerUUID) {
		Optional<Playerstat> ps = getPlayerStatByUUID(playerUUID);
		return ps.map(Playerstat::getMoney).orElse(-1.0D);
	}


	public void addToMuteList(String uuid, String addition) {

		Optional<Playerstat> ps = getPlayerStatByUUID(uuid);
		if(ps.isPresent()){
			ps.map(x -> x.getMutedChats().add(addition));
			updatePlayerstat(ps.get());
		}
	}

	public boolean removeFromMuteList(String uniqueId, String removal) {

		Optional<Playerstat> ps = getPlayerStatByUUID(uniqueId);

		if(ps.isPresent()) {
			Playerstat stats = ps.get();
			if(stats.getMutedChats().contains(removal)) {
				stats.getMutedChats().remove(removal);
				updatePlayerstat(stats);
				return true;
			}
		}
		return false;
	}

	public List<Playerstat> getPlayersSortedByMoney(){
		List<Playerstat> psl = getPlayerstats();
		psl.sort(
				(Playerstat x, Playerstat y) -> (int) (y.getMoney() - x.getMoney()));
		return psl;
	}

	public void addPlayerMoney(String uniqueId, double amount) {
		Optional<Playerstat> playerstat = getPlayerStatByUUID(uniqueId);

		if(playerstat.isPresent()) {
			Playerstat stat = playerstat.get();
			stat.setMoney(stat.getMoney() + amount);
			updatePlayerstat(stat);
		}
	}

	public void removePlayerMoney(String uniqueId, double amount) {
		Optional<Playerstat> ps = getPlayerStatByUUID(uniqueId);
		if(ps.isPresent()) {
			Playerstat playerstat = ps.get();
			playerstat.setMoney(playerstat.getMoney() - amount);
			updatePlayerstat(playerstat);
		}
	}
}

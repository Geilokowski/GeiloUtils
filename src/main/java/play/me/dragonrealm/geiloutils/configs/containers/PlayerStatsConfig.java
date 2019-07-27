package play.me.dragonrealm.geiloutils.configs.containers;

import play.me.dragonrealm.geiloutils.configs.ConfigAccess;
import play.me.dragonrealm.geiloutils.configs.IJsonFile;
import play.me.dragonrealm.geiloutils.configs.interfaces.IPlayerStatConfig;
import play.me.dragonrealm.geiloutils.configs.models.PlayerStats;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PlayerStatsConfig implements IPlayerStatConfig, IJsonFile {

	private List<PlayerStats> playerstats = new ArrayList<PlayerStats>();

	public List<PlayerStats> getPlayerstats() {
		return playerstats;
	}
	public void setPlayerstats(List<PlayerStats> playerstats) {
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

	public Optional<PlayerStats> getPlayerStatByUUID(String uuid) {
		for(PlayerStats ps : getPlayerstats()) {
			if(ps.getUuid().equals(uuid)) {
				return Optional.of(ps);
			}
		}
		return Optional.empty();
	}

	public Optional<PlayerStats> getPlayerstatByDiscordID(Long id) {
		for(PlayerStats ps : getPlayerstats()) {
			if(ps.getDiscordID() != null && ps.getDiscordID().equals(id)) {
				return Optional.of(ps);
			}
		}
		return Optional.empty();
	}

	public boolean isFirstJoin(String uuid) {
		return !getPlayerStatByUUID(uuid).isPresent();
	}

	public void updatePlayerstat(PlayerStats ps) {
		Optional<PlayerStats> opstat = getPlayerStatByUUID(ps.getUuid());
		if(opstat.isPresent()) {
			getPlayerstats().remove(opstat.get());
			getPlayerstats().add(ps);
			ConfigAccess.writePlayerStatsFile();
		}
	}

	public double getPlayerBalance(String playerUUID) {
		Optional<PlayerStats> ps = getPlayerStatByUUID(playerUUID);
		return ps.map(PlayerStats::getMoney).orElse(-1.0D);
	}


	public void addToMuteList(String uuid, String addition) {

		Optional<PlayerStats> ps = getPlayerStatByUUID(uuid);
		if(ps.isPresent()){
			ps.map(x -> x.getMutedChats().add(addition));
			updatePlayerstat(ps.get());
		}
	}

	public boolean removeFromMuteList(String uniqueId, String removal) {

		Optional<PlayerStats> ps = getPlayerStatByUUID(uniqueId);

		if(ps.isPresent()) {
			PlayerStats stats = ps.get();
			if(stats.getMutedChats().contains(removal)) {
				stats.getMutedChats().remove(removal);
				updatePlayerstat(stats);
				return true;
			}
		}
		return false;
	}

	public List<PlayerStats> getPlayersSortedByMoney(){
		List<PlayerStats> psl = getPlayerstats();
		psl.sort(
				(PlayerStats x, PlayerStats y) -> (int) (y.getMoney() - x.getMoney()));
		return psl;
	}

	public void addPlayerMoney(String uniqueId, double amount) {
		Optional<PlayerStats> playerstat = getPlayerStatByUUID(uniqueId);

		if(playerstat.isPresent()) {
			PlayerStats stat = playerstat.get();
			stat.setMoney(stat.getMoney() + amount);
			updatePlayerstat(stat);
		}
	}

	public void removePlayerMoney(String uniqueId, double amount) {
		Optional<PlayerStats> ps = getPlayerStatByUUID(uniqueId);
		if(ps.isPresent()) {
			PlayerStats playerstat = ps.get();
			playerstat.setMoney(playerstat.getMoney() - amount);
			updatePlayerstat(playerstat);
		}
	}
}

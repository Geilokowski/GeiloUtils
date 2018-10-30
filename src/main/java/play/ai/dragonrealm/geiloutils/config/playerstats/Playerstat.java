package play.ai.dragonrealm.geiloutils.config.playerstats;

import java.util.ArrayList;
import java.util.List;

public class Playerstat {
	private String name = "";
	private String uuid = "";
	private double money = 0.0;
	private String rank = "";
	private List<KitLastUsed> kitLastUsed = new ArrayList<KitLastUsed>();
	private List<String> mutedChats = new ArrayList<>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public List<KitLastUsed> getKitLastUsed() {
		return kitLastUsed;
	}
	public void setKitLastUsed(List<KitLastUsed> kitLastUsed) {
		this.kitLastUsed = kitLastUsed;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public List<String> getMutedChats() {
		return mutedChats;
	}

	public void setMutedChats(List<String> mutedChats) {
		this.mutedChats = mutedChats;
	}
}

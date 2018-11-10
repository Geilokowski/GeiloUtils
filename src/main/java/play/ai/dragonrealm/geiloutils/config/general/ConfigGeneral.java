package play.ai.dragonrealm.geiloutils.config.general;

public class ConfigGeneral {
	private String commandPrefix;
	private String standartRank = "player";

	public String getCommandPrefix() {
		return commandPrefix;
	}
	public String getStandartRank() {
		return standartRank;
	}

	public void setCommandPrefix(String commandPrefix) {
		this.commandPrefix = commandPrefix;
	}
	public void setStandartRank(String standartRank) {
		this.standartRank = standartRank;
	}
}

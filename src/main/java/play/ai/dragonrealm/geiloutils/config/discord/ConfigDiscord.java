package play.ai.dragonrealm.geiloutils.config.discord;

import java.util.List;

public class ConfigDiscord {
	private boolean enabled;
	private boolean isSingleToMulti;
	private String token;
	private String channelIDCommands;
	private String channelIDRelay;
	private String minecraftChatPrefix;
	private String discordChatPrefix;
	private String discordCommandPrefix;
	private List<String> validColors;

	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getChannelIDCommands() {
		return channelIDCommands;
	}
	public void setChannelIDCommands(String channelIDCommands) {
		this.channelIDCommands = channelIDCommands;
	}
	public String getChannelIDRelay() {
		return channelIDRelay;
	}
	public void setChannelIDRelay(String channelIDRelay) {
		this.channelIDRelay = channelIDRelay;
	}
	public String getMinecraftChatPrefix() {
		return minecraftChatPrefix;
	}
	public void setMinecraftChatPrefix(String minecraftChatPrefix) {
		this.minecraftChatPrefix = minecraftChatPrefix;
	}
	public String getDiscordChatPrefix() {
		return discordChatPrefix;
	}
	public void setDiscordChatPrefix(String discordChatPrefix) {
		this.discordChatPrefix = discordChatPrefix;
	}
	public String getDiscordCommandPrefix() {
		return discordCommandPrefix;
	}
	public void setDiscordCommandPrefix(String discordCommandPrefix) {
		this.discordCommandPrefix = discordCommandPrefix;
	}


	public boolean isSingleToMulti() {
		return isSingleToMulti;
	}

	public void setSingleToMulti(boolean singleToMulti) {
		isSingleToMulti = singleToMulti;
	}

	public void setValidColors(List<String> validColors) {
		this.validColors = validColors;
	}

	public List<String> getValidColors() {
		return validColors;
	}
}

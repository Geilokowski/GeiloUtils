package play.ai.dragonrealm.geiloutils.config.discord;

public class ConfigDiscord {
	private boolean enabled;
	private String token;
	private String channelIDCommands;
	private String channelIDRelay;
	private String minecraftChatPrefix;
	private String discordChatPrefix;
	private String discordCommandPrefix;
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
}

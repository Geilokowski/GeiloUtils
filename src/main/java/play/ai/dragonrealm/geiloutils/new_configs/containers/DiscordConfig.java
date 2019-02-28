package play.ai.dragonrealm.geiloutils.new_configs.containers;

import play.ai.dragonrealm.geiloutils.discord.utils.UserRanks;
import play.ai.dragonrealm.geiloutils.new_configs.IJsonFile;
import play.ai.dragonrealm.geiloutils.new_configs.interfaces.IDiscordConfigs;

import java.util.ArrayList;
import java.util.List;

public class DiscordConfig implements IJsonFile, IDiscordConfigs {

    public static final String MANAGER_NAME = "discordGeneral";

    private boolean enabled;
    private boolean enableMultipleServersOnOneChannel;
    private String token;
    private String channelIDCommands;
    private String channelIDRelay;
    private String minecraftChatPrefix;
    private String discordChatPrefix;
    private String discordCommandPrefix;
    private List<String> validColors;
    private String serverIP;
    private List<UserRanks> discordRankIntegration;
    private String supporterRank;

    @Override
    public String getFileName() {
        return "discord.json";
    }

    @Override
    public IJsonFile getDefaultJson() {
        DiscordConfig defaultDiscordConfig = new DiscordConfig();
        defaultDiscordConfig.setEnabled(false);
        defaultDiscordConfig.setSingleToMulti(false);
        defaultDiscordConfig.setMinecraftChatPrefix("§3[§6Discord§3] §r");
        defaultDiscordConfig.setChannelIDCommands("");
        defaultDiscordConfig.setChannelIDRelay("");
        defaultDiscordConfig.setToken("");
        defaultDiscordConfig.setDiscordCommandPrefix("!");
        defaultDiscordConfig.setDiscordChatPrefix("[%s] >> ");
        ArrayList<String> color = new ArrayList<>();
        color.add("§c");
        color.add("§e");
        color.add("§2");
        color.add("§a");
        color.add("§b");
        color.add("§3");
        color.add("§9");
        defaultDiscordConfig.setValidColors(color);
        defaultDiscordConfig.setServerIP("dragonrealm.me");
        ArrayList<UserRanks> ranks = new ArrayList<>();
        defaultDiscordConfig.setDiscordRankIntegration(ranks);
        defaultDiscordConfig.setSupporterRank("");
        return defaultDiscordConfig;
    }


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

    public void setDiscordRankIntegration(List<UserRanks> r) {
        this.discordRankIntegration = r;
    }

    public List<UserRanks> getDiscordRankIntegration() {
        return discordRankIntegration;
    }

    public boolean isSingleToMulti() {
        return enableMultipleServersOnOneChannel;
    }

    public void setSingleToMulti(boolean singleToMulti) {
        enableMultipleServersOnOneChannel = singleToMulti;
    }

    public void setValidColors(List<String> validColors) {
        this.validColors = validColors;
    }

    public List<String> getValidColors() {
        return validColors;
    }

    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }

    public String getServerIP() {
        return serverIP;
    }

    public String getPatronGlobalRank() {
        return supporterRank;
    }

    public void setSupporterRank(String supporterRank){
        this.supporterRank = supporterRank;
    }
}

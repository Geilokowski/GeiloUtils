package play.ai.dragonrealm.geiloutils.new_configs.interfaces;

import play.ai.dragonrealm.geiloutils.discord.utils.UserRanks;

import java.util.List;

public interface IDiscordConfigs {

    public boolean isEnabled();
    public void setEnabled(boolean enabled);

    public String getToken();
    public void setToken(String token);

    public String getChannelIDCommands();
    public void setChannelIDCommands(String channelIDCommands);

    public String getChannelIDRelay();
    public void setChannelIDRelay(String channelIDRelay);

    public String getMinecraftChatPrefix();
    public void setMinecraftChatPrefix(String minecraftChatPrefix);

    public String getDiscordChatPrefix();
    public void setDiscordChatPrefix(String discordChatPrefix);

    public String getDiscordCommandPrefix();
    public void setDiscordCommandPrefix(String discordCommandPrefix);

    public void setDiscordRankIntegration(List<UserRanks> r);
    public List<UserRanks> getDiscordRankIntegration();

    public boolean isSingleToMulti();
    public void setSingleToMulti(boolean singleToMulti);

    public void setValidColors(List<String> validColors);
    public List<String> getValidColors();

    public void setServerIP(String serverIP);
    public String getServerIP();

    public String getPatronGlobalRank();
    public void setSupporterRank(String supporterRank);

}

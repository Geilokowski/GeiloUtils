package play.ai.dragonrealm.geiloutils.new_configs.interfaces;

public interface IGeneralConfig {

    public String getCommandPrefix();
    public String getStandardRank();

    public void setCommandPrefix(String commandPrefix);
    public void setStandardRank(String standardRank);
}

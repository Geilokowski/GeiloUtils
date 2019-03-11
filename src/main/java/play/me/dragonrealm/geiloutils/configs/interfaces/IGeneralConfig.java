package play.me.dragonrealm.geiloutils.configs.interfaces;

public interface IGeneralConfig {

    public String getCommandPrefix();
    public String getStandardRank();

    public void setCommandPrefix(String commandPrefix);
    public void setStandardRank(String standardRank);
}

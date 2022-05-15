package play.ai.dragonrealm.geiloutils.new_configs.containers;

import play.ai.dragonrealm.geiloutils.new_configs.IJsonFile;
import play.ai.dragonrealm.geiloutils.new_configs.interfaces.IGeneralConfig;

import javax.annotation.Nonnull;

public class GeneralConfig implements IJsonFile, IGeneralConfig {

    private String commandPrefix;
    private String standardRank;

    public String getCommandPrefix() {
        return commandPrefix;
    }
    public String getStandardRank() {
        return standardRank;
    }

    public void setCommandPrefix(String commandPrefix) {
        this.commandPrefix = commandPrefix;
    }
    public void setStandardRank(String standartRank) {
        this.standardRank = standartRank;
    }

    @Nonnull
    @Override
    public String getFileName() {
        return "general.json";
    }

    @Nonnull
    @Override
    public IJsonFile getDefaultJson() {
        GeneralConfig defaultGeneralConfig = new GeneralConfig();
        defaultGeneralConfig.setCommandPrefix("[GeiloUtils] ");
        defaultGeneralConfig.setStandardRank("player");
        return defaultGeneralConfig;
    }
}

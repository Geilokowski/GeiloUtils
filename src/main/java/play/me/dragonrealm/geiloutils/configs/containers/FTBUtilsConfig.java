package play.me.dragonrealm.geiloutils.configs.containers;


import play.me.dragonrealm.geiloutils.configs.IJsonFile;

import javax.annotation.Nonnull;
import java.util.HashMap;

public class FTBUtilsConfig implements IJsonFile {

    public FTBUtilsConfig(){}

    public boolean enabled;
    public HashMap<String, String> inLocoBigas;


    @Nonnull
    @Override
    public String getFileName() {
        return "ftbutilities.json";
    }

    @Nonnull
    @Override
    public IJsonFile getDefaultJson() {
        FTBUtilsConfig inter = new FTBUtilsConfig();
        inter.enabled = false;
        inter.inLocoBigas = new HashMap<>();
        return inter;
    }
}

package play.ai.dragonrealm.geiloutils.config.ftbutils;


import java.io.File;
import java.util.HashMap;

public class ConfigFTBUtilsInter {

    public ConfigFTBUtilsInter(){}

    public boolean enabled;
    public HashMap<String, String> inLocoBigas;


    public static ConfigFTBUtilsInter getDefaultConfig() {
        ConfigFTBUtilsInter inter = new ConfigFTBUtilsInter();
        inter.enabled = false;
        inter.inLocoBigas = new HashMap<>();
        return inter;
    }

}

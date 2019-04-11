package play.ai.dragonrealm.geiloutils.new_configs.containers;

import play.ai.dragonrealm.geiloutils.new_configs.FileEnum;
import play.ai.dragonrealm.geiloutils.new_configs.IJsonFile;
import play.ai.dragonrealm.geiloutils.new_configs.models.Permission;
import play.ai.dragonrealm.geiloutils.new_configs.models.World;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultiworldConfig implements IJsonFile {
    private List<World> worlds = new ArrayList<>();
    private Map<String, Integer> numberOfDimensionForPermission = new HashMap<>();
    private Permission geiloportPerm = new Permission("perm.geiloport");
    public List<World> getWorlds() {
        return worlds;
    }

    public Permission getGeiloportPerm() {
        return geiloportPerm;
    }

    public Map<String, Integer> getNumberOfDimensionForPermission() {
        return numberOfDimensionForPermission;
    }

    public void setNumberOfDimensionForPermission(Map<String, Integer> numberOfDimensionForPermission) {
        this.numberOfDimensionForPermission = numberOfDimensionForPermission;
    }

    public void setWorlds(List<World> worlds) {
        this.worlds = worlds;
    }

    @Nonnull
    @Override
    public String getFileName() {
        return "multiworld.json";
    }

    @Nonnull
    @Override
    public IJsonFile getDefaultJson() {
        return new MultiworldConfig();
    }
}

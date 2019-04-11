package play.ai.dragonrealm.geiloutils.multiworld;

import net.minecraftforge.common.DimensionManager;
import play.ai.dragonrealm.geiloutils.GeiloUtils;
import play.ai.dragonrealm.geiloutils.new_configs.ConfigAccess;
import play.ai.dragonrealm.geiloutils.new_configs.models.World;
import play.ai.dragonrealm.geiloutils.exceptions.NoFreeDimensionIDException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorldManager {
    public static int findFreeDimensionID() throws NoFreeDimensionIDException {
        for(int i = 2; i < Integer.MAX_VALUE; i++){
            if(!DimensionManager.isDimensionRegistered(i)){
                return i;
            }
        }

        throw new NoFreeDimensionIDException();
    }

    /**
     * Register dimensions.
     */
    public static void registerDimension(WorldVoid worldVoid)
    {
        DimensionManager.registerDimension(worldVoid.getDimensionID(), worldVoid.getDimensionType());
        GeiloUtils.getLogger().info("Registered dimension: Name: " + worldVoid.getDimensionName() + " ID: " + worldVoid.getDimensionID());
    }

    public static void registerDimensions(){
        for(World configWorld : ConfigAccess.getMultiworldConfig().getWorlds()){
            registerDimension(new WorldVoid(configWorld.getDimensionName(), configWorld.getDimensionTypeEnum(), false));
        }
    }

    public static List<String> dimensionNameList(){
        List<String> nameList = new ArrayList<>();
        for(Integer dimID : DimensionManager.getStaticDimensionIDs()){
            nameList.add(DimensionManager.getProviderType(dimID).getName());
        }

        return nameList;
    }

    public static Map<String, Integer> dimensionMap(){
        Map<String, Integer> dimMap = new HashMap<>();
        for(Integer dimID : DimensionManager.getStaticDimensionIDs()){
            dimMap.put(DimensionManager.getProviderType(dimID).getName(), dimID);
        }

        return dimMap;
    }
}

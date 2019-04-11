package play.ai.dragonrealm.geiloutils.multiworld;

import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import play.ai.dragonrealm.geiloutils.GeiloUtils;
import play.ai.dragonrealm.geiloutils.new_configs.ConfigAccess;
import play.ai.dragonrealm.geiloutils.new_configs.models.World;
import play.ai.dragonrealm.geiloutils.multiworld.Void.WorldProviderVoid;

public class WorldVoid {
    private String dimensionName;

    private int dimensionID;
    private DimensionType dimensionType;
    private WorldProvider worldProvider;

    public WorldVoid(String dimensionName, DimensionTypeEnum dimensionTypeEnum, boolean firstGeneration) {
        try {
            //TODO: Make this a little bit more universal
            this.dimensionName = dimensionName;
            this.dimensionID = WorldManager.findFreeDimensionID();
            switch(dimensionTypeEnum){
                case VOID:
                    this.dimensionType = DimensionType.register(dimensionName, "_"+dimensionName, dimensionID, WorldProviderVoid.class, true);
                    this.worldProvider = new WorldProviderVoid(dimensionType);
                    break;
                case OVERWORLD:
                    break;
                case MININGWORLD:
                    break;
            }

            if(firstGeneration) {
                ConfigAccess.getMultiworldConfig().getWorlds().add(new World(dimensionName, DimensionTypeEnum.VOID, dimensionID));
                ConfigAccess.writeMultiworldFile(); //TODO: Remove this
            }
            //GeiloUtils.getLogger().info("Registered dimension: Name: " + ConfigurationManager.getMultiworldConfig().getWorlds().get(ConfigurationManager.getMultiworldConfig().getWorlds().size() - 1).getDimensionName() + " ID: " + ConfigurationManager.getMultiworldConfig().getWorlds().get(ConfigurationManager.getMultiworldConfig().getWorlds().size() - 1).getDimensionsID());
        }catch (Exception e){
            GeiloUtils.getLogger().error("Error creating void world: Name" + dimensionName + " Dimension ID: " + dimensionID);
        }
    }

    public WorldVoid(String dimensionName, DimensionTypeEnum dimensionTypeEnum, String ownerUUID, boolean firstGeneration) {
        try {
            //TODO: Make this a little bit more universal
            this.dimensionName = dimensionName;
            this.dimensionID = WorldManager.findFreeDimensionID();
            switch(dimensionTypeEnum){
                case VOID:
                    this.dimensionType = DimensionType.register(dimensionName, "_"+dimensionName, dimensionID, WorldProviderVoid.class, true);
                    this.worldProvider = new WorldProviderVoid(dimensionType);
                    break;
                case OVERWORLD:
                    break;
                case MININGWORLD:
                    break;
            }

            if(firstGeneration) {
                ConfigAccess.getMultiworldConfig().getWorlds().add(new World(dimensionName, DimensionTypeEnum.VOID, ownerUUID, dimensionID));
                ConfigAccess.writeMultiworldFile(); //TODO: Remove this
            }
            //GeiloUtils.getLogger().info("Registered dimension: Name: " + ConfigurationManager.getMultiworldConfig().getWorlds().get(ConfigurationManager.getMultiworldConfig().getWorlds().size() - 1).getDimensionName() + " ID: " + ConfigurationManager.getMultiworldConfig().getWorlds().get(ConfigurationManager.getMultiworldConfig().getWorlds().size() - 1).getDimensionsID());
        }catch (Exception e){
            GeiloUtils.getLogger().error("Error creating void world: Name" + dimensionName + " Dimension ID: " + dimensionID);
        }
    }
    public String getDimensionName() {
        return dimensionName;
    }

    public void setDimensionName(String dimensionName) {
        this.dimensionName = dimensionName;
    }

    public int getDimensionID() {
        return dimensionID;
    }

    public DimensionType getDimensionType() {
        return dimensionType;
    }
}

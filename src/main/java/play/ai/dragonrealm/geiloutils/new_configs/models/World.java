package play.ai.dragonrealm.geiloutils.new_configs.models;

import play.ai.dragonrealm.geiloutils.multiworld.DimensionTypeEnum;

import java.util.ArrayList;
import java.util.List;

public class World {
    private String ownerUUID;
    private int dimensionsID;
    private boolean isFirstJoin;
    private List<String> allyList = new ArrayList<>();
    private DimensionTypeEnum dimensionTypeEnum;
    private String dimensionName;

    public List<String> getAllyList() {
        return allyList;
    }

    public void setAllyList(List<String> allyList) {
        this.allyList = allyList;
    }

    public DimensionTypeEnum getDimensionTypeEnum() {
        return dimensionTypeEnum;
    }

    public void setDimensionTypeEnum(DimensionTypeEnum dimensionTypeEnum) {
        this.dimensionTypeEnum = dimensionTypeEnum;
    }

    public String getDimensionName() {
        return dimensionName;
    }

    public void setDimensionName(String dimensionName) {
        this.dimensionName = dimensionName;
    }

    public World(String dimensionName, DimensionTypeEnum dimensionTypeEnum, String ownerUUID, int dimensionsID, boolean isFirstJoin) {
        this.dimensionName = dimensionName;
        this.dimensionTypeEnum = dimensionTypeEnum;
        this.ownerUUID = ownerUUID;
        this.dimensionsID = dimensionsID;
        this.isFirstJoin = isFirstJoin;
    }

    public World(String dimensionName, DimensionTypeEnum dimensionTypeEnum, String ownerUUID, int dimensionsID) {
        this.dimensionTypeEnum = dimensionTypeEnum;
        this.dimensionName = dimensionName;
        this.ownerUUID = ownerUUID;
        this.dimensionsID = dimensionsID;
        this.isFirstJoin = true;
    }

    public World(String dimensionName, DimensionTypeEnum dimensionTypeEnum, int dimensionsID) {
        this.dimensionTypeEnum = dimensionTypeEnum;
        this.dimensionName = dimensionName;
        this.ownerUUID = "";
        this.dimensionsID = dimensionsID;
        this.isFirstJoin = true;
    }

    public String getOwnerUUID() {
        return ownerUUID;
    }

    public void setOwnerUUID(String ownerUUID) {
        this.ownerUUID = ownerUUID;
    }

    public int getDimensionsID() {
        return dimensionsID;
    }

    public void setDimensionsID(int dimensionsID) {
        this.dimensionsID = dimensionsID;
    }

    public boolean isFirstJoin() {
        return isFirstJoin;
    }

    public void setFirstJoin(boolean firstJoin) {
        isFirstJoin = firstJoin;
    }
}
